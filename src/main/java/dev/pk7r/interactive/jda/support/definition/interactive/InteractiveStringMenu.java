package dev.pk7r.interactive.jda.support.definition.interactive;

import dev.pk7r.interactive.jda.support.component.StringMenuComponent;
import dev.pk7r.interactive.jda.support.definition.InteractiveComponentDefinition;
import lombok.Builder;
import lombok.Getter;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

@Getter
@Builder
public class InteractiveStringMenu implements InteractiveComponentDefinition<StringMenuComponent, StringSelectMenu> {

    private String id;

    private StringSelectMenu component;

    private StringMenuComponent interactiveComponent;

    private Class<? extends StringMenuComponent> interactiveClass;

}