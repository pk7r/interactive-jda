package dev.pk7r.interactive.jda.factory;

import dev.pk7r.interactive.jda.InteractiveJDAManager;
import dev.pk7r.interactive.jda.registry.*;
import dev.pk7r.interactive.jda.support.component.*;
import dev.pk7r.interactive.jda.support.definition.interactive.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.util.Objects;

@Slf4j
@Getter
@Setter
@RequiredArgsConstructor
public class InteractiveFactoryManager {

    private boolean initialized = false;

    private final InteractiveJDAManager interactiveJDAManager;

    private InteractiveFactory<ProviderComponent, InteractiveProvider> providerInteractiveFactory;

    private InteractiveFactory<ButtonComponent, InteractiveButton> buttonInteractiveFactory;

    private InteractiveFactory<ModalComponent, InteractiveModal> modalInteractiveFactory;

    private PublishableInteractiveFactory<CommandData, ContextCommandComponent, InteractiveContextCommand> contextCommandInteractiveFactory;

    private PublishableInteractiveFactory<SlashCommandData, SlashCommandComponent, InteractiveSlashCommand> slashCommandInteractiveFactory;

    private InteractiveFactory<EntityMenuComponent, InteractiveEntityMenu> entityMenuInteractiveFactory;

    private InteractiveFactory<StringMenuComponent, InteractiveStringMenu> stringMenuInteractiveFactory;

    public InteractiveFactoryManager  initialize() {
        log.info("Guild id {}", interactiveJDAManager.getGuild().getId());
        log.info("JDA Token {}", interactiveJDAManager.getJda().getToken());
        if (isInitialized()) return this;
        if (Objects.isNull(getProviderInteractiveFactory())) {
            setProviderInteractiveFactory(new ProviderComponentFactory(
                    interactiveJDAManager.getJda(),
                    interactiveJDAManager.getGuild(),
                    interactiveJDAManager.getProviderComponents(),
                    InteractiveIterableProviderRegistry.getInstance()));
        }
        if (Objects.isNull(getButtonInteractiveFactory())) {
            setButtonInteractiveFactory(new ButtonComponentFactory(
                    interactiveJDAManager.getJda(),
                    interactiveJDAManager.getGuild(),
                    interactiveJDAManager.getButtonComponents(),
                    InteractiveButtonRegistry.getInstance()));
        }
        if (Objects.isNull(getStringMenuInteractiveFactory())) {
            setStringMenuInteractiveFactory(new StringMenuComponentFactory(
                    interactiveJDAManager.getJda(),
                    interactiveJDAManager.getGuild(),
                    interactiveJDAManager.getStringMenuComponents(),
                    InteractiveStringMenuRegistry.getInstance()));
        }
        if (Objects.isNull(getEntityMenuInteractiveFactory())) {
            setEntityMenuInteractiveFactory(new EntityMenuComponentFactory(
                    interactiveJDAManager.getJda(),
                    interactiveJDAManager.getGuild(),
                    interactiveJDAManager.getEntityMenuComponents(),
                    InteractiveEntityMenuRegistry.getInstance()));
        }
        if (Objects.isNull(getModalInteractiveFactory())) {
            setModalInteractiveFactory(new ModalComponentFactory(
                    interactiveJDAManager.getJda(),
                    interactiveJDAManager.getGuild(),
                    interactiveJDAManager.getModalComponents(),
                    InteractiveModalRegistry.getInstance()));
        }
        if (Objects.isNull(getSlashCommandInteractiveFactory())) {
            setSlashCommandInteractiveFactory(new SlashCommandFactory(
                    interactiveJDAManager.getJda(),
                    interactiveJDAManager.getGuild(),
                    interactiveJDAManager.getSlashCommandComponents(),
                    InteractiveSlashCommandRegistry.getInstance()));
        }
        if (Objects.isNull(getContextCommandInteractiveFactory())) {
            setContextCommandInteractiveFactory(new ContextCommandFactory(
                    interactiveJDAManager.getJda(),
                    interactiveJDAManager.getGuild(),
                    interactiveJDAManager.getContextCommandComponents(),
                    InteractiveContextCommandRegistry.getInstance()));
        }
        getProviderInteractiveFactory().initialize();
        getButtonInteractiveFactory().initialize();
        getStringMenuInteractiveFactory().initialize();
        getEntityMenuInteractiveFactory().initialize();
        getModalInteractiveFactory().initialize();
        getSlashCommandInteractiveFactory().initialize();
        getContextCommandInteractiveFactory().initialize();
        setInitialized(true);
        return this;
    }
}