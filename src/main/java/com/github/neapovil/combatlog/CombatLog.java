package com.github.neapovil.combatlog;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

import com.electronwill.nightconfig.core.file.FileConfig;
import com.github.neapovil.combatlog.listener.Listener;
import com.github.neapovil.combatlog.manager.Manager;
import com.github.neapovil.combatlog.runnable.Runnable;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.IntegerArgument;
import dev.jorel.commandapi.arguments.LiteralArgument;

public final class CombatLog extends JavaPlugin
{
    private static CombatLog instance;
    private Manager manager;
    private FileConfig config;

    @Override
    public void onEnable()
    {
        instance = this;

        this.saveResource("config.toml", false);

        this.config = FileConfig.builder(new File(this.getDataFolder(), "config.toml"))
                .autoreload()
                .autosave()
                .build();
        this.config.load();

        this.manager = new Manager();

        this.getServer().getPluginManager().registerEvents(new Listener(), this);

        new Runnable().runTaskTimer(this, 0, 20);

        new CommandAPICommand("combatlog")
                .withPermission("combatlog.command")
                .withArguments(new LiteralArgument("setcooldown"))
                .withArguments(new IntegerArgument("seconds"))
                .executes((sender, args) -> {
                    final int cd = (int) args[0];

                    this.config.set("general.cooldown", cd);

                    sender.sendMessage("Cooldown changed to: " + cd + "s");
                })
                .register();
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
