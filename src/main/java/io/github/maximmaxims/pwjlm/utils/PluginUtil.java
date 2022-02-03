package io.github.maximmaxims.pwjlm.utils;

import com.tchristofferson.configupdater.ConfigUpdater;
import io.github.maximmaxims.pwjlm.PWJLM;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

public class PluginUtil {
    public static void disable(PWJLM plugin, @NotNull String message) {
        if (!message.equals("")) {
            plugin.getLogger().severe(message);
        }
        plugin.getServer().getPluginManager().disablePlugin(plugin);
    }

    public static boolean usePapi (@NotNull PWJLM plugin) {
        boolean hasPapi = plugin.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null;
        return hasPapi && plugin.getConfig().getBoolean("parsePlaceholders");
    }

    public static boolean hasAuthme(@NotNull PWJLM plugin) {
        return plugin.getServer().getPluginManager().getPlugin("AuthMe") != null;
    }

    public static boolean isVanished(@NotNull Player player) {
        for (MetadataValue meta : player.getMetadata("vanished")) {
            if (meta.asBoolean()) return true;
        }
        return false;
    }

    public static void update(@NotNull PWJLM plugin) {
        plugin.saveDefaultConfig();
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        try {
            ConfigUpdater.update(plugin, "config.yml", configFile, Collections.singletonList("groups"));
        } catch (IOException e) {
            e.printStackTrace();
            disable(plugin, "An error has occurred during config update, please check stack-trace");
        }

        plugin.reloadConfig();
    }
}
