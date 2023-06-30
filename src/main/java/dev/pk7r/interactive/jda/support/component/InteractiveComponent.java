package dev.pk7r.interactive.jda.support.component;

import dev.pk7r.interactive.jda.support.common.Sender;
import net.dv8tion.jda.api.entities.Mentions;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.modals.ModalMapping;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface InteractiveComponent<T> {

    default Consumer<T> interact(@NotNull Sender sender) {
        return i -> {};
    }

    default Consumer<T> interact(@NotNull Sender sender, @NotNull OptionMapping... options) {
        return i -> {};
    }

    default Consumer<T> interact(@NotNull Sender sender, @NotNull ModalMapping... options) {
        return i -> {};
    }

    default Consumer<T> interact(@NotNull Sender sender, @NotNull SelectOption... options) {
        return i -> {};
    }

    default Consumer<T> interact(@NotNull Sender sender, @NotNull Mentions mentions) {
        return i -> {};
    }

}