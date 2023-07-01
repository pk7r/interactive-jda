package dev.pk7r.interactive.jda.factory;

import dev.pk7r.interactive.jda.registry.InteractiveComponentRegistry;
import dev.pk7r.interactive.jda.support.definition.InteractiveComponentDefinition;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;

import java.util.Set;


public interface InteractiveFactory<T, K extends InteractiveComponentDefinition<T, ?>> {

    default void initialize() {
        getComponents().forEach(this::create);
    }

    Set<T> getComponents();

    InteractiveComponentRegistry<K> getRegistry();

    K create(T component);

    Guild getGuild();

    JDA getJda();

}