package dev.pk7r.interactive.jda.annotation.command.slash;

import dev.pk7r.interactive.jda.annotation.stereotype.localization.LocalizationsMapping;

public @interface MetaOptionChoice {

    String name();

    String value();

    LocalizationsMapping localizationsMapping() default @LocalizationsMapping;

}