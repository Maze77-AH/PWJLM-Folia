package io.github.maximmaxims.pwjlm.listeners;

import io.github.maximmaxims.pwjlm.PWJLM;
import io.github.maximmaxims.pwjlm.classes.WorldGroup;
import io.github.maximmaxims.pwjlm.utils.ConfigUtil;
import io.github.maximmaxims.pwjlm.utils.MessageSenderUtil;
import io.github.maximmaxims.pwjlm.utils.PluginUtil;
import io.github.maximmaxims.pwjlm.utils.UpdateUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerJoinListener implements Listener {
    private final PWJLM plugin;

    public PlayerJoinListener(@NotNull PWJLM plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoinEvent(@NotNull PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("pwjlm.update")) {
            new UpdateUtil(plugin, 99738).getVersion(version -> {
                if (!plugin.getDescription().getVersion().equals(version)) {
                    player.sendMessage("There is a new update available (" + version + ")!");
                }
            });
        }
        if (plugin.getConfig().getBoolean("removeDefaultJoin", false)) {
            //noinspection deprecation
            event.setJoinMessage(null);
        }
        boolean checkVanish = plugin.getConfig().getBoolean("ignoreVanished");
        if (checkVanish && PluginUtil.isVanished(player)) return;
        WorldGroup group = WorldGroup.getInstance(plugin, player.getWorld());
        if (group != null && group.getUseServer(true)) {
            String message = group.getServerMessage(true);
            MessageSenderUtil.sendMessage(group.getWorlds(), message, player, ConfigUtil.usePapi(plugin));
        }
    }
}