package dev.pk7r.interactive.jda.utils;

import dev.pk7r.interactive.jda.annotation.command.slash.MetaCommand;
import dev.pk7r.interactive.jda.annotation.command.slash.MetaSubCommand;
import dev.pk7r.interactive.jda.support.component.SlashCommandComponent;
import lombok.experimental.UtilityClass;
import lombok.val;

import java.util.Arrays;

@UtilityClass
public class CommandUtils {

    public String getFullCommandName(Class<? extends SlashCommandComponent> clazz) {
        if (!isSubCommand(clazz)) {
            return AnnotationUtils.get(clazz, MetaCommand.class).name();
        }
        val subCommand = AnnotationUtils.get(clazz, MetaSubCommand.class);
        val mainCommandClass = subCommand.mainCommand();
        val mainCommand = AnnotationUtils.get(mainCommandClass, MetaCommand.class);
        if (Arrays.asList(mainCommand.subCommands()).contains(clazz)) {
            return String.format("%s %s", mainCommand.name(), subCommand.name());
        }
        val groups = mainCommand.subCommandGroups();
        val subCommandGroup = Arrays.stream(groups)
                .filter(group -> Arrays.asList(group.subCommands()).contains(clazz))
                .findFirst()
                .orElseThrow();
        return String.format("%s %s %s", mainCommand.name(), subCommandGroup.name(), subCommand.name());
    }

    public boolean isSubCommand(Class<? extends SlashCommandComponent> clazz) {
        return AnnotationUtils.hasAnnotation(MetaSubCommand.class, clazz);
    }

    public boolean isCommand(Class<? extends SlashCommandComponent> clazz) {
        return AnnotationUtils.hasAnnotation(MetaCommand.class, clazz);
    }
}