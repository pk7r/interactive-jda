package dev.pk7r.interactive.jda.spring.configuration;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.ApplicationContext;

import java.util.Collection;

@AutoConfiguration
@AutoConfigureAfter(JDAAutoConfiguration.class)
public class ListenerAdapterAutoConfiguration {

    public ListenerAdapterAutoConfiguration(ApplicationContext context, JDA jda) {
        Collection<ListenerAdapter> listenerAdapters = context.getBeansOfType(ListenerAdapter.class).values();
        for (ListenerAdapter listenerAdapter : listenerAdapters) {
            jda.addEventListener(listenerAdapter);
        }
    }
}