package io.github.maximmaxims.pwjlm.listeners;

import fr.xephi.authme.api.v3.AuthMeApi;
import io.github.maximmaxims.pwjlm.PWJLM;
import io.github.maximmaxims.pwjlm.classes.WorldGroup;
import io.github.maximmaxims.pwjlm.utils.MessageSenderUtil;
import io.github.maximmaxims.pwjlm.utils.PluginUtil;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerChangedWorldListener implements Listener {
    private final PWJLM plugin;

    public PlayerChangedWorldListener(@NotNull PWJLM plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChangedWorldEvent(@NotNull PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        boolean checkVanish = plugin.getConfig().getBoolean("ignoreVanished");
        if (checkVanish && PluginUtil.isVanished(player)) return;
        sendMessage(plugin, player, event.getFrom(), false);
        sendMessage(plugin, player, player.getWorld(), true);
    }

    private void sendMessage(PWJLM plugin, Player player, World world, boolean forJoin) {
        WorldGroup group = WorldGroup.getInstance(plugin, world);
        if (group == null || !group.getUseGroups(forJoin)) {
            return;
        }
        if (group.getGroupIgnoreUnauthed(forJoin)) {
            if (!PluginUtil.hasAuthme(plugin)) {
                plugin.getLogger().warning("'no-unauthed' is set to true, but AuthMe is not installed, install AuthMe or set to false");
                return;
            }
            if (!AuthMeApi.getInstance().isAuthenticated(player)) return;
        }
        String message = group.getGroupMessage(forJoin);
        message = message.replace("{PLAYER}", player.getName()); // Add player name
        MessageSenderUtil.sendMessage(group.getWorlds(), message, PluginUtil.usePapi(plugin));
    }

}