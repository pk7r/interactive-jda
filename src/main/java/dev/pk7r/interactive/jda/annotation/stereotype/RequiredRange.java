package dev.pk7r.interactive.jda.annotation.stereotype;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredRange {

    int min() default -1;

    int max() default -1;

}