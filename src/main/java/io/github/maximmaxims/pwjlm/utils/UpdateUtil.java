package io.github.maximmaxims.pwjlm.utils;

import io.github.maximmaxims.pwjlm.PWJLM;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;
import org.bukkit.Bukkit;

public class UpdateUtil {

    private final PWJLM plugin;
    private final int resourceId;

    public UpdateUtil(PWJLM plugin, int resourceId) {
        this.plugin = plugin;
        this.resourceId = resourceId;
    }

    public void getVersion(final Consumer<String> consumer) {
        Bukkit.getAsyncScheduler().runNow(plugin, scheduledTask -> {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream();
                 Scanner scanner = new Scanner(inputStream)) {
    
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }
    
            } catch (IOException exception) {
                plugin.getLogger().warning("Unable to check for updates: " + exception.getMessage());
            }
        });
    }    
}
 