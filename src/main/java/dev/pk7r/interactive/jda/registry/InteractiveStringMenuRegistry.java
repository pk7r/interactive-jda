package dev.pk7r.interactive.jda.registry;

import dev.pk7r.interactive.jda.exception.DuplicateInteractiveComponentException;
import dev.pk7r.interactive.jda.support.definition.interactive.InteractiveStringMenu;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InteractiveStringMenuRegistry extends InteractiveComponentRegistry<InteractiveStringMenu> {

    @Getter
    private static final InteractiveComponentRegistry<InteractiveStringMenu> instance = new InteractiveStringMenuRegistry();

    @Override
    public Optional<InteractiveStringMenu> get(String id) {
        return getRegistered()
                .stream()
                .filter(i -> i.getId().equals(id))
                .findFirst();
    }

    @Override
    public InteractiveStringMenu register(InteractiveStringMenu interactive) {
        if (exists(interactive.getId())) {
            val message = String.format("Found multiple InteractiveStringMenu for id '%s'", interactive.getId());
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