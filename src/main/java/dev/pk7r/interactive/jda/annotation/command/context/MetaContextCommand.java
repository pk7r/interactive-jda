package dev.pk7r.interactive.jda.annotation.command.context;

import dev.pk7r.interactive.jda.annotation.stereotype.localization.LocalizationsMapping;
import net.dv8tion.jda.api.Permission;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MetaContextCommand {

    String name();

    boolean guildOnly() default false;

    boolean nsfw() default false;

    ContextCommandType type();

    Permission[] permissions() default {};

    LocalizationsMapping localizationsMapping() default @LocalizationsMapping;

}