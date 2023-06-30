package dev.pk7r.interactive.jda.annotation.modal;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MetaModal {

    String id();

    String title();

    MetaActionRow[] actionRows() default {};

}