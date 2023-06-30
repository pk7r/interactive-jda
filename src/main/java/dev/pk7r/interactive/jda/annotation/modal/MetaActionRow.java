package dev.pk7r.interactive.jda.annotation.modal;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MetaActionRow {

    MetaItemComponent[] value();

}