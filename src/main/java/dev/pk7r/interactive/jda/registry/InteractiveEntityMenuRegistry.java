package dev.pk7r.interactive.jda.registry;

import dev.pk7r.interactive.jda.exception.DuplicateInteractiveComponentException;
import dev.pk7r.interactive.jda.support.definition.interactive.InteractiveContextCommand;
import dev.pk7r.interactive.jda.support.definition.interactive.InteractiveEntityMenu;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InteractiveEntityMenuRegistry extends InteractiveComponentRegistry<InteractiveEntityMenu> {

    @Getter
    private static final InteractiveComponentRegistry<InteractiveEntityMenu> instance = new InteractiveEntityMenuRegistry();

    @Getter
    private final Set<InteractiveEntityMenu> registered = new HashSet<>();

    @Override
    public Optional<InteractiveEntityMenu> get(String id) {
        return getRegistered()
                .stream()
                .filter(i -> i.getId().equals(id))
                .findFirst();
    }

    @Override
    public InteractiveEntityMenu register(InteractiveEntityMenu interactive) {
        if (exists(interactive.getId())) {
            val message = String.format("Found multiple InteractiveEntityMenu for id '%s'", interactive.getId());
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