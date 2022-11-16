package com.github.neapovil.combatlog.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.jetbrains.annotations.Nullable;

import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import com.github.neapovil.combatlog.CombatLog;

public final class Listener implements org.bukkit.event.Listener
{
    private final CombatLog plugin = CombatLog.getInstance();

    @EventHandler
    public void entityDamageByEntity(EntityDamageByEntityEvent event)
    {
        if (!plugin.getConfigManager().isCombatlogEnabled())
        {
            return;
        }

        final Player victim = this.getPlayerFromEntity(event.getEntity());
        final Player killer = this.getPlayerFromEntity(event.getDamager());

        if (victim == null || killer == null)
        {
            return;
        }

        if (!plugin.getCombatManager().has(victim))
        {
            final String message = plugin.getMessageManager().getMessage("yes_combatlog");

            victim.sendMessage(plugin.getMessageManager().deserialize(message));
        }

        if (!plugin.getCombatManager().has(killer))
        {
            final String message = plugin.getMessageManager().getMessage("yes_combatlog");

            killer.sendMessage(plugin.getMessageManager().deserialize(message));
        }

        plugin.getCombatManager().add(victim);
        plugin.getCombatManager().add(killer);
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent event)
    {
        if (!plugin.getConfigManager().isCombatlogEnabled())
        {
            return;
        }

        if (!plugin.getCombatManager().isExpired(event.getPlayer()))
        {
            event.getPlayer().setHealth(0);
            plugin.getCombatManager().remove(event.getPlayer());
        }
    }

    @EventHandler
    public void serverTick(ServerTickStartEvent event)
    {
        if (event.getTickNumber() % 20 != 0)
        {
            return;
        }

        for (Player player : plugin.getServer().getOnlinePlayers())
        {
            if (!plugin.getCombatManager().isExpired(player))
            {
                continue;
            }

            plugin.getCombatManager().remove(player);

            final String message = plugin.getMessageManager().getMessage("no_combatlog");

            player.sendMessage(plugin.getMessageManager().deserialize(message));
        }
    }

    @Nullable
    private final Player getPlayerFromEntity(Entity entity)
    {
        if (entity instanceof Player)
        {
            return (Player) entity;
        }

        if (entity instanceof Projectile)
        {
            final Projectile projectile = (Projectile) entity;
            final ProjectileSource shooter = projectile.getShooter();

            if (shooter instanceof Player)
            {
                return (Player) shooter;
            }
        }

        return null;
    }
}
