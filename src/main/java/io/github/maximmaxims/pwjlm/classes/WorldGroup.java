package io.github.maximmaxims.pwjlm.classes;

import io.github.maximmaxims.pwjlm.PWJLM;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class WorldGroup {
    private final List<World> worlds;
    private final String name;
    private final ConfigurationSection customSection;
    private final ConfigurationSection defaultSection;

    private WorldGroup(@NotNull FileConfiguration config, @NotNull String name, @NotNull List<World> worlds) {
        this.worlds = worlds;
        this.name = name;
        customSection = config.getConfigurationSection("groups." + name + ".settings");
        defaultSection = config.getConfigurationSection("default");
    }

    public static @Nullable WorldGroup getInstance(@NotNull PWJLM plugin, @NotNull String groupName) {
        List<World> worlds = parseWorlds(plugin, groupName);
        return worlds != null ? new WorldGroup(plugin.getConfig(), groupName, worlds) : null;
    }

    public static @Nullable WorldGroup getInstance(@NotNull PWJLM plugin, @NotNull World world) {
        String worldName = world.getName(); // Name of the searched world
        ConfigurationSection groups = plugin.getConfig().getConfigurationSection("groups");
        if (groups != null) {
            Set<String> groupNames = groups.getKeys(false); // Group names
            for (String groupName : groupNames) {
                List<String> worldNames = groups.getStringList(groupName + ".worlds"); // List of worlds in group
                if (!worldNames.isEmpty() && worldNames.contains(worldName)) { // If worlds contain the searched world
                    return getInstance(plugin, groupName);
                }
            }
        }
        return null;
    }

    private static @Nullable List<World> parseWorlds(@NotNull PWJLM plugin, @NotNull String groupName) {
        List<String> worldNames = plugin.getConfig().getStringList("groups." + groupName + ".worlds");
        if (!worldNames.isEmpty()) {
            List<World> worlds = new ArrayList<>();
            for (String worldName : worldNames) {
                World world = plugin.getServer().getWorld(worldName);
                if (world == null) return null;
                worlds.add(world);
            }
            return worlds;
        }
        return null;
    }

    public boolean getUseServer(boolean forJoin) {
        return forJoin ? getBoolean("join.server.use") : getBoolean("leave.server.use");
    }

    public String getServerMessage(boolean forJoin) {
        return forJoin ? getString("join.server.message", "join.message") : getString("leave.server.message", "leave.message");
    }

    public boolean getUseAuthme(boolean forJoin) {
        return forJoin ? getBoolean("join.authme.use") : getBoolean("leave.authme.use");
    }

    public String getAuthmeMessage(boolean forJoin) {
        return forJoin ? getString("join.authme.message", "join.message") : getString("leave.authme.message", "leave.message");
    }

    public boolean getUseGroups(boolean forJoin) {
        return forJoin ? getBoolean("join.group.use") : getBoolean("leave.group.use");
    }

    public String getGroupMessage(boolean forJoin) {
        return forJoin ? getString("join.group.message", "join.message") : getString("leave.group.message", "leave.message");
    }

    public boolean getGroupIgnoreUnauthed(boolean forJoin) {
        return forJoin ? getBoolean("join.group.no-unauthed") : getBoolean("leave.group.no-unauthed");
    }

    public boolean getRemoveDefault(boolean forJoin) {
        return forJoin ? getBoolean("join.server.removeDefault") : getBoolean("leave.server.removeDefault");
    }

    public List<World> getWorlds() {
        return worlds;
    }

    private boolean getBoolean(@NotNull String path) {
        boolean use = false;
        if (defaultSection != null) {
            use = defaultSection.getBoolean(path, false);
        }
        if (customSection != null) {
            use = customSection.getBoolean(path, use);
        }
        return use;
    }

    private @NotNull String getString(@NotNull String longPath, @NotNull String shortPath) {
        String msg = "";
        if (customSection != null) {
            msg = customSection.getString(longPath, "");
            if (!msg.equals("")) {
                return msg;
            }
            msg = customSection.getString(shortPath, "");
            if (!msg.equals("")) {
                return msg;
            }
        }
        if (defaultSection != null) {
            msg = defaultSection.getString(longPath, "");
            if (!msg.equals("")) {
                return msg;
            }
            msg = defaultSection.getString(shortPath);
            if (msg == null) {
                msg = "";
            }
        }
        if (msg.equals("")) {
            msg = "No " + longPath + " message found for group " + name;
        }
        return msg;
    }

}
