package dev.pk7r.interactive.jda.wrapper;

import dev.pk7r.interactive.jda.annotation.modal.MetaActionRow;
import dev.pk7r.interactive.jda.annotation.modal.MetaItemComponent;
import dev.pk7r.interactive.jda.annotation.modal.MetaModal;
import dev.pk7r.interactive.jda.exception.InteractiveComponentCreationException;
import dev.pk7r.interactive.jda.registry.InteractiveButtonRegistry;
import dev.pk7r.interactive.jda.registry.InteractiveEntityMenuRegistry;
import dev.pk7r.interactive.jda.registry.InteractiveStringMenuRegistry;
import dev.pk7r.interactive.jda.support.definition.interactive.InteractiveButton;
import dev.pk7r.interactive.jda.support.definition.interactive.InteractiveEntityMenu;
import dev.pk7r.interactive.jda.support.definition.interactive.InteractiveStringMenu;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MetaModalWrapper implements MetaComponentWrapper<MetaModal, Modal> {

    @Getter
    private static final MetaComponentWrapper<MetaModal, Modal> instance = new MetaModalWrapper();

    @Override
    public Modal wrap(MetaModal metaModal) {
        val modal = Modal.create(metaModal.id(), metaModal.title());
        modal.addComponents(Arrays.stream(metaModal.actionRows()).map(this::wrapRow).toList());
        return modal.build();
    }

    private ActionRow wrapRow(MetaActionRow row) {
        val components = new ArrayList<ItemComponent>();
        val items = row.value();
        Arrays.stream(items).map(this::wrapItem).filter(Objects::nonNull).forEach(components::add);
        return ActionRow.of(components);
    }

    @Nullable
    private ItemComponent wrapItem(MetaItemComponent item) {
        switch (item.type()) {
            case TEXT_INPUT -> {
                val input = item.textInput();
                val inputObject = TextInput.create(input.id(), input.label(), input.style());
                if (!input.placeholder().isEmpty()) {
                    inputObject.setPlaceholder(input.placeholder());
                }
                inputObject.setRequired(input.required());
                val range = input.range();
                if (range.min() != -1) {
                    inputObject.setMinLength(range.min());
                }
                if (range.max() != -1) {
                    inputObject.setMaxLength(range.max());
                }
                if (!input.defaultValue().isEmpty()) inputObject.setValue(input.defaultValue());
                return inputObject.build();
            }
            case ENTITY_MENU -> {
                if (item.id().isEmpty()) {
                    val message = "Param 'id' on @MetaItemComponent is mandatory if ItemComponentType is ENTITY_MENU";
                    throw new InteractiveComponentCreationException(message);
                }
                val interactiveEntityMenu = InteractiveEntityMenuRegistry.getInstance().get(item.id());
                return interactiveEntityMenu.map(InteractiveEntityMenu::getComponent).orElse(null);
            }
            case STRING_MENU -> {
                if (item.id().isEmpty()) {
                    val message = "Param 'id' on @MetaItemComponent is mandatory if ItemComponentType is STRING_MENU";
                    throw new InteractiveComponentCreationException(message);
                }
                val interactiveStringMenu = InteractiveStringMenuRegistry.getInstance().get(item.id());
                return interactiveStringMenu.map(InteractiveStringMenu::getComponent).orElse(null);
            }
            default -> {
                if (item.id().isEmpty()) {
                    val message = "Param 'id' on @MetaItemComponent is mandatory if ItemComponentType is BUTTON";
                    throw new InteractiveComponentCreationException(message);
                }
                val interactive = InteractiveButtonRegistry.getInstance().get(item.id());
                return interactive.map(InteractiveButton::getComponent).orElse(null);
            }
        }
    }
}