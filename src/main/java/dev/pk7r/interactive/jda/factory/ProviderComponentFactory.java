package dev.pk7r.interactive.jda.factory;

import dev.pk7r.interactive.jda.annotation.stereotype.AutoCompletionProvider;
import dev.pk7r.interactive.jda.exception.InteractiveComponentCreationException;
import dev.pk7r.interactive.jda.registry.InteractiveComponentRegistry;
import dev.pk7r.interactive.jda.support.common.Sender;
import dev.pk7r.interactive.jda.support.component.ProviderComponent;
import dev.pk7r.interactive.jda.support.definition.interactive.InteractiveProvider;
import dev.pk7r.interactive.jda.utils.AnnotationUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.val;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.LinkedMultiValueMap;

import java.util.Set;

@Getter
@Builder
class ProviderComponentFactory extends InteractiveFactory<ProviderComponent, InteractiveProvider> {

    private JDA jda;

    private Guild guild;

    @Singular
    private Set<ProviderComponent> components;

    private InteractiveComponentRegistry<InteractiveProvider> registry;

    @Override
    public InteractiveProvider create(ProviderComponent component) {
        val componentClass = component.getClass();
        if (!AnnotationUtils.hasAnnotation(AutoCompletionProvider.class, componentClass)) {
            val message = String.format("@AutoCompletionProvider is mandatory on IterableProvider (%s)",
                    componentClass.getName());
            throw new InteractiveComponentCreationException(message);
        }
        val annotation = AnnotationUtils.get(componentClass, AutoCompletionProvider.class);
        if (annotation.id().isEmpty()) {
            val message = String.format("Param 'id' on @AutoCompletionProvider is mandatory on IterableProvider (%s)",
                    componentClass.getName());
            throw new InteractiveComponentCreationException(message);
        }
        return getRegistry().register(InteractiveProvider
                .builder()
                .id(annotation.id())
                .interactiveClass(componentClass)
                .interactiveComponent(component)
                .component(new LinkedMultiValueMap<>())
                .build());
    }

    @Override
    public void onCommandAutoCompleteInteraction(@NotNull CommandAutoCompleteInteractionEvent event) {
        val provider = getRegistry().getRegistered().stream()
                .filter(i -> i.getComponent().containsKey(event.getFullCommandName()))
                .filter(i -> i.getComponent()
                        .get(event.getFullCommandName()).stream()
                        .anyMatch(s -> event.getFocusedOption().getName().equals(s)))
                .findFirst().orElseThrow();
        provider.getInteractiveComponent().provide(Sender.ofEvent(event)).accept(event.getInteraction());
    }
}