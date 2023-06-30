package dev.pk7r.interactive.jda.wrapper;

import dev.pk7r.interactive.jda.annotation.button.MetaButton;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MetaButtonWrapper implements MetaComponentWrapper<MetaButton, Button> {

    @Getter
    private static final MetaComponentWrapper<MetaButton, Button> instance = new MetaButtonWrapper();

    @Override
    public Button wrap(MetaButton metaButton) {
        val button = Button.of(metaButton.style(),
                metaButton.idOrUrl(), metaButton.label(), Emoji.fromUnicode(metaButton.unicodeEmoji()));
        return button.withDisabled(metaButton.disabled());
    }
}