package com.github.neapovil.combatlog.manager;

import java.time.Instant;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import com.github.neapovil.combatlog.CombatLog;

public final class Manager
{
    private final CombatLog plugin = CombatLog.getInstance();
    private final NamespacedKey namespacedKey = new NamespacedKey(plugin, "time");

    public void add(Player player, boolean send)
    {
        if (send && !player.getPersistentDataContainer().has(this.namespacedKey))
        {
            final String message = plugin.getConfigMessage("yes_combatlog");
            player.sendMessage(plugin.getMiniMessage().deserialize(message));
        }

        final long time = Instant.now().plusSeconds(plugin.getCooldownPeriod()).toEpochMilli();

        player.getPersistentDataContainer().set(this.namespacedKey, PersistentDataType.LONG, time);
    }

    public void remove(Player player, boolean send)
    {
        if (send && player.getPersistentDataContainer().has(this.namespacedKey))
        {
            final String message = plugin.getConfigMessage("no_combatlog");
            player.sendMessage(plugin.getMiniMessage().deserialize(message));
        }

        player.getPersistentDataContainer().remove(this.namespacedKey);
    }

    public void clear()
    {
        plugin.getServer().getOnlinePlayers().forEach(i -> this.remove(i, false));
    }

    public boolean hasCombatlog(Player player)
    {
        return player.getPersistentDataContainer().has(this.namespacedKey);
    }

    public boolean isCombatlogExpired(Player player)
    {
        final long time = player.getPersistentDataContainer().get(this.namespacedKey, PersistentDataType.LONG);
        return Instant.now().isAfter(Instant.ofEpochMilli(time));
    }
}
