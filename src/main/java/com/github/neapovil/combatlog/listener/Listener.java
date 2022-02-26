package com.github.neapovil.combatlog.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.projectiles.ProjectileSource;

import com.github.neapovil.combatlog.CombatLog;

public final class Listener implements org.bukkit.event.Listener
{
    private final CombatLog plugin = CombatLog.getInstance();

    @EventHandler
    public void entityDamageByEntity(EntityDamageByEntityEvent event)
    {
        final Player victim = this.getPlayerFromEntity(event.getEntity());
        final Player killer = this.getPlayerFromEntity(event.getDamager());

        if (victim == null || killer == null)
        {
            return;
        }

        plugin.getManager().add(victim);
        plugin.getManager().add(killer);
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent event)
    {
        if (plugin.getManager().getPlayers().containsKey(event.getPlayer().getUniqueId()))
        {
            event.getPlayer().setHealth(0);
            plugin.getManager().remove(event.getPlayer());
        }
    }

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
