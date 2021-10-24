package com.github.nearata.combatlog.listener;

import java.util.UUID;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.projectiles.ProjectileSource;

import com.github.nearata.combatlog.CombatLog;

public final class CombatLogListener implements Listener
{
    private final CombatLog plugin;

    public CombatLogListener(CombatLog plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void entityDamageByEntity(EntityDamageByEntityEvent event)
    {
        final Player victim = this.getPlayerFromEntity(event.getEntity());
        final Player killer = this.getPlayerFromEntity(event.getDamager());

        if (victim == null || killer == null)
        {
            return;
        }

        plugin.getCombatLogManager().add(victim);
        plugin.getCombatLogManager().add(killer);
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent event)
    {
        final UUID uuid = event.getPlayer().getUniqueId();

        if (plugin.getCombatLogManager().hasCooldown(uuid))
        {
            event.getPlayer().setHealth(0);
            plugin.getCombatLogManager().remove(uuid);
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
