package dev.pk7r.interactive.jda.spring.configuration;

import dev.pk7r.interactive.jda.factory.InteractiveFactoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;

@AutoConfiguration
@AutoConfigureAfter(InteractiveJDAManagerAutoConfiguration.class)
public class AutowireCapableJDAComponentsAutoConfiguration {

    @Autowired
    public AutowireCapableJDAComponentsAutoConfiguration(InteractiveFactoryManager factoryManager,
                                                         ConfigurableListableBeanFactory beanFactory) {
        factoryManager.getButtonInteractiveFactory().getRegistry().getRegistered()
                .forEach(i -> beanFactory.registerSingleton(i.getId(), i.getComponent()));
        factoryManager.getModalInteractiveFactory().getRegistry().getRegistered()
                .forEach(i -> beanFactory.registerSingleton(i.getId(), i.getComponent()));
        factoryManager.getEntityMenuInteractiveFactory().getRegistry().getRegistered()
                .forEach(i -> beanFactory.registerSingleton(i.getId(), i.getComponent()));
        factoryManager.getStringMenuInteractiveFactory().getRegistry().getRegistered()
                .forEach(i -> beanFactory.registerSingleton(i.getId(), i.getComponent()));
    }
}