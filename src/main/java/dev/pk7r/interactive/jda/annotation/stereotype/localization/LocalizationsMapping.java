package dev.pk7r.interactive.jda.annotation.stereotype.localization;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface LocalizationsMapping {

    NameLocalization[] name() default {};

    DescriptionLocalization[] descriptions() default {};

}