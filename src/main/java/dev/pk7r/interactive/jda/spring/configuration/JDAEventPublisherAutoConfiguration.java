package dev.pk7r.interactive.jda.spring.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.PayloadApplicationEvent;

import java.util.Objects;

@AutoConfiguration
@RequiredArgsConstructor
@AutoConfigureAfter(JDAAutoConfiguration.class)
public class JDAEventPublisherAutoConfiguration {

    public JDAEventPublisherAutoConfiguration(JDA jda, ApplicationEventPublisher publisher) {
        jda.addEventListener(new ListenerAdapter() {
            @Override
            public void onGenericEvent(@NotNull GenericEvent event) {
                publisher.publishEvent(new PayloadApplicationEvent<>(jda, event));
            }
        });
    }
}