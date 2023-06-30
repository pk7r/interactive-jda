package dev.pk7r.interactive.jda.wrapper;

import dev.pk7r.interactive.jda.annotation.menu.string.MetaSelectOption;
import dev.pk7r.interactive.jda.annotation.menu.string.MetaStringMenu;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

import java.util.Arrays;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MetaStringMenuWrapper implements MetaComponentWrapper<MetaStringMenu, StringSelectMenu> {

    @Getter
    private static final MetaComponentWrapper<MetaStringMenu, StringSelectMenu> instance = new MetaStringMenuWrapper();

    @Override
    public StringSelectMenu wrap(MetaStringMenu stringMenu) {
        val menu = StringSelectMenu.create(stringMenu.id());
        menu.setDisabled(stringMenu.disabled());
        menu.addOptions(Arrays.stream(stringMenu.options()).map(this::wrapOption).toList());
        menu.setPlaceholder(stringMenu.placeholder());
        val range = stringMenu.range();
        if (range.min() != -1) {
            menu.setMinValues(range.min());
        }
        if (range.max() != -1) {
            menu.setMaxValues(range.max());
        }
        return menu.build();
    }

    private SelectOption wrapOption(MetaSelectOption selectOption) {
        return SelectOption.of(selectOption.label(), selectOption.value())
                .withDefault(selectOption.defaultOption())
                .withDescription(selectOption.description())
                .withEmoji(Emoji.fromUnicode(selectOption.unicodeEmoji()));
    }
}