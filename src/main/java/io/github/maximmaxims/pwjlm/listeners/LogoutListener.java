package io.github.maximmaxims.pwjlm.listeners;

import fr.xephi.authme.events.LogoutEvent;
import io.github.maximmaxims.pwjlm.PWJLM;
import io.github.maximmaxims.pwjlm.classes.WorldGroup;
import io.github.maximmaxims.pwjlm.utils.ConfigUtil;
import io.github.maximmaxims.pwjlm.utils.MessageSenderUtil;
import io.github.maximmaxims.pwjlm.utils.PluginUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public class LogoutListener implements Listener {
    private final PWJLM plugin;

    public LogoutListener(@NotNull PWJLM plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLogout(@NotNull LogoutEvent event) {
        Player player = event.getPlayer();
        if (plugin.getConfig().getBoolean("ignoreVanished") && PluginUtil.isVanished(player)) return;
        if (plugin.getConfig().getBoolean("ignoreNoPermission") && !player.hasPermission("pwjlm.notify")) return;
        WorldGroup group = WorldGroup.getInstance(plugin, player.getWorld());
        if (group != null && group.getUseAuthme(false)) {
            String message = group.getAuthmeMessage(false);
            MessageSenderUtil.sendMessage(new HashSet<>(group.getWorlds()), message, player, ConfigUtil.usePapi(plugin), false);
        }
    }
}
