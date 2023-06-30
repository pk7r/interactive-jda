package dev.pk7r.interactive.jda.factory;

import dev.pk7r.interactive.jda.annotation.command.slash.MetaCommand;
import dev.pk7r.interactive.jda.annotation.command.slash.MetaSubCommand;
import dev.pk7r.interactive.jda.exception.InteractiveComponentCreationException;
import dev.pk7r.interactive.jda.exception.InteractiveComponentPublishException;
import dev.pk7r.interactive.jda.registry.InteractiveComponentRegistry;
import dev.pk7r.interactive.jda.support.common.Sender;
import dev.pk7r.interactive.jda.support.component.SlashCommandComponent;
import dev.pk7r.interactive.jda.support.definition.interactive.InteractiveSlashCommand;
import dev.pk7r.interactive.jda.utils.AnnotationUtils;
import dev.pk7r.interactive.jda.utils.CommandUtils;
import dev.pk7r.interactive.jda.wrapper.MetaSlashCommandWrapper;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.val;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.util.Set;

@Getter
@Builder
public class SlashCommandFactory extends PublishableInteractiveFactory<SlashCommandComponent, InteractiveSlashCommand> {

    private JDA jda;

    private Guild guild;

    @Singular
    private Set<SlashCommandComponent> components;

    private InteractiveComponentRegistry<InteractiveSlashCommand> registry;

    @Override
    public InteractiveSlashCommand create(SlashCommandComponent component) {
        val componentClass = component.getClass();
        if (!CommandUtils.isCommand(componentClass) && !CommandUtils.isSubCommand(componentClass)) {
            val message = String.format("@MetaCommand or @MetaSubCommand is mandatory on SlashCommandComponent (%s)",
                    componentClass.getName());
            throw new InteractiveComponentCreationException(message);
        }
        Class<? extends SlashCommandComponent> mainCommandClass;
        if (CommandUtils.isSubCommand(componentClass)) {
            val subCommand = AnnotationUtils.get(componentClass, MetaSubCommand.class);
            mainCommandClass = subCommand.mainCommand();
        } else mainCommandClass = componentClass;
        val mainCommand = AnnotationUtils.get(mainCommandClass, MetaCommand.class);
        return getRegistry().register(InteractiveSlashCommand
                .builder()
                        .id(CommandUtils.getFullCommandName(componentClass))
                        .component(MetaSlashCommandWrapper.getInstance().wrap(componentClass))
                        .guildOnly(mainCommand.guildOnly())
                        .NSFW(mainCommand.nsfw())
                        .subCommand(CommandUtils.isSubCommand(componentClass))
                        .interactiveClass(componentClass)
                        .interactiveComponent(component)
                .build());
    }

    @Override
    void publish(InteractiveSlashCommand interactiveSlashCommand) throws InteractiveComponentPublishException {
        if (interactiveSlashCommand.isGuildOnly()) {
            guild.updateCommands().addCommands(interactiveSlashCommand.getComponent()).queue();
            return;
        }
        jda.updateCommands().addCommands(interactiveSlashCommand.getComponent()).queue();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        val interactive = getRegistry().get(event.getFullCommandName())
                .orElseThrow();
        interactive.getInteractiveComponent()
                .interact(Sender.ofEvent(event), event.getOptions().toArray(OptionMapping[]::new))
                .accept(event.getInteraction());
    }
}