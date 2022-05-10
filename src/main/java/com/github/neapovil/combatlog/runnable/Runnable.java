package com.github.neapovil.combatlog.runnable;

import org.bukkit.scheduler.BukkitRunnable;

import com.github.neapovil.combatlog.CombatLog;

public final class Runnable extends BukkitRunnable
{
    private final CombatLog plugin = CombatLog.getInstance();

    @Override
    public void run()
    {
        plugin.getServer().getOnlinePlayers()
                .stream()
                .filter(i -> plugin.getManager().hasCombatlog(i))
                .filter(i -> plugin.getManager().isCombatlogExpired(i))
                .forEach(i -> plugin.getManager().remove(i, true));
    }
}
