package com.github.nearata.combatlog;

import org.bukkit.plugin.java.JavaPlugin;

import com.github.nearata.combatlog.listener.Listener;
import com.github.nearata.combatlog.manager.Manager;
import com.github.nearata.combatlog.runnable.Runnable;

public final class CombatLog extends JavaPlugin
{
    private static CombatLog instance;
    private Manager manager;

    @Override
    public void onEnable()
    {
        instance = this;

        this.manager = new Manager();

        this.getServer().getPluginManager().registerEvents(new Listener(), this);

        this.saveDefaultConfig();

        new Runnable().runTaskTimer(this, 0, 20);
    }

    @Override
    public void onDisable()
    {
    }

    public static CombatLog getInstance()
    {
        return instance;
    }

    public Manager getManager()
    {
        return this.manager;
    }
}
