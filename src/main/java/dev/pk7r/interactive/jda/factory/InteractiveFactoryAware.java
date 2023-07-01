package dev.pk7r.interactive.jda.factory;

import dev.pk7r.interactive.jda.support.common.Interactive;
import dev.pk7r.interactive.jda.support.definition.InteractiveComponentDefinition;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@Getter
public abstract class InteractiveFactoryAware<T extends Interactive,
        K extends InteractiveComponentDefinition<T, ?>> extends ListenerAdapter {

    private final JDA jda;

    private final Guild guild;

    protected InteractiveFactoryAware(JDA jda, Guild guild) {
        this.jda = jda;
        this.guild = guild;
        getJda().addEventListener(this);
    }
}