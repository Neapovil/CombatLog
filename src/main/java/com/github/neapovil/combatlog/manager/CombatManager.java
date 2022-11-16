package com.github.neapovil.combatlog.manager;

import java.time.Instant;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import com.github.neapovil.combatlog.CombatLog;

public final class CombatManager
{
    private final CombatLog plugin;
    private final NamespacedKey namespacedKey;

    public CombatManager(CombatLog plugin)
    {
        this.plugin = plugin;
        this.namespacedKey = new NamespacedKey(plugin, "time");
    }

    public void add(Player player)
    {
        final long time = Instant.now().plusSeconds(plugin.getConfigManager().getCooldownPeriod()).toEpochMilli();

        player.getPersistentDataContainer().set(this.namespacedKey, PersistentDataType.LONG, time);
    }

    public void remove(Player player)
    {
        player.getPersistentDataContainer().remove(this.namespacedKey);
    }

    public void clearAll()
    {
        plugin.getServer().getOnlinePlayers().forEach(i -> this.remove(i));
    }

    public boolean has(Player player)
    {
        return player.getPersistentDataContainer().has(this.namespacedKey);
    }

    public boolean isExpired(Player player)
    {
        if (!this.has(player))
        {
            return true;
        }

        final long time = player.getPersistentDataContainer().get(this.namespacedKey, PersistentDataType.LONG);

        return Instant.now().isAfter(Instant.ofEpochMilli(time));
    }
}
