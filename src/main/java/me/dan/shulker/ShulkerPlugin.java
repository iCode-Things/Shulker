package me.dan.shulker;

import lombok.Getter;
import me.dan.pluginapi.configuration.Configuration;
import me.dan.pluginapi.file.YamlFile;
import me.dan.pluginapi.plugin.CustomPlugin;
import me.dan.shulker.configuration.Messages;
import me.dan.shulker.listener.BlockBreakListener;
import me.dan.shulker.listener.BlockPlaceListener;
import me.dan.shulker.listener.PlayerInteractListener;
import me.dan.shulker.manager.ShulkerManager;

@Getter
public final class ShulkerPlugin extends CustomPlugin {


    @Getter
    private static ShulkerPlugin instance;

    private ShulkerManager shulkerManager;

    @Override
    public void enable() {
        instance = this;
        this.shulkerManager = new ShulkerManager();
        Configuration.loadConfig(new YamlFile("messages.yml", this.getDataFolder().getAbsolutePath(), null, this), Messages.values());
        registerEvents(new BlockBreakListener(), new BlockPlaceListener(), new PlayerInteractListener());
    }

    @Override
    public void disable() {

    }
}
