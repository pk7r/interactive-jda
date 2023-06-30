package dev.pk7r.interactive.jda.spring.configuration;

import dev.pk7r.interactive.jda.spring.properties.JDAProperties;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Objects;
import java.util.stream.Collectors;

@AutoConfiguration
@RequiredArgsConstructor
@EnableConfigurationProperties(JDAProperties.class)
public class JDAAutoConfiguration {

    private final JDAProperties properties;

    @Bean
    @SneakyThrows
    @ConditionalOnMissingBean
    public JDA jdaBean() {
        val requiredIntents = properties.getCacheFlags()
                .stream()
                .map(CacheFlag::getRequiredIntent)
                .filter(Objects::nonNull)
                .collect(Collectors.toUnmodifiableSet());
        return JDABuilder.createDefault(properties.getToken())
                .setStatus(properties.getOnlineStatus())
                .enableCache(properties.getCacheFlags())
                .enableIntents(requiredIntents)
                .build()
                .awaitReady();
    }

    @Bean
    @ConditionalOnMissingBean
    public Guild guildBean(JDA jda) {
        return jda.getGuildById(properties.getGuildId());
    }
}