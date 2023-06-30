package dev.pk7r.interactive.jda.annotation.menu.string;

public @interface MetaSelectOption {

    String label();

    String value();

    String unicodeEmoji() default "";

    String description() default "";

    boolean defaultOption() default false;

}