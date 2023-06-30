package dev.pk7r.interactive.jda.support.definition.interactive;

import dev.pk7r.interactive.jda.support.component.SlashCommandComponent;
import dev.pk7r.interactive.jda.support.definition.InteractiveComponentDefinition;
import lombok.Builder;
import lombok.Getter;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

@Getter
@Builder
public class InteractiveSlashCommand implements InteractiveComponentDefinition<SlashCommandComponent, SlashCommandData> {

    private String id;

    private boolean subCommand;

    private boolean guildOnly;

    private boolean NSFW;

    private SlashCommandData component;

    private SlashCommandComponent interactiveComponent;

    private Class<? extends SlashCommandComponent> interactiveClass;

}