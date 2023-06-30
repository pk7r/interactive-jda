package dev.pk7r.interactive.jda.support.definition;

public interface InteractiveComponentDefinition<T, K> {

    String getId();

    K getComponent();

    T getInteractiveComponent();

    Class<? extends T> getInteractiveClass();

}