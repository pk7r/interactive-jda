package dev.pk7r.interactive.jda.registry;

import dev.pk7r.interactive.jda.exception.DuplicateInteractiveComponentException;
import dev.pk7r.interactive.jda.support.definition.interactive.InteractiveModal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InteractiveModalRegistry extends InteractiveComponentRegistry<InteractiveModal> {

    @Getter
    private static final InteractiveComponentRegistry<InteractiveModal> instance = new InteractiveModalRegistry();

    @Override
    public Optional<InteractiveModal> get(String id) {
        return getRegistered()
                .stream()
                .filter(i -> i.getId().equals(id))
                .findFirst();
    }

    @Override
    public InteractiveModal register(InteractiveModal interactive) {
        if (exists(interactive.getId())) {
            val message = String.format("Found multiple InteractiveModal for id '%s'", interactive.getId());
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