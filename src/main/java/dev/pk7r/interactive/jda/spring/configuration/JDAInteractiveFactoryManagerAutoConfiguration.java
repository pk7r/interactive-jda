package dev.pk7r.interactive.jda.spring.configuration;

import dev.pk7r.interactive.jda.InteractiveJDAManager;
import dev.pk7r.interactive.jda.factory.InteractiveFactoryManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@RequiredArgsConstructor
@AutoConfigureAfter(InteractiveJDAManagerAutoConfiguration.class)
public class JDAInteractiveFactoryManagerAutoConfiguration {

    private final InteractiveJDAManager manager;

    @Bean
    @ConditionalOnMissingBean
    public InteractiveFactoryManager interactiveFactoryManagerBean() {
        return new InteractiveFactoryManager(manager).initialize();
    }
}