package dev.pk7r.interactive.jda.factory;

import dev.pk7r.interactive.jda.annotation.menu.string.MetaStringMenu;
import dev.pk7r.interactive.jda.exception.InteractiveComponentCreationException;
import dev.pk7r.interactive.jda.exception.InteractiveComponentNotFoundException;
import dev.pk7r.interactive.jda.registry.InteractiveComponentRegistry;
import dev.pk7r.interactive.jda.support.common.Sender;
import dev.pk7r.interactive.jda.support.component.StringMenuComponent;
import dev.pk7r.interactive.jda.support.definition.interactive.InteractiveStringMenu;
import dev.pk7r.interactive.jda.utils.AnnotationUtils;
import dev.pk7r.interactive.jda.wrapper.MetaStringMenuWrapper;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.val;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Set;

@Getter
@Builder
class StringMenuComponentFactory extends InteractiveFactory<StringMenuComponent, InteractiveStringMenu> {

    private JDA jda;

    private Guild guild;

    @Singular
    private Set<StringMenuComponent> components;

    private InteractiveComponentRegistry<InteractiveStringMenu> registry;

    @Override
    public InteractiveStringMenu create(StringMenuComponent component) {
        val componentClass = component.getClass();
        if (!AnnotationUtils.hasAnnotation(MetaStringMenu.class, componentClass)) {
            val message = String.format("@MetaStringMenu is mandatory on StringMenuComponent (%s)",
                    componentClass.getName());
            throw new InteractiveComponentCreationException(message);
        }
        val annotation = AnnotationUtils.get(componentClass, MetaStringMenu.class);
        if (annotation.id().isEmpty()) {
            val message = String.format("Param 'id' on @MetaStringMenu is mandatory on StringMenuComponent (%s)",
                    componentClass.getName());
            throw new InteractiveComponentCreationException(message);
        }
        return getRegistry().register(InteractiveStringMenu
                .builder()
                .id(annotation.id())
                .interactiveClass(componentClass)
                .interactiveComponent(component)
                .component(MetaStringMenuWrapper.getInstance().wrap(annotation))
                .build());
    }

    @Override
    public void onStringSelectInteraction(@NotNull StringSelectInteractionEvent event) {
        val stringMenu = event.getComponent();
        if (Objects.isNull(stringMenu.getId())) return;
        val interactive = getRegistry().get(event.getComponent().getId())
                .orElseThrow(() -> {
                    val message = String.format("StringMenu with id '%s' not found", stringMenu.getId());
                    return new InteractiveComponentNotFoundException(message);
                });
        interactive.getInteractiveComponent()
                .interact(Sender.ofEvent(event), event.getSelectedOptions().toArray(SelectOption[]::new))
                .accept(event.getInteraction());
    }
}