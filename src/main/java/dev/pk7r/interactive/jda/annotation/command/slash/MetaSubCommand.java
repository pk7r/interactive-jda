package dev.pk7r.interactive.jda.annotation.command.slash;

import dev.pk7r.interactive.jda.annotation.stereotype.localization.LocalizationsMapping;
import dev.pk7r.interactive.jda.support.component.SlashCommandComponent;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MetaSubCommand {

    String name();

    String description() default "";

    MetaOption[] options() default {};

    LocalizationsMapping localizationsMapping() default @LocalizationsMapping;

    Class<? extends SlashCommandComponent> mainCommand();

}