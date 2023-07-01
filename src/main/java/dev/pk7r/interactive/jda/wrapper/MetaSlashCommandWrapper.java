package dev.pk7r.interactive.jda.wrapper;

import dev.pk7r.interactive.jda.annotation.command.slash.*;
import dev.pk7r.interactive.jda.registry.InteractiveIterableProviderRegistry;
import dev.pk7r.interactive.jda.support.component.SlashCommandComponent;
import dev.pk7r.interactive.jda.utils.AnnotationUtils;
import dev.pk7r.interactive.jda.utils.CommandUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.*;

import java.util.Arrays;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MetaSlashCommandWrapper implements MetaComponentWrapper<Class<? extends SlashCommandComponent>, SlashCommandData> {

    @Getter
    private static final MetaComponentWrapper<Class<? extends SlashCommandComponent>, SlashCommandData> instance = new MetaSlashCommandWrapper();

    @Override
    public SlashCommandData wrap(Class<? extends SlashCommandComponent> command) {
        if (CommandUtils.isSubCommand(command)) {
            val subCommand = AnnotationUtils.get(command, MetaSubCommand.class);
            return wrap(subCommand.mainCommand());
        }
        val mainCommand = AnnotationUtils.get(command, MetaCommand.class);
        val data = Commands.slash(mainCommand.name(), mainCommand.description());
        data.setNSFW(mainCommand.nsfw());
        data.setGuildOnly(mainCommand.guildOnly());
        Arrays.stream(mainCommand.localizationsMapping().name())
                .forEach(nameLocalization ->
                        data.setNameLocalization(nameLocalization.locale(), nameLocalization.value()));
        Arrays.stream(mainCommand.localizationsMapping().descriptions())
                .forEach(descLocalization ->
                        data.setNameLocalization(descLocalization.locale(), descLocalization.value()));
        data.setDefaultPermissions(DefaultMemberPermissions.enabledFor(mainCommand.permissions()));
        data.addOptions(Arrays.stream(mainCommand.options())
                .map(o -> wrapOption(o, mainCommand.name())).toList());
        data.addSubcommandGroups(Arrays.stream(mainCommand.subCommandGroups()).map(this::wrapSubCommandGroup).toList());
        data.addSubcommands(Arrays.stream(mainCommand.subCommands()).map(this::wrapSubCommand).toList());
        return data;
    }

    private SubcommandData wrapSubCommand(Class<? extends SlashCommandComponent> subCommand) {
        val annotation = AnnotationUtils.get(subCommand, MetaSubCommand.class);
        val data = new SubcommandData(annotation.name(), annotation.description());
        data.addOptions(Arrays.stream(annotation.options())
                .map(o -> wrapOption(o, CommandUtils.getFullCommandName(subCommand))).toList());
        Arrays.stream(annotation.localizationsMapping().name())
                .forEach(nameLocalization ->
                        data.setNameLocalization(nameLocalization.locale(), nameLocalization.value()));
        Arrays.stream(annotation.localizationsMapping().descriptions())
                .forEach(descLocalization ->
                        data.setNameLocalization(descLocalization.locale(), descLocalization.value()));
        return data;
    }

    private SubcommandGroupData wrapSubCommandGroup(MetaSubCommandGroup subCommandGroup) {
        val data = new SubcommandGroupData(subCommandGroup.name(), subCommandGroup.description());
        Arrays.stream(subCommandGroup.localizationsMapping().name())
                .forEach(nameLocalization ->
                        data.setNameLocalization(nameLocalization.locale(), nameLocalization.value()));
        Arrays.stream(subCommandGroup.localizationsMapping().descriptions())
                .forEach(descLocalization ->
                        data.setNameLocalization(descLocalization.locale(), descLocalization.value()));
        data.addSubcommands(Arrays.stream(subCommandGroup.subCommands()).map(this::wrapSubCommand).toList());
        return data;
    }

    private OptionData wrapOption(MetaOption option, String fullCommandName) {
        val data = new OptionData(option.type(), option.name(), option.description(),
                option.required(), !option.providerId().isEmpty());
        data.addChoices(Arrays.stream(option.choices()).map(this::wrapChoice).toList());
        val range = option.range();
        if (range.min() != -1) {
            data.setMinLength(range.min());
        }
        if (range.max() != -1) {
            data.setMaxLength(range.max());
        }
        Arrays.stream(option.localizationsMapping().name())
                .forEach(nameLocalization ->
                        data.setNameLocalization(nameLocalization.locale(), nameLocalization.value()));
        Arrays.stream(option.localizationsMapping().descriptions())
                .forEach(descLocalization ->
                        data.setNameLocalization(descLocalization.locale(), descLocalization.value()));
        if (option.channelTypes().length != 0) data.setChannelTypes(option.channelTypes());
        if (data.isAutoComplete()) {
            val provider = InteractiveIterableProviderRegistry.getInstance().get(option.providerId());
            if (provider.isPresent()) {
                provider.orElseThrow().getComponent().add(fullCommandName, option.name());
            } else {
                val message = String.format("IterableProvider with id '%s' not found", option.providerId());
                log.warn(message);
            }
        }
        return data;
    }

    private Command.Choice wrapChoice(MetaOptionChoice choice) {
        val data = new Command.Choice(choice.name(), choice.value());
        Arrays.stream(choice.localizationsMapping().name())
                .forEach(nameLocalization ->
                        data.setNameLocalization(nameLocalization.locale(), nameLocalization.value()));
        Arrays.stream(choice.localizationsMapping().descriptions())
                .forEach(descLocalization ->
                        data.setNameLocalization(descLocalization.locale(), descLocalization.value()));
        return data;
    }
}