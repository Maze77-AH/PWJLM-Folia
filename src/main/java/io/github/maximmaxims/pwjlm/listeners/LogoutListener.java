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

public class LogoutListener implements Listener {
    private final PWJLM plugin;

    public LogoutListener(@NotNull PWJLM plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLogout(@NotNull LogoutEvent event) {
        Player player = event.getPlayer();
        boolean checkVanish = plugin.getConfig().getBoolean("ignoreVanished");
        if (checkVanish && PluginUtil.isVanished(player)) return;
        WorldGroup group = WorldGroup.getInstance(plugin, player.getWorld());
        if (group != null && group.getUseAuthme(false)) {
            String message = group.getAuthmeMessage(false);
            MessageSenderUtil.sendMessage(group.getWorlds(), message, player, ConfigUtil.usePapi(plugin));
        }
    }
}
