package io.github.maximmaxims.pwjlm.commands;

import io.github.maximmaxims.pwjlm.PWJLM;
import io.github.maximmaxims.pwjlm.utils.PluginUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PWJLMCommand implements CommandExecutor, TabCompleter {
    private final PWJLM plugin;

    public PWJLMCommand(PWJLM plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("pwjlm.command")) {
            if (args.length == 0) {
                sendHelpMenu(sender);
                return true;
            } else {
                String subcommand = args[0].toLowerCase();
                switch (subcommand) {
                    case "reload":
                        if (!sender.hasPermission("pwjlm.command.reload")) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&ePWJLM&7] " + "&cYou don''t have permission to use this command!"));
                            return true;
                        }
                        PluginUtil.update(plugin);
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&ePWJLM&7] " + "Config Reloaded Successfully!"));
                        break;
                    case "help":
                        sendHelpMenu(sender);
                        break;
                    default:
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&ePWJLM&7] " + "&cUnknown Subcommand \"" + command + "\"!"));
                }
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&ePWJLM&7] " + plugin.getDescription().getDescription()));
        }
        return true;
    }

    public void sendHelpMenu(@NotNull CommandSender sender) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&ePWJLM&7] Help Menu (v" + plugin.getDescription().getVersion() + ")"));
        if (sender.hasPermission("pwjlm.command.reload")) {
            sender.sendMessage(ChatColor.YELLOW + "/pwjlm reload " + ChatColor.GRAY + "-" + ChatColor.RESET + " Reload PWJLM Configuration");
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String @NotNull [] args) {
        List<String> subCommands = new ArrayList<>();
        switch (args.length) {
            case 0:
                return null;
            case 1:
                if (sender.hasPermission("pwjlm.command")) {
                    subCommands.add("help");
                    if (sender.hasPermission("pwjlm.command.reload")) {
                        subCommands.add("reload");
                    }
                }
                break;
        }
        return subCommands;
    }
}

