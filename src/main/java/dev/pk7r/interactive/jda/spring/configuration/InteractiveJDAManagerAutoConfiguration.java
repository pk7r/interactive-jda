package dev.pk7r.interactive.jda.spring.configuration;

import dev.pk7r.interactive.jda.InteractiveJDAManager;
import dev.pk7r.interactive.jda.support.component.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@AutoConfigureAfter(JDAEventPublisherAutoConfiguration.class)
public class InteractiveJDAManagerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public InteractiveJDAManager interactiveJDAManagerBean(JDA jda, Guild guild, ApplicationContext context) {
        return InteractiveJDAManager
                .builder()
                .jda(jda)
                .guild(guild)
                .providerComponents(context.getBeansOfType(ProviderComponent.class).values())
                .buttonComponents(context.getBeansOfType(ButtonComponent.class).values())
                .contextCommandComponents(context.getBeansOfType(ContextCommandComponent.class).values())
                .slashCommandComponents(context.getBeansOfType(SlashCommandComponent.class).values())
                .modalComponents(context.getBeansOfType(ModalComponent.class).values())
                .entityMenuComponents(context.getBeansOfType(EntityMenuComponent.class).values())
                .stringMenuComponents(context.getBeansOfType(StringMenuComponent.class).values())
                .build();
    }
}