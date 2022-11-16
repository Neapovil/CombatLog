package com.github.neapovil.combatlog.manager;

import com.electronwill.nightconfig.core.file.FileConfig;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

public class MessageManager
{
    private final FileConfig fileConfig;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    public MessageManager(FileConfig fileConfig)
    {
        this.fileConfig = fileConfig;
    }

    public String getMessage(String path)
    {
        return this.fileConfig.get("messages." + path);
    }

    public Component deserialize(String message)
    {
        return this.miniMessage.deserialize(message);
    }

    public Component deserialize(String message, TagResolver... tagResolvers)
    {
        return this.miniMessage.deserialize(message, tagResolvers);
    }
}
