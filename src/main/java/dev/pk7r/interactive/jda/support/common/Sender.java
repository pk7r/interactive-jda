package dev.pk7r.interactive.jda.support.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Sender {

    private User user;

    private Member member;

    private Sender(User user, Member member) {
        this.user = user;
        this.member = member;
    }

    public static <K extends GenericInteractionCreateEvent> Sender ofEvent(K event) {
        return new Sender(event.getUser(), event.getMember());
    }
}