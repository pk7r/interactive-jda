package dev.pk7r.interactive.jda.factory;

import dev.pk7r.interactive.jda.exception.InteractiveComponentPublishException;
import dev.pk7r.interactive.jda.support.component.InteractiveComponent;
import dev.pk7r.interactive.jda.support.definition.InteractiveComponentDefinition;

public abstract class PublishableInteractiveFactory<T extends InteractiveComponent<?>,
                                                    K extends InteractiveComponentDefinition<T, ?>>
        extends InteractiveFactory<T, K> {

    @Override
    public InteractiveFactory<T, K> initialize() {
        super.initialize();
        getRegistry().getRegistered().forEach(this::publish);
        return this;
    }

    abstract void publish(K k) throws InteractiveComponentPublishException;

}