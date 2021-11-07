package com.github.nearata.combatlog.manager;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.github.nearata.combatlog.CombatLog;

import net.md_5.bungee.api.ChatColor;

public final class Manager
{
    private final CombatLog plugin = CombatLog.getInstance();
    private Map<UUID, Instant> players = new HashMap<>();

    public Map<UUID, Instant> getPlayers()
    {
        return this.players;
    }

    public void add(Player player)
    {
        final Instant prev = players.put(player.getUniqueId(), Instant.now().plusSeconds(plugin.getConfig().getInt("cooldown")));

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
}
