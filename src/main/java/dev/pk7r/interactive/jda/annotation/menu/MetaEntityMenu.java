package dev.pk7r.interactive.jda.annotation.menu;

import dev.pk7r.interactive.jda.annotation.stereotype.RequiredRange;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.interactions.components.selections.EntitySelectMenu;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MetaEntityMenu {

    String id();

    String placeholder() default "";

    boolean disabled() default false;

    EntitySelectMenu.SelectTarget[] target();

    RequiredRange range() default @RequiredRange;

    ChannelType channelTypes() default ChannelType.TEXT;

}