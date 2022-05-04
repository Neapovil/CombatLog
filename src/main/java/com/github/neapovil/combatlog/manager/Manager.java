package com.github.neapovil.combatlog.manager;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.github.neapovil.combatlog.CombatLog;

import net.md_5.bungee.api.ChatColor;

public final class Manager
{
    private final CombatLog plugin = CombatLog.getInstance();
    private final Map<UUID, Instant> players = new HashMap<>();

    public void add(Player player)
    {
        final Instant prev = players.put(player.getUniqueId(), Instant.now().plusSeconds(plugin.getCooldownPeriod()));

        if (prev == null)
        {
            player.sendMessage(ChatColor.RED + "You are now in combat!");
        }
    }

    public void remove(Player player)
    {
        final Instant prev = players.remove(player.getUniqueId());

        if (prev != null)
        {
            player.sendMessage(ChatColor.GREEN + "You are no longer in combat!");
        }
    }

    public void clear()
    {
        this.players.clear();
    }

    public boolean isInCombatlog(Player player)
    {
        return this.players.containsKey(player.getUniqueId());
    }

    public void removeIfExpired()
    {
        this.players.forEach((uuid, time) -> {
            if (Instant.now().isAfter(time))
            {
                plugin.getServer().getPlayer(uuid).sendMessage(ChatColor.GREEN + "You are no longer in combat!");
            }
        });

        this.players.values().removeIf(v -> Instant.now().isAfter(v));
    }
}
