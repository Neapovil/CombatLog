package com.github.neapovil.combatlog.runnable;

import java.time.Instant;

import org.bukkit.scheduler.BukkitRunnable;

import com.github.neapovil.combatlog.CombatLog;

import net.md_5.bungee.api.ChatColor;

public final class Runnable extends BukkitRunnable
{
    private final CombatLog plugin = CombatLog.getInstance();

    @Override
    public void run()
    {
        plugin.getManager().getPlayers().forEach((uuid, time) -> {
            if (Instant.now().isAfter(time))
            {
                plugin.getServer().getPlayer(uuid).sendMessage(ChatColor.GREEN + "You are no longer in combat!");
            }
        });

        plugin.getManager().getPlayers().values().removeIf(v -> Instant.now().isAfter(v));
    }
}
