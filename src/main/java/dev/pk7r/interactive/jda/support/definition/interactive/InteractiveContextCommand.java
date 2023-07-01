package dev.pk7r.interactive.jda.support.definition.interactive;

import dev.pk7r.interactive.jda.annotation.command.context.ContextCommandType;
import dev.pk7r.interactive.jda.support.component.ContextCommandComponent;
import dev.pk7r.interactive.jda.support.definition.CommandComponentDefinition;
import dev.pk7r.interactive.jda.support.definition.InteractiveComponentDefinition;
import lombok.Builder;
import lombok.Getter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

@Getter
@Builder
public class InteractiveContextCommand implements InteractiveComponentDefinition<ContextCommandComponent, CommandData>, CommandComponentDefinition {

    private String id;

    private ContextCommandType type;

    private boolean guildOnly;

    private boolean NSFW;

    private CommandData component;

    private ContextCommandComponent interactiveComponent;

    private Class<? extends ContextCommandComponent> interactiveClass;

}