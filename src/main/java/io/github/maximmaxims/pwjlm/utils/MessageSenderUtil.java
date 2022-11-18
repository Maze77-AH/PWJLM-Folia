package io.github.maximmaxims.pwjlm.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class MessageSenderUtil {
    public static void sendMessage(@NotNull Set<World> worlds, @NotNull String message, @NotNull Player player, boolean usePapi, boolean isJoin) {
        message = message.replace("{PLAYER}", player.getName()); // Add player name
        if (usePapi) {
            message = PlaceholderAPI.setPlaceholders(player, message);
        }
        Set<Player> players = new HashSet<>();
        for (World w : worlds) {
            players.addAll(w.getPlayers());
        }
        if (isJoin) {
            players.add(player);
        }
        for (Player p : players) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        }
    }

}