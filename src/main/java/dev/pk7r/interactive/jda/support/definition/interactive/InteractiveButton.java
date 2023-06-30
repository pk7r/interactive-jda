package dev.pk7r.interactive.jda.support.definition.interactive;

import dev.pk7r.interactive.jda.support.component.ButtonComponent;
import dev.pk7r.interactive.jda.support.definition.InteractiveComponentDefinition;
import lombok.Builder;
import lombok.Getter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

@Getter
@Builder
public class InteractiveButton implements InteractiveComponentDefinition<ButtonComponent, Button> {

    private String id;

    private Button component;

    private ButtonComponent interactiveComponent;

    private Class<? extends ButtonComponent> interactiveClass;

}