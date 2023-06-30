package dev.pk7r.interactive.jda.factory;

import dev.pk7r.interactive.jda.annotation.command.context.ContextCommandType;
import dev.pk7r.interactive.jda.annotation.command.context.MetaContextCommand;
import dev.pk7r.interactive.jda.exception.InteractiveComponentCreationException;
import dev.pk7r.interactive.jda.exception.InteractiveComponentNotFoundException;
import dev.pk7r.interactive.jda.exception.InteractiveComponentPublishException;
import dev.pk7r.interactive.jda.registry.InteractiveComponentRegistry;
import dev.pk7r.interactive.jda.support.common.Sender;
import dev.pk7r.interactive.jda.support.component.ContextCommandComponent;
import dev.pk7r.interactive.jda.support.definition.interactive.InteractiveContextCommand;
import dev.pk7r.interactive.jda.utils.AnnotationUtils;
import dev.pk7r.interactive.jda.wrapper.MetaContextCommandWrapper;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.val;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

@Getter
@Builder
class ContextCommandFactory extends PublishableInteractiveFactory<ContextCommandComponent, InteractiveContextCommand> {

    private JDA jda;

    private Guild guild;

    @Singular
    private Set<ContextCommandComponent> components;

    private InteractiveComponentRegistry<InteractiveContextCommand> registry;

    @Override
    void publish(InteractiveContextCommand interactiveContextCommand) throws InteractiveComponentPublishException {
        if (interactiveContextCommand.isGuildOnly()) {
            guild.updateCommands().addCommands(interactiveContextCommand.getComponent()).queue();
            return;
        }
        jda.updateCommands().addCommands(interactiveContextCommand.getComponent()).queue();
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
}