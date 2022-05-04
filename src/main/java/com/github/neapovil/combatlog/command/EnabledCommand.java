package com.github.neapovil.combatlog.command;

import com.github.neapovil.combatlog.CombatLog;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.BooleanArgument;
import dev.jorel.commandapi.arguments.LiteralArgument;

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

                    sender.sendMessage("Combatlog enabled: " + bool);
                })
                .register();
    }
}
