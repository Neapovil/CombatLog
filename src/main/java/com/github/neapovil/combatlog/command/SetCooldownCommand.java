package com.github.neapovil.combatlog.command;

import com.github.neapovil.combatlog.CombatLog;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.IntegerArgument;
import dev.jorel.commandapi.arguments.LiteralArgument;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

public final class SetCooldownCommand
{
    private static final CombatLog plugin = CombatLog.getInstance();

    public static final void register()
    {
        new CommandAPICommand("combatlog")
                .withPermission("combatlog.command")
                .withArguments(new LiteralArgument("setcooldown"))
                .withArguments(new IntegerArgument("seconds"))
                .executes((sender, args) -> {
                    final int seconds = (int) args[0];

                    plugin.setCooldownPeriod(seconds);

                    final String message = plugin.getConfigMessage("cooldown_changed");

                    sender.sendMessage(plugin.getMiniMessage().deserialize(message, Placeholder.parsed("new_period", "" + seconds)));
                })
                .register();
    }
}
