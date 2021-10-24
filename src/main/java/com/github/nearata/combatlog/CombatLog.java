package com.github.nearata.combatlog;

import org.bukkit.plugin.java.JavaPlugin;

import com.github.nearata.combatlog.listener.CombatLogListener;
import com.github.nearata.combatlog.manager.CombatLogManager;
import com.github.nearata.combatlog.runnable.CombatLogRunnable;

public final class CombatLog extends JavaPlugin
{
    private static CombatLog instance;
    private CombatLogManager combatLogManager;

    @Override
    public void onEnable()
    {
        instance = this;

        this.combatLogManager = new CombatLogManager(this);

        this.getServer().getPluginManager().registerEvents(new CombatLogListener(this), this);

        this.saveDefaultConfig();

        new CombatLogRunnable(this).runTaskTimer(this, 0, 20);
    }

    @Override
    public void onDisable()
    {
    }

    public static CombatLog getInstance()
    {
        return instance;
    }

    public CombatLogManager getCombatLogManager()
    {
        return this.combatLogManager;
    }
}
