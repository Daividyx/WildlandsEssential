package de.blockorca.wildlandsEssential;

import com.earth2me.essentials.Essentials;
import de.blockorca.wildlandsEssential.data.ConfigManager;
import de.blockorca.wildlandsEssential.economy.EconomyManager;
import de.blockorca.wildlandsEssential.listener.CommandListener;
import de.blockorca.wildlandsEssential.listener.GuiListener;
import de.blockorca.wildlandsEssential.listener.PlayerListener;
import de.blockorca.wildlandsEssential.logic.DeadChestLogic;
import de.blockorca.wildlandsEssential.logic.FlyLogic;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

/**
 * Main class for the WildlandsEssential plugin.
 * <p>
 * This class initializes and manages core components such as the Essentials integration,
 * configuration management, economy, flight logic, and dead chest functionality. It also sets up
 * commands and event listeners necessary for the plugin's operation.
 */
public final class Main extends JavaPlugin {

    private Essentials essentials;
    private EconomyManager economyManager;
    private ConfigManager configManager;
    private FlyLogic flyLogic;
    private DeadChestLogic deadChestLogic;

    /**
     * Called when the plugin is enabled.
     * <p>
     * This method initializes the plugin by loading dependencies, setting up managers and logic classes,
     * and registering commands and event listeners.
     */
    @Override
    public void onEnable() {
        // Attempt to retrieve the Essentials plugin.
        Plugin essPlugin = getServer().getPluginManager().getPlugin("Essentials");
        if (essPlugin instanceof Essentials) {
            this.essentials = (Essentials) essPlugin;
            getLogger().info("Essentials detected. Access to Essentials API enabled.");
        } else {
            getLogger().warning("Essentials plugin not found.");
            // If Essentials is required, you may disable this plugin:
            // getServer().getPluginManager().disablePlugin(this);
        }
        // Initialize configuration, economy, flight, and dead chest logic managers.
        this.configManager = new ConfigManager(this);
        this.economyManager = new EconomyManager(essentials);
        this.flyLogic = new FlyLogic(this);
        this.deadChestLogic = new DeadChestLogic(this);

        // ➤ Prüfe beim Plugin‑Start, ob für jeden online-Spieler die Flugzeit bereits abgelaufen ist
        for (Player p : getServer().getOnlinePlayers()) {
            if (getFlyLogic().isFlyTimeExpired(p)) {
                getFlyLogic().disableFly(p);
            }
        }


        // Register commands and event listeners.
        registerCommands();
        registerEvents();
    }

    /**
     * Called when the plugin is disabled.
     * <p>
     * Perform any necessary cleanup during plugin shutdown.
     */
    @Override
    public void onDisable() {
        // Add plugin shutdown procedures here if needed.
    }

    /**
     * Registers the plugin commands.
     * <p>
     * Sets the executor for the "menu" and "wildlands" commands.
     */
    public void registerCommands() {
        Objects.requireNonNull(getCommand("menu")).setExecutor(new CommandListener(this));
        Objects.requireNonNull(getCommand("wildlands")).setExecutor(new CommandListener(this));
    }

    /**
     * Registers the event listeners for the plugin.
     * <p>
     * Sets up listeners for GUI and player events.
     */
    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new GuiListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    /**
     * Retrieves the Essentials instance.
     *
     * @return the Essentials plugin instance if available
     */
    public Essentials getEssentials() {
        return essentials;
    }

    /**
     * Retrieves the EconomyManager instance.
     *
     * @return the EconomyManager used for managing economic features
     */
    public EconomyManager getEconomyManager() {
        return economyManager;
    }

    /**
     * Retrieves the ConfigManager instance.
     *
     * @return the ConfigManager used for managing configuration files
     */
    public ConfigManager getConfigManager() {
        return configManager;
    }

    /**
     * Retrieves the FlyLogic instance.
     *
     * @return the FlyLogic used for managing flight-related functionality
     */
    public FlyLogic getFlyLogic() {
        return flyLogic;
    }

    /**
     * Retrieves the DeadChestLogic instance.
     *
     * @return the DeadChestLogic used for managing dead chest functionality
     */
    public DeadChestLogic getDeadChestLogic() {
        return deadChestLogic;
    }

    /**
     * Reloads the plugin's configuration and player data.
     * <p>
     * This method reloads the main configuration file and, if applicable, reloads player-specific data,
     * logging the process to the console.
     */
    public void reloadWildlands() {
        getLogger().info("🔄 Reloading WildlandsEssential...");

        // Reload the main configuration.
        reloadConfig();

        // If a separate configuration for player data is used, reload it.
        if (configManager != null) {
            configManager.reloadPlayerData();
        }

        getLogger().info("✅ WildlandsEssential successfully reloaded!");
    }
}
