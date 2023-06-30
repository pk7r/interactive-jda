package dev.pk7r.interactive.jda.support.definition.interactive;

import dev.pk7r.interactive.jda.support.component.ModalComponent;
import dev.pk7r.interactive.jda.support.definition.InteractiveComponentDefinition;
import lombok.Builder;
import lombok.Getter;
import net.dv8tion.jda.api.interactions.modals.Modal;

@Getter
@Builder
public class InteractiveModal implements InteractiveComponentDefinition<ModalComponent, Modal> {

    private String id;

    private Modal component;

    private ModalComponent interactiveComponent;

    private Class<? extends ModalComponent> interactiveClass;

}