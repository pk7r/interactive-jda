package dev.pk7r.interactive.jda.factory;

import dev.pk7r.interactive.jda.annotation.menu.MetaEntityMenu;
import dev.pk7r.interactive.jda.exception.InteractiveComponentCreationException;
import dev.pk7r.interactive.jda.exception.InteractiveComponentNotFoundException;
import dev.pk7r.interactive.jda.registry.InteractiveComponentRegistry;
import dev.pk7r.interactive.jda.support.common.Sender;
import dev.pk7r.interactive.jda.support.component.EntityMenuComponent;
import dev.pk7r.interactive.jda.support.definition.interactive.InteractiveEntityMenu;
import dev.pk7r.interactive.jda.utils.AnnotationUtils;
import dev.pk7r.interactive.jda.wrapper.MetaEntityMenuWrapper;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.val;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;

import java.util.Objects;
import java.util.Set;

@Getter
@Builder
class EntityMenuComponentFactory extends InteractiveFactory<EntityMenuComponent, InteractiveEntityMenu> {

    private JDA jda;

    private Guild guild;

    @Singular
    private Set<EntityMenuComponent> components;

    private InteractiveComponentRegistry<InteractiveEntityMenu> registry;

    @Override
    public InteractiveEntityMenu create(EntityMenuComponent component) {
        val componentClass = component.getClass();
        if (!AnnotationUtils.hasAnnotation(MetaEntityMenu.class, componentClass)) {
            val message = String.format("@EntityMenu is mandatory on EntityMenuComponent (%s)",
                    componentClass.getName());
            throw new InteractiveComponentCreationException(message);
        }
        val annotation = AnnotationUtils.get(componentClass, MetaEntityMenu.class);
        if (annotation.id().isEmpty()) {
            val message = String.format("Param 'id' on @EntityMenu is mandatory on EntityMenuComponent (%s)",
                    componentClass.getName());
            throw new InteractiveComponentCreationException(message);
        }
        return getRegistry().register(InteractiveEntityMenu
                .builder()
                .id(annotation.id())
                .interactiveClass(componentClass)
                .interactiveComponent(component)
                .component(MetaEntityMenuWrapper.getInstance().wrap(annotation))
                .build());
    }

    @Override
    public void onEntitySelectInteraction(EntitySelectInteractionEvent event) {
        val entityMenu = event.getComponent();
        if (Objects.isNull(entityMenu.getId())) return;
        val interactive = getRegistry().get(event.getComponent().getId())
                .orElseThrow(() -> {
                    val message = String.format("EntityMenu with id '%s' not found", entityMenu.getId());
                    return new InteractiveComponentNotFoundException(message);
                });
        interactive.getInteractiveComponent()
                .interact(Sender.ofEvent(event), event.getMentions())
                .accept(event.getInteraction());
    }
}