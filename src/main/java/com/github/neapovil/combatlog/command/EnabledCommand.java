package com.github.neapovil.combatlog.command;

import com.github.neapovil.combatlog.CombatLog;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.BooleanArgument;
import dev.jorel.commandapi.arguments.LiteralArgument;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

public final class EnabledCommand
{
    private static final CombatLog plugin = CombatLog.getInstance();

    public static final void register()
    {
        new CommandAPICommand("combatlog")
                .withPermission("combatlog.command")
                .withArguments(new LiteralArgument("enabled"))
                .withArguments(new BooleanArgument("bool"))
                .executes((sender, args) -> {
                    final boolean bool = (boolean) args[0];

                    plugin.getManager().clear();

                    plugin.setCooldownStatus(bool);

                    final String message = plugin.getConfigMessage("status_changed");

                    sender.sendMessage(plugin.getMiniMessage().deserialize(message, Placeholder.parsed("new_status", "" + bool)));
                })
                .register();
    }
}
