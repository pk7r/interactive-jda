package dev.pk7r.interactive.jda.factory;

import dev.pk7r.interactive.jda.annotation.command.slash.MetaCommand;
import dev.pk7r.interactive.jda.annotation.command.slash.MetaSubCommand;
import dev.pk7r.interactive.jda.exception.InteractiveComponentCreationException;
import dev.pk7r.interactive.jda.exception.InteractiveComponentPublishException;
import dev.pk7r.interactive.jda.registry.InteractiveComponentRegistry;
import dev.pk7r.interactive.jda.support.common.Sender;
import dev.pk7r.interactive.jda.support.component.SlashCommandComponent;
import dev.pk7r.interactive.jda.support.definition.CommandComponentDefinition;
import dev.pk7r.interactive.jda.support.definition.interactive.InteractiveSlashCommand;
import dev.pk7r.interactive.jda.utils.AnnotationUtils;
import dev.pk7r.interactive.jda.utils.CommandUtils;
import dev.pk7r.interactive.jda.wrapper.MetaSlashCommandWrapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.util.Set;

@Slf4j
@Getter
public class SlashCommandFactory extends InteractiveFactoryAware<SlashCommandComponent, InteractiveSlashCommand>
        implements PublishableInteractiveFactory<SlashCommandData, SlashCommandComponent, InteractiveSlashCommand>  {


    private final Set<SlashCommandComponent> components;

    private final InteractiveComponentRegistry<InteractiveSlashCommand> registry;

    protected SlashCommandFactory(JDA jda, Guild guild, Set<SlashCommandComponent> components, InteractiveComponentRegistry<InteractiveSlashCommand> registry) {
        super(jda, guild);
        this.components = components;
        this.registry = registry;
    }

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
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        val interactive = getRegistry().get(event.getFullCommandName())
                .orElseThrow();
        interactive.getInteractiveComponent()
                .interact(Sender.ofEvent(event), event.getOptions().toArray(OptionMapping[]::new))
                .accept(event.getInteraction());
    }

    @Override
    public void publish() throws InteractiveComponentPublishException {
        getRegistry().getRegistered()
                .forEach(d -> {
                    if (((CommandComponentDefinition) d).isGuildOnly()) {
                        getGuild().updateCommands().addCommands(d.getComponent()).queue();
                    } else {
                        getJda().updateCommands().addCommands(d.getComponent()).queue();
                    }
                });
    }
}