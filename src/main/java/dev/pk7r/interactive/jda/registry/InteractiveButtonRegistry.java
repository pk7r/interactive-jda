package dev.pk7r.interactive.jda.registry;

import dev.pk7r.interactive.jda.exception.DuplicateInteractiveComponentException;
import dev.pk7r.interactive.jda.support.definition.interactive.InteractiveButton;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InteractiveButtonRegistry extends InteractiveComponentRegistry<InteractiveButton> {

    @Getter
    private static final InteractiveComponentRegistry<InteractiveButton> instance = new InteractiveButtonRegistry();

    @Getter
    private final Set<InteractiveButton> registered = new HashSet<>();

    @Override
    public Optional<InteractiveButton> get(String id) {
        return getRegistered()
                .stream()
                .filter(i -> i.getId().equals(id))
                .findFirst();
    }

    @Override
    public InteractiveButton register(InteractiveButton interactive) {
        if (exists(interactive.getId())) {
            val message = String.format("Found multiple InteractiveButton for id '%s'", interactive.getId());
            throw new DuplicateInteractiveComponentException(message);
        }
        getRegistered().add(interactive);
        return interactive;
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