package com.github.neapovil.combatlog.command;

import com.github.neapovil.combatlog.CombatLog;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.IntegerArgument;
import dev.jorel.commandapi.arguments.LiteralArgument;

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

                    sender.sendMessage("Cooldown changed to: " + seconds + "s");
                })
                .register();
    }
}
