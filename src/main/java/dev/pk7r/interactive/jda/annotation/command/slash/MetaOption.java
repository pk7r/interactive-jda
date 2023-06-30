package dev.pk7r.interactive.jda.annotation.command.slash;

import dev.pk7r.interactive.jda.annotation.stereotype.RequiredRange;
import dev.pk7r.interactive.jda.annotation.stereotype.localization.LocalizationsMapping;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.interactions.commands.OptionType;

public @interface MetaOption {

    String name();

    OptionType type();

    ChannelType[] channelTypes() default {};

    String description() default "";

    boolean required() default true;

    String providerId() default "";

    MetaOptionChoice[] choices() default {};

    LocalizationsMapping localizationsMapping() default @LocalizationsMapping;

    RequiredRange range() default @RequiredRange;

}