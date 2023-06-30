package dev.pk7r.interactive.jda.support.definition.interactive;

import dev.pk7r.interactive.jda.support.component.ProviderComponent;
import dev.pk7r.interactive.jda.support.definition.InteractiveComponentDefinition;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.MultiValueMap;

@Getter
@Builder
public class InteractiveProvider implements InteractiveComponentDefinition<ProviderComponent, MultiValueMap<String, String>> {

    private String id;

    private MultiValueMap<String, String> component;

    private ProviderComponent interactiveComponent;

    private Class<? extends ProviderComponent> interactiveClass;

}