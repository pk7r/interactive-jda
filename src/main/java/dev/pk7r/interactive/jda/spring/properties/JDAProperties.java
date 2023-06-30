package dev.pk7r.interactive.jda.spring.properties;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

@Getter
@Setter(AccessLevel.PROTECTED)
@ConfigurationProperties("spring.jda")
public class JDAProperties {

    private String token;

    private OnlineStatus onlineStatus = OnlineStatus.ONLINE;

    private Set<CacheFlag> cacheFlags;

    private String guildId;

}