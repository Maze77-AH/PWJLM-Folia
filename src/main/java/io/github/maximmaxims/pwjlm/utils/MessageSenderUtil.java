package io.github.maximmaxims.pwjlm.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MessageSenderUtil {
    public static void sendMessage(@NotNull List<World> worlds, @NotNull String message, Player player, boolean usePapi) {
        message = message.replace("{PLAYER}", player.getName()); // Add player name
        for (World w : worlds) {
            for (Player p : w.getPlayers()) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', usePapi ? PlaceholderAPI.setPlaceholders(player, message) : message));
            }
        }
    }

}