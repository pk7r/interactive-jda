package dev.pk7r.interactive.jda.factory;

import dev.pk7r.interactive.jda.annotation.command.context.ContextCommandType;
import dev.pk7r.interactive.jda.annotation.command.context.MetaContextCommand;
import dev.pk7r.interactive.jda.exception.InteractiveComponentCreationException;
import dev.pk7r.interactive.jda.exception.InteractiveComponentNotFoundException;
import dev.pk7r.interactive.jda.exception.InteractiveComponentPublishException;
import dev.pk7r.interactive.jda.registry.InteractiveComponentRegistry;
import dev.pk7r.interactive.jda.support.common.Sender;
import dev.pk7r.interactive.jda.support.component.ContextCommandComponent;
import dev.pk7r.interactive.jda.support.definition.CommandComponentDefinition;
import dev.pk7r.interactive.jda.support.definition.interactive.InteractiveContextCommand;
import dev.pk7r.interactive.jda.utils.AnnotationUtils;
import dev.pk7r.interactive.jda.wrapper.MetaContextCommandWrapper;
import lombok.Getter;
import lombok.val;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Set;

@Getter
class ContextCommandFactory extends InteractiveFactoryAware<ContextCommandComponent, InteractiveContextCommand>
        implements PublishableInteractiveFactory<CommandData, ContextCommandComponent, InteractiveContextCommand> {

    private final Set<ContextCommandComponent> components;

    private final InteractiveComponentRegistry<InteractiveContextCommand> registry;

    protected ContextCommandFactory(JDA jda, Guild guild, Set<ContextCommandComponent> components, InteractiveComponentRegistry<InteractiveContextCommand> registry) {
        super(jda, guild);
        this.components = components;
        this.registry = registry;
    }

    @Override
    public void onMessageContextInteraction(@NotNull MessageContextInteractionEvent event) {
        val interactive = getRegistry().get(event.getFullCommandName())
                .filter(i -> i.getType().equals(ContextCommandType.MESSAGE))
                .orElseThrow(() -> {
                    val message = String.format("MessageContextCommand with id '%s' not found", event.getFullCommandName());
                    return new InteractiveComponentNotFoundException(message);
                });
        interactive.getInteractiveComponent()
                .interact(Sender.ofEvent(event))
                .accept(event.getInteraction());
    }

    @Override
    public void onUserContextInteraction(UserContextInteractionEvent event) {
        val interactive = getRegistry().get(event.getFullCommandName())
                .filter(i -> i.getType().equals(ContextCommandType.USER))
                .orElseThrow(() -> {
                    val message = String.format("UserContextCommand with id '%s' not found", event.getFullCommandName());
                    return new InteractiveComponentNotFoundException(message);
                });
        interactive.getInteractiveComponent()
                .interact(Sender.ofEvent(event))
                .accept(event.getInteraction());
    }


    @Override
    public InteractiveContextCommand create(ContextCommandComponent component) {
        val componentClass = component.getClass();
        if (!AnnotationUtils.hasAnnotation(MetaContextCommand.class, componentClass)) {
            val message = String.format("@MetaContextCommand is mandatory on MessageContextCommandComponent (%s)",
                    componentClass.getName());
            throw new InteractiveComponentCreationException(message);
        }
        val annotation = AnnotationUtils.get(componentClass, MetaContextCommand.class);
        if (annotation.name().isEmpty()) {
            val message = String.format("Param 'name' on @MetaContextCommand is mandatory on MessageContextCommandComponent (%s)",
                    componentClass.getName());
            throw new InteractiveComponentCreationException(message);
        }
        return getRegistry().register(InteractiveContextCommand
                .builder()
                .id(annotation.name())
                .type(annotation.type())
                .component(MetaContextCommandWrapper.getInstance().wrap(annotation))
                .interactiveClass(componentClass)
                .guildOnly(annotation.guildOnly())
                .NSFW(annotation.nsfw())
                .interactiveComponent(component)
                .build());
    }

    @Override
    public void publish() throws InteractiveComponentPublishException {
        getRegistry().getRegistered()
                .stream()
                .filter(Objects::nonNull)
                .forEach(d -> {
                    if (((CommandComponentDefinition) d).isGuildOnly()) {
                        getGuild().updateCommands().addCommands(d.getComponent()).queue();
                    } else {
                        getJda().updateCommands().addCommands(d.getComponent()).queue();
                    }
                });
    }
}