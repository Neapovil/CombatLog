package com.github.neapovil.combatlog;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

import com.electronwill.nightconfig.core.file.FileConfig;
import com.github.neapovil.combatlog.command.EnabledCommand;
import com.github.neapovil.combatlog.command.SetCooldownCommand;
import com.github.neapovil.combatlog.listener.Listener;
import com.github.neapovil.combatlog.manager.Manager;
import com.github.neapovil.combatlog.runnable.Runnable;

public final class CombatLog extends JavaPlugin
{
    private static CombatLog instance;
    private Manager manager;
    private FileConfig config;

    @Override
    public void onEnable()
    {
        instance = this;

        this.saveResource("config.json", false);

        this.config = FileConfig.builder(new File(this.getDataFolder(), "config.json"))
                .autoreload()
                .autosave()
                .build();
        this.config.load();

        this.manager = new Manager();

        this.getServer().getPluginManager().registerEvents(new Listener(), this);

        new Runnable().runTaskTimer(this, 0, 20);

        EnabledCommand.register();
        SetCooldownCommand.register();
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

    public boolean isCombatlogEnabled()
    {
        return this.config.get("config.enabled");
    }

    public int getCooldownPeriod()
    {
        return this.config.get("config.cooldown");
    }

    public void setCooldownPeriod(int seconds)
    {
        this.config.set("config.cooldown", seconds);
    }

    public void setCooldownStatus(boolean bool)
    {
        this.config.set("config.enabled", bool);
    }
}
