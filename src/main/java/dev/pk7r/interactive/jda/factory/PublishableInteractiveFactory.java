package dev.pk7r.interactive.jda.factory;

import dev.pk7r.interactive.jda.support.component.InteractiveComponent;
import dev.pk7r.interactive.jda.support.definition.InteractiveComponentDefinition;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public interface PublishableInteractiveFactory<D extends CommandData, T extends InteractiveComponent<?>,
                                                    K extends InteractiveComponentDefinition<T, D>>
        extends InteractiveFactory<T, K> {

    @Override
    default void initialize() {
        InteractiveFactory.super.initialize();
        publish();
    }

    void publish();
}