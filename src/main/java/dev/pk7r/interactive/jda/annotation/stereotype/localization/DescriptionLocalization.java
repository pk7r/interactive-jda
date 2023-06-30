package dev.pk7r.interactive.jda.annotation.stereotype.localization;

import net.dv8tion.jda.api.interactions.DiscordLocale;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DescriptionLocalization {

    String value();

    DiscordLocale locale();

}