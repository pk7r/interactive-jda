package dev.pk7r.interactive.jda.spring.configuration;

import dev.pk7r.interactive.jda.factory.InteractiveFactoryManager;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;

@AutoConfiguration
@AutoConfigureAfter(JDAInteractiveFactoryManagerAutoConfiguration.class)
public class AutowireCapableJDAComponentsAutoConfiguration {

    public AutowireCapableJDAComponentsAutoConfiguration(InteractiveFactoryManager factoryManager,
                                                         ConfigurableListableBeanFactory beanFactory) {

    }
}