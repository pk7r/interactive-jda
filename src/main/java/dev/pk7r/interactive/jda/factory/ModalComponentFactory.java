package dev.pk7r.interactive.jda.factory;

import dev.pk7r.interactive.jda.annotation.modal.MetaModal;
import dev.pk7r.interactive.jda.exception.InteractiveComponentCreationException;
import dev.pk7r.interactive.jda.exception.InteractiveComponentNotFoundException;
import dev.pk7r.interactive.jda.registry.InteractiveComponentRegistry;
import dev.pk7r.interactive.jda.support.common.Sender;
import dev.pk7r.interactive.jda.support.component.ModalComponent;
import dev.pk7r.interactive.jda.support.definition.interactive.InteractiveModal;
import dev.pk7r.interactive.jda.utils.AnnotationUtils;
import dev.pk7r.interactive.jda.wrapper.MetaModalWrapper;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.val;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.interactions.modals.ModalMapping;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

@Getter
@Builder
class ModalComponentFactory extends InteractiveFactory<ModalComponent, InteractiveModal> {

    private JDA jda;

    private Guild guild;

    @Singular
    private Set<ModalComponent> components;

    private InteractiveComponentRegistry<InteractiveModal> registry;

    @Override
    public InteractiveModal create(ModalComponent component) {
        val componentClass = component.getClass();
        if (!AnnotationUtils.hasAnnotation(MetaModal.class, componentClass)) {
            val message = String.format("@MetaModal is mandatory on ModalComponent (%s)",
                    componentClass.getName());
            throw new InteractiveComponentCreationException(message);
        }
        val annotation = AnnotationUtils.get(componentClass, MetaModal.class);
        if (annotation.id().isEmpty()) {
            val message = String.format("Param 'id' on @MetaModal is mandatory on ModalComponent (%s)",
                    componentClass.getName());
            throw new InteractiveComponentCreationException(message);
        }
        return getRegistry().register(InteractiveModal
                .builder()
                .id(annotation.id())
                .interactiveClass(componentClass)
                .interactiveComponent(component)
                .component(MetaModalWrapper.getInstance().wrap(annotation))
                .build());
    }

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        val interactive = getRegistry().get(event.getModalId())
                .orElseThrow(() -> {
                    val message = String.format("Modal with id '%s' not found", event.getModalId());
                    return new InteractiveComponentNotFoundException(message);
                });
        interactive.getInteractiveComponent()
                .interact(Sender.ofEvent(event), event.getValues().toArray(ModalMapping[]::new))
                .accept(event.getInteraction());
    }
}