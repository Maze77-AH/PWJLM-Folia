package io.github.maximmaxims.pwjlm.utils;

import com.tchristofferson.configupdater.ConfigUpdater;
import io.github.maximmaxims.pwjlm.PWJLM;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

public class ConfigUtil {
    public static boolean usePapi(@NotNull PWJLM plugin) {
        boolean hasPapi = plugin.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null;
        return hasPapi && plugin.getConfig().getBoolean("parsePlaceholders");
    }

    public static void update(@NotNull PWJLM plugin) {
        plugin.saveDefaultConfig();
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        try {
            ConfigUpdater.update(plugin, "config.yml", configFile, Collections.singletonList("groups"));
        } catch (IOException e) {
            e.printStackTrace();
            PluginUtil.disable(plugin, "An error has occurred during config update, please check stack-trace");
        }

        plugin.reloadConfig();
    }
}
