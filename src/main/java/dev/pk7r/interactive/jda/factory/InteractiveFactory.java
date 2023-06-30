package dev.pk7r.interactive.jda.factory;

import dev.pk7r.interactive.jda.registry.InteractiveComponentRegistry;
import dev.pk7r.interactive.jda.support.definition.InteractiveComponentDefinition;

import java.util.Set;

public abstract class InteractiveFactory<T, K extends InteractiveComponentDefinition<T, ?>>
        extends InteractiveFactoryAware {

    public InteractiveFactory<T, K> initialize() {
        super.initialize();
        getComponents().forEach(this::create);
        return this;
    }

    public abstract K create(T component);

    public abstract Set<T> getComponents();

    public abstract InteractiveComponentRegistry<K> getRegistry();

}