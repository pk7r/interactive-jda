package dev.pk7r.interactive.jda.support.definition.interactive;

import dev.pk7r.interactive.jda.support.component.EntityMenuComponent;
import dev.pk7r.interactive.jda.support.definition.InteractiveComponentDefinition;
import lombok.Builder;
import lombok.Getter;
import net.dv8tion.jda.api.interactions.components.selections.EntitySelectMenu;

@Getter
@Builder
public class InteractiveEntityMenu implements InteractiveComponentDefinition<EntityMenuComponent, EntitySelectMenu> {

    private String id;

    private EntitySelectMenu component;

    private EntityMenuComponent interactiveComponent;

    private Class<? extends EntityMenuComponent> interactiveClass;

}