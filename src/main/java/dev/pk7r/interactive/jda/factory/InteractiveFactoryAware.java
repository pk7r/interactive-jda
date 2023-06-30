package dev.pk7r.interactive.jda.factory;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public abstract class InteractiveFactoryAware extends ListenerAdapter {

    public InteractiveFactoryAware initialize() {
        getJda().addEventListener(this);
        return this;
    }

    protected abstract JDA getJda();

    protected abstract Guild getGuild();

}