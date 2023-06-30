package dev.pk7r.interactive.jda.factory;

import dev.pk7r.interactive.jda.annotation.button.MetaButton;
import dev.pk7r.interactive.jda.exception.InteractiveComponentCreationException;
import dev.pk7r.interactive.jda.exception.InteractiveComponentNotFoundException;
import dev.pk7r.interactive.jda.registry.InteractiveComponentRegistry;
import dev.pk7r.interactive.jda.support.common.Sender;
import dev.pk7r.interactive.jda.support.component.ButtonComponent;
import dev.pk7r.interactive.jda.support.definition.interactive.InteractiveButton;
import dev.pk7r.interactive.jda.utils.AnnotationUtils;
import dev.pk7r.interactive.jda.wrapper.MetaButtonWrapper;
import lombok.Builder;
import lombok.Getter;
import lombok.val;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Set;

@Getter
@Builder
class ButtonComponentFactory extends InteractiveFactory<ButtonComponent, InteractiveButton> {

    private JDA jda;

    private Guild guild;

    private Set<ButtonComponent> components;

    private InteractiveComponentRegistry<InteractiveButton> registry;

    @Override
    public InteractiveButton create(ButtonComponent component) {
        val componentClass = component.getClass();
        if (!AnnotationUtils.hasAnnotation(MetaButton.class, componentClass)) {
            val message = String.format("@MetaButton is mandatory on ButtonComponent (%s)",
                    componentClass.getName());
            throw new InteractiveComponentCreationException(message);
        }
        val annotation = AnnotationUtils.get(componentClass, MetaButton.class);
        if (annotation.idOrUrl().isEmpty()) {
            val message = String.format("Param 'idOrUrl' on @MetaButton is mandatory on ButtonComponent (%s)",
                    componentClass.getName());
            throw new InteractiveComponentCreationException(message);
        }
        return getRegistry().register(InteractiveButton
                .builder()
                .id(annotation.idOrUrl())
                .interactiveClass(componentClass)
                .interactiveComponent(component)
                .component(MetaButtonWrapper.getInstance().wrap(annotation))
                .build());
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        val button = event.getButton();
        if (Objects.isNull(button.getId())) return;
        val interactive = getRegistry().get(event.getButton().getId())
                .orElseThrow(() -> {
                    val message = String.format("Button with id '%s' not found", button.getId());
                    return new InteractiveComponentNotFoundException(message);
                });
        interactive.getInteractiveComponent()
                .interact(Sender.ofEvent(event))
                .accept(event.getInteraction());
    }
}