package dev.pk7r.interactive.jda.registry;

import dev.pk7r.interactive.jda.support.definition.InteractiveComponentDefinition;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public abstract class InteractiveComponentRegistry<T extends InteractiveComponentDefinition<?, ?>> {

    public abstract Optional<T> get(String id);

    public Set<T> getRegistered() {
        return new HashSet<>();
    }

    public abstract T register(T t);

    public abstract boolean exists(String id);

    public abstract void unregister(String id);

}