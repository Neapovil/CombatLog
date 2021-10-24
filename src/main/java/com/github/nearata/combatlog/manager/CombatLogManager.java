package com.github.nearata.combatlog.manager;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.github.nearata.combatlog.CombatLog;

import net.md_5.bungee.api.ChatColor;

public final class CombatLogManager
{
    private final CombatLog plugin;
    private Map<UUID, Instant> players = new HashMap<>();

    public CombatLogManager(CombatLog plugin)
    {
        this.plugin = plugin;
    }

    public void add(Player player)
    {
        final Instant prev = players.put(player.getUniqueId(), Instant.now().plusSeconds(plugin.getConfig().getInt("cooldown")));

        if (prev == null)
        {
            player.sendMessage(ChatColor.RED + "You are now in combat!");
        }
    }

    public void removeExpired()
    {
        players.forEach((uuid, time) -> {
            if (Instant.now().isAfter(time))
            {
                plugin.getServer().getPlayer(uuid).sendMessage(ChatColor.GREEN + "You are no longer in combat!");
            }
        });
        players.values().removeIf(v -> Instant.now().isAfter(v));
    }

    public boolean hasCooldown(UUID uuid)
    {
        return players.get(uuid) != null;
    }

    public void remove(UUID uuid)
    {
        players.remove(uuid);
    }
}
