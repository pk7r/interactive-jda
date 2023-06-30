package dev.pk7r.interactive.jda.annotation.command.slash;

import dev.pk7r.interactive.jda.annotation.stereotype.localization.LocalizationsMapping;
import dev.pk7r.interactive.jda.support.component.SlashCommandComponent;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MetaSubCommandGroup {

    String name();

    String description();

    LocalizationsMapping localizationsMapping() default @LocalizationsMapping;

    Class<? extends SlashCommandComponent>[] subCommands();

}