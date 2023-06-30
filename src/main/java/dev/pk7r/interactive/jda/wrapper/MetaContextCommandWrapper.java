package dev.pk7r.interactive.jda.wrapper;

import dev.pk7r.interactive.jda.annotation.command.context.ContextCommandType;
import dev.pk7r.interactive.jda.annotation.command.context.MetaContextCommand;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.Arrays;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MetaContextCommandWrapper implements MetaComponentWrapper<MetaContextCommand, CommandData> {

    @Getter
    private static final MetaComponentWrapper<MetaContextCommand, CommandData> instance = new MetaContextCommandWrapper();

    @Override
    public CommandData wrap(MetaContextCommand metaContextCommand) {
        CommandData data;
        if (metaContextCommand.type().equals(ContextCommandType.MESSAGE)) {
            data = Commands.message(metaContextCommand.name());
        } else {
            data = Commands.user(metaContextCommand.name());
        }
        data.setNSFW(metaContextCommand.nsfw());
        data.setGuildOnly(metaContextCommand.guildOnly());
        data.setDefaultPermissions(DefaultMemberPermissions.enabledFor(metaContextCommand.permissions()));
        Arrays.stream(metaContextCommand.localizationsMapping().name())
                .forEach(nameLocalization ->
                        data.setNameLocalization(nameLocalization.locale(), nameLocalization.value()));
        return data;
    }
}
