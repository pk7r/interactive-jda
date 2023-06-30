package dev.pk7r.interactive.jda.annotation.command.slash;

import dev.pk7r.interactive.jda.annotation.stereotype.localization.LocalizationsMapping;
import dev.pk7r.interactive.jda.support.component.SlashCommandComponent;
import net.dv8tion.jda.api.Permission;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MetaCommand {

    String name();

    boolean guildOnly() default false;

    boolean nsfw() default false;

    String description() default "";

    MetaOption[] options() default {};

    Permission[] permissions() default {};

    MetaSubCommandGroup[] subCommandGroups() default {};

    Class<? extends SlashCommandComponent>[] subCommands() default {};

    LocalizationsMapping localizationsMapping() default @LocalizationsMapping;

}