package dev.pk7r.interactive.jda.annotation.button;

import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MetaButton {

    String idOrUrl();

    String label() default "";

    String unicodeEmoji() default "";

    ButtonStyle style();

    boolean disabled() default false;

}