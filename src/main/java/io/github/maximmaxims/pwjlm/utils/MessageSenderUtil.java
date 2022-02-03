package io.github.maximmaxims.pwjlm.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MessageSenderUtil {
    public static void sendMessage(@NotNull List<World> worlds, @NotNull String rawMessage, boolean usePapi) {
        for (World w : worlds) {
            for (Player p : w.getPlayers()) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', usePapi ? PlaceholderAPI.setPlaceholders(p, rawMessage) : rawMessage));
            }
        }
    }

}