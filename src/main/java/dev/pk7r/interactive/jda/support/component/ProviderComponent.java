package dev.pk7r.interactive.jda.support.component;

import dev.pk7r.interactive.jda.support.common.Interactive;
import dev.pk7r.interactive.jda.support.common.Sender;
import net.dv8tion.jda.api.interactions.commands.CommandAutoCompleteInteraction;

import java.util.function.Consumer;

public interface ProviderComponent extends Interactive {

    Consumer<CommandAutoCompleteInteraction> provide(Sender sender);

}