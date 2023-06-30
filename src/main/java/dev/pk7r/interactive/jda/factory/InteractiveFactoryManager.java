package dev.pk7r.interactive.jda.factory;

import dev.pk7r.interactive.jda.InteractiveJDAManager;
import dev.pk7r.interactive.jda.registry.*;
import dev.pk7r.interactive.jda.support.component.*;
import dev.pk7r.interactive.jda.support.definition.interactive.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class InteractiveFactoryManager {

    private boolean initialized;

    private final InteractiveJDAManager interactiveJDAManager;

    private InteractiveFactory<ProviderComponent, InteractiveProvider> providerInteractiveFactory =
            ProviderComponentFactory
                    .builder()
                    .registry(InteractiveIterableProviderRegistry.getInstance())
                    .guild(getInteractiveJDAManager().getGuild())
                    .jda(getInteractiveJDAManager().getJda())
                    .components(getInteractiveJDAManager().getProviderComponents())
                    .build();

    private InteractiveFactory<ButtonComponent, InteractiveButton> buttonInteractiveFactory =
            ButtonComponentFactory
            .builder()
            .registry(InteractiveButtonRegistry.getInstance())
            .guild(getInteractiveJDAManager().getGuild())
            .jda(getInteractiveJDAManager().getJda())
            .components(getInteractiveJDAManager().getButtonComponents())
            .build();

    private InteractiveFactory<ModalComponent, InteractiveModal> modalInteractiveFactory =
            ModalComponentFactory
            .builder()
            .registry(InteractiveModalRegistry.getInstance())
            .guild(getInteractiveJDAManager().getGuild())
            .jda(getInteractiveJDAManager().getJda())
            .components(getInteractiveJDAManager().getModalComponents())
            .build();

    private PublishableInteractiveFactory<ContextCommandComponent, InteractiveContextCommand> contextCommandInteractiveFactory =
            ContextCommandFactory
                    .builder()
                    .registry(InteractiveContextCommandRegistry.getInstance())
                    .guild(getInteractiveJDAManager().getGuild())
                    .jda(getInteractiveJDAManager().getJda())
                    .components(getInteractiveJDAManager().getContextCommandComponents())
                    .build();

    private PublishableInteractiveFactory<SlashCommandComponent, InteractiveSlashCommand> slashCommandInteractiveFactory =
            SlashCommandFactory
                    .builder()
                    .registry(InteractiveSlashCommandRegistry.getInstance())
                    .guild(getInteractiveJDAManager().getGuild())
                    .jda(getInteractiveJDAManager().getJda())
                    .components(getInteractiveJDAManager().getSlashCommandComponents())
                    .build();

    private InteractiveFactory<EntityMenuComponent, InteractiveEntityMenu> entityMenuInteractiveFactory =
            EntityMenuComponentFactory
                    .builder()
                    .registry(InteractiveEntityMenuRegistry.getInstance())
                    .guild(getInteractiveJDAManager().getGuild())
                    .jda(getInteractiveJDAManager().getJda())
                    .components(getInteractiveJDAManager().getEntityMenuComponents())
                    .build();

    private InteractiveFactory<StringMenuComponent, InteractiveStringMenu> stringMenuInteractiveFactory =
            StringMenuComponentFactory
                    .builder()
                    .registry(InteractiveStringMenuRegistry.getInstance())
                    .guild(getInteractiveJDAManager().getGuild())
                    .jda(getInteractiveJDAManager().getJda())
                    .components(getInteractiveJDAManager().getStringMenuComponents())
                    .build();

    public InteractiveFactoryManager initialize() {
        if (isInitialized()) return this;
        getProviderInteractiveFactory().initialize();
        getButtonInteractiveFactory().initialize();
        getModalInteractiveFactory().initialize();
        getStringMenuInteractiveFactory().initialize();
        getEntityMenuInteractiveFactory().initialize();
        getSlashCommandInteractiveFactory().initialize();
        getContextCommandInteractiveFactory().initialize();
        setInitialized(true);
        return this;
    }
}