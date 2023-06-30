package dev.pk7r.interactive.jda;

import dev.pk7r.interactive.jda.support.component.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

@Getter
@Builder
public class InteractiveJDAManager {

    @NotNull
    private JDA jda;

    @NotNull
    private Guild guild;

    @Singular
    private Set<ProviderComponent> providerComponents;

    @Singular
    private Set<SlashCommandComponent> slashCommandComponents;

    @Singular
    private Set<ContextCommandComponent> contextCommandComponents;

    @Singular
    private Set<ButtonComponent> buttonComponents;

    @Singular
    private Set<ModalComponent> modalComponents;

    @Singular
    private Set<EntityMenuComponent> entityMenuComponents;

    @Singular
    private Set<StringMenuComponent> stringMenuComponents;

}