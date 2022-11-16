package com.github.neapovil.combatlog.command;

import com.github.neapovil.combatlog.CombatLog;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.BooleanArgument;
import dev.jorel.commandapi.arguments.LiteralArgument;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

public final class EnabledCommand
{
    public static final void register()
    {
        new CommandAPICommand("combatlog")
                .withPermission("combatlog.command")
                .withArguments(new LiteralArgument("enabled"))
                .withArguments(new BooleanArgument("bool"))
                .executes((sender, args) -> {
                    final boolean bool = (boolean) args[0];

                    final CombatLog plugin = CombatLog.getInstance();

                    plugin.getCombatManager().clearAll();

                    plugin.getConfigManager().setCooldownStatus(bool);

                    final String message = plugin.getMessageManager().getMessage("status_changed");

                    sender.sendMessage(plugin.getMessageManager().deserialize(message, Placeholder.parsed("new_status", "" + bool)));
                })
                .register();
    }
}
