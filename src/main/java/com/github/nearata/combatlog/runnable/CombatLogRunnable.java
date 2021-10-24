package com.github.nearata.combatlog.runnable;

import org.bukkit.scheduler.BukkitRunnable;

import com.github.nearata.combatlog.CombatLog;

public final class CombatLogRunnable extends BukkitRunnable
{
    private final CombatLog plugin;

    public CombatLogRunnable(CombatLog plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public void run()
    {
        plugin.getCombatLogManager().removeExpired();
    }
}
