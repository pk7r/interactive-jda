package dev.pk7r.interactive.jda.spring.configuration;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.PayloadApplicationEvent;

@AutoConfiguration
@RequiredArgsConstructor
@AutoConfigureAfter(JDAAutoConfiguration.class)
public class JDAEventPublisherAutoConfiguration {

    @Autowired
    public JDAEventPublisherAutoConfiguration(JDA jda, ApplicationEventPublisher publisher) {
        jda.addEventListener(new ListenerAdapter() {
            @Override
            public void onGenericEvent(@NotNull GenericEvent event) {
                publisher.publishEvent(new PayloadApplicationEvent<>(jda, event));
            }
        });
    }
}