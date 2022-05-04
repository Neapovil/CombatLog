package com.github.neapovil.combatlog.runnable;

import org.bukkit.scheduler.BukkitRunnable;

import com.github.neapovil.combatlog.CombatLog;

public final class Runnable extends BukkitRunnable
{
    private final CombatLog plugin = CombatLog.getInstance();

    @Override
    public void run()
    {
        plugin.getManager().removeIfExpired();
    }
}
