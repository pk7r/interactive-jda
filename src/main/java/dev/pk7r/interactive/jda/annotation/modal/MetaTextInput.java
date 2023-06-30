package dev.pk7r.interactive.jda.annotation.modal;

import dev.pk7r.interactive.jda.annotation.stereotype.RequiredRange;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MetaTextInput {

    String placeholder() default "";

    boolean required() default false;

    String defaultValue() default "";

    RequiredRange range() default @RequiredRange;

    @NotNull String id();

    @NotNull String label();

    @NotNull TextInputStyle style();

}