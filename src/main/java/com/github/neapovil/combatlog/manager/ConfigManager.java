package com.github.neapovil.combatlog.manager;

import com.electronwill.nightconfig.core.file.FileConfig;

public class ConfigManager
{
    private final FileConfig fileConfig;

    public ConfigManager(FileConfig fileConfig)
    {
        this.fileConfig = fileConfig;
    }

    public boolean isCombatlogEnabled()
    {
        return this.fileConfig.get("config.enabled");
    }

    public int getCooldownPeriod()
    {
        return this.fileConfig.get("config.cooldown");
    }

    public void setCooldownPeriod(int seconds)
    {
        this.fileConfig.set("config.cooldown", seconds);
    }

    public void setCooldownStatus(boolean status)
    {
        this.fileConfig.set("config.enabled", status);
    }
}
