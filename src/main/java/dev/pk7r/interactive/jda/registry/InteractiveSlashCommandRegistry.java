package dev.pk7r.interactive.jda.registry;

import dev.pk7r.interactive.jda.exception.DuplicateInteractiveComponentException;
import dev.pk7r.interactive.jda.support.definition.interactive.InteractiveModal;
import dev.pk7r.interactive.jda.support.definition.interactive.InteractiveSlashCommand;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InteractiveSlashCommandRegistry extends InteractiveComponentRegistry<InteractiveSlashCommand> {

    @Getter
    private static final InteractiveComponentRegistry<InteractiveSlashCommand> instance = new InteractiveSlashCommandRegistry();

    @Getter
    private final Set<InteractiveSlashCommand> registered = new HashSet<>();

    @Override
    public Optional<InteractiveSlashCommand> get(String id) {
        return getRegistered()
                .stream()
                .filter(i -> i.getId().equals(id))
                .findFirst();
    }

    @Override
    public InteractiveSlashCommand register(InteractiveSlashCommand interactive) {
        if (exists(interactive.getId())) {
            val message = String.format("Found multiple InteractiveSlashCommand for id '%s'", interactive.getId());
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