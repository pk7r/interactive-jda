package dev.pk7r.interactive.jda.annotation.menu.string;

import dev.pk7r.interactive.jda.annotation.stereotype.RequiredRange;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MetaStringMenu {

    String id();

    String placeholder() default "";

    boolean disabled() default false;

    MetaSelectOption[] options();

    RequiredRange range() default @RequiredRange;

}