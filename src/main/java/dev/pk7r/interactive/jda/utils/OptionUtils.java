package dev.pk7r.interactive.jda.utils;

import lombok.experimental.UtilityClass;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.modals.ModalMapping;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

@UtilityClass
public class OptionUtils {

    @Nullable
    public OptionMapping select(String name, OptionMapping... optionMappings) {
        return Arrays.stream(optionMappings).filter(o -> o.getName().equals(name)).findFirst().orElse(null);
    }

    @Nullable
    public ModalMapping select(String name, ModalMapping... optionMappings) {
        return Arrays.stream(optionMappings).filter(o -> o.getId().equals(name)).findFirst().orElse(null);
    }

    @Nullable
    public SelectOption select(String name, SelectOption... optionMappings) {
        return Arrays.stream(optionMappings).filter(o -> o.getValue().equals(name)).findFirst().orElse(null);
    }
}
