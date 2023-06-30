package dev.pk7r.interactive.jda.wrapper;

import dev.pk7r.interactive.jda.annotation.menu.MetaEntityMenu;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import net.dv8tion.jda.api.interactions.components.selections.EntitySelectMenu;

import java.util.Arrays;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MetaEntityMenuWrapper implements MetaComponentWrapper<MetaEntityMenu, EntitySelectMenu> {

    @Getter
    private static final MetaComponentWrapper<MetaEntityMenu, EntitySelectMenu> instance = new MetaEntityMenuWrapper();

    @Override
    public EntitySelectMenu wrap(MetaEntityMenu entityMenu) {
        val menu = EntitySelectMenu.create(entityMenu.id(), Arrays.stream(entityMenu.target()).collect(Collectors.toSet()));
        menu.setDisabled(entityMenu.disabled());
        menu.setChannelTypes(entityMenu.channelTypes());
        menu.setPlaceholder(entityMenu.placeholder());
        val range = entityMenu.range();
        if (range.min() != -1) {
            menu.setMinValues(range.min());
        }
        if (range.max() != -1) {
            menu.setMaxValues(range.max());
        }
        return menu.build();
    }
}