package dev.pk7r.interactive.jda.registry;

import dev.pk7r.interactive.jda.support.definition.InteractiveComponentDefinition;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
public abstract class InteractiveComponentRegistry<T extends InteractiveComponentDefinition<?, ?>> {

    public abstract Optional<T> get(String id);

    public abstract Set<T> getRegistered();

    public abstract T register(T t);

    public abstract boolean exists(String id);

    public abstract void unregister(String id);

}