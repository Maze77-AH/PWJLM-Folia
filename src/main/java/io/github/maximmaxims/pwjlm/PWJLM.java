package io.github.maximmaxims.pwjlm;

import io.github.maximmaxims.pwjlm.commands.PWJLMCommand;
import io.github.maximmaxims.pwjlm.listeners.*;
import io.github.maximmaxims.pwjlm.utils.PluginUtil;
import org.bstats.bukkit.Metrics;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class PWJLM extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        registerEvents();
        registerCommands();
        PluginUtil.update(this);
        new Metrics(this, 13675);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new PlayerChangedWorldListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        if (PluginUtil.hasAuthme(this)) {
            getServer().getPluginManager().registerEvents(new LoginListener(this), this);
            getServer().getPluginManager().registerEvents(new LogoutListener(this), this);
        } else {
            getLogger().config("AuthMe is not installed, disabling events!");
        }
    }

    private void registerCommands() {
        PluginCommand pluginCommand = getCommand("pwjlm");
        if (pluginCommand != null) {
            PWJLMCommand command = new PWJLMCommand(this);
            pluginCommand.setExecutor(command);
            pluginCommand.setTabCompleter(command);
        } else {
            PluginUtil.disable(this, "Couldn't register command, disabling plugin");
        }
    }
}
