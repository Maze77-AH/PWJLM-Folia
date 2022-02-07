package io.github.maximmaxims.pwjlm.utils;

import io.github.maximmaxims.pwjlm.PWJLM;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.jetbrains.annotations.NotNull;

public class PluginUtil {
    public static void disable(PWJLM plugin, @NotNull String message) {
        if (!message.equals("")) {
            plugin.getLogger().severe(message);
        }
        plugin.getServer().getPluginManager().disablePlugin(plugin);
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
}
