package dev.pk7r.interactive.jda.registry;

import dev.pk7r.interactive.jda.exception.DuplicateInteractiveComponentException;
import dev.pk7r.interactive.jda.support.definition.interactive.InteractiveProvider;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InteractiveIterableProviderRegistry extends InteractiveComponentRegistry<InteractiveProvider> {

    @Getter
    private static final InteractiveComponentRegistry<InteractiveProvider> instance = new InteractiveIterableProviderRegistry();

    @Override
    public Optional<InteractiveProvider> get(String id) {
        return getRegistered()
                .stream()
                .filter(i -> i.getId().equals(id))
                .findFirst();
    }

    //TODO
    @Override
    public InteractiveProvider register(InteractiveProvider interactive) {
        if (exists(interactive.getId())) {
            val message = String.format("Found multiple InteractiveEntityMenu for id '%s'", interactive.getId());
            throw new DuplicateInteractiveComponentException(message);
        }
        getRegistered().add(interactive);
        return null;
    }

    @Override
    public boolean exists(String id) {
        return getRegistered()
                .stream()
                .anyMatch(i -> i.getId().equals(id));
    }

    @Override
    public void unregister(String id) {
        getRegistered().removeIf(i -> i.getId().equals(id));
    }
}