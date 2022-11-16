package com.github.neapovil.combatlog;

import org.bukkit.plugin.java.JavaPlugin;

import com.electronwill.nightconfig.core.file.FileConfig;
import com.github.neapovil.combatlog.command.EnabledCommand;
import com.github.neapovil.combatlog.command.SetCooldownCommand;
import com.github.neapovil.combatlog.listener.Listener;
import com.github.neapovil.combatlog.manager.ConfigManager;
import com.github.neapovil.combatlog.manager.MessageManager;
import com.github.neapovil.combatlog.manager.CombatManager;

public final class CombatLog extends JavaPlugin
{
    private static CombatLog instance;
    private CombatManager combatManager;
    private ConfigManager configManager;
    private MessageManager messageManager;

    @Override
    public void onEnable()
    {
        instance = this;

        this.combatManager = new CombatManager(this);
        this.configManager = new ConfigManager(this.initConfig("config.json"));
        this.messageManager = new MessageManager(this.initConfig("messages.json"));

        this.getServer().getPluginManager().registerEvents(new Listener(), this);

        EnabledCommand.register();
        SetCooldownCommand.register();
    }

    @Override
    public void onDisable()
    {
        this.combatManager.clearAll();
    }

    public static CombatLog getInstance()
    {
        return instance;
    }

    public CombatManager getCombatManager()
    {
        return this.combatManager;
    }

    public ConfigManager getConfigManager()
    {
        return this.configManager;
    }

    public MessageManager getMessageManager()
    {
        return this.messageManager;
    }

    private FileConfig initConfig(String fileName)
    {
        this.saveResource(fileName, false);

        final FileConfig config = FileConfig.builder(this.getDataFolder().toPath().resolve(fileName))
                .autoreload()
                .autosave()
                .build();

        config.load();

        return config;
    }
}
