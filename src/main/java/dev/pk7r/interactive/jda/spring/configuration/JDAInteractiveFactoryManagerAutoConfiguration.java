package dev.pk7r.interactive.jda.spring.configuration;

import dev.pk7r.interactive.jda.InteractiveJDAManager;
import dev.pk7r.interactive.jda.factory.InteractiveFactoryManager;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@AutoConfigureAfter(InteractiveJDAManagerAutoConfiguration.class)
public class JDAInteractiveFactoryManagerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public InteractiveFactoryManager interactiveFactoryManagerBean(InteractiveJDAManager manager) {
        return new InteractiveFactoryManager(manager).initialize();
    }
}