package io.github.maximmaxims.pwjlm.listeners;

import fr.xephi.authme.events.LoginEvent;
import io.github.maximmaxims.pwjlm.PWJLM;
import io.github.maximmaxims.pwjlm.classes.WorldGroup;
import io.github.maximmaxims.pwjlm.utils.ConfigUtil;
import io.github.maximmaxims.pwjlm.utils.MessageSenderUtil;
import io.github.maximmaxims.pwjlm.utils.PluginUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class LoginListener implements Listener {
    private final PWJLM plugin;

    public LoginListener(@NotNull PWJLM plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLogin(@NotNull LoginEvent event) {
        Player player = event.getPlayer();
        if (plugin.getConfig().getBoolean("ignoreVanished") && PluginUtil.isVanished(player)) return;
        if (plugin.getConfig().getBoolean("ignoreNoPermission") && !player.hasPermission("pwjlm.notify")) return;
        WorldGroup group = WorldGroup.getInstance(plugin, player.getWorld());
        if (group != null && group.getUseAuthme(true)) {
            String message = group.getAuthmeMessage(true);
            MessageSenderUtil.sendMessage(group.getWorlds(), message, player, ConfigUtil.usePapi(plugin));
        }
    }
}
