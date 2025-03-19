package de.blockorca.wildlandsEssential;

import com.earth2me.essentials.Essentials;
import de.blockorca.wildlandsEssential.data.ConfigManager;
import de.blockorca.wildlandsEssential.economy.EconomyManager;
import de.blockorca.wildlandsEssential.listener.CommandListener;
import de.blockorca.wildlandsEssential.listener.GuiListener;
import de.blockorca.wildlandsEssential.listener.PlayerListener;
import de.blockorca.wildlandsEssential.logic.DeadChestLogic;
import de.blockorca.wildlandsEssential.logic.FlyLogic;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private Essentials essentials;
    private EconomyManager economyManager;
    private ConfigManager configManager;
    private FlyLogic flyLogic;
    private DeadChestLogic deadChestLogic;


    @Override
    public void onEnable() {


        // Versuche, das Plugin "Essentials" zu bekommen
        Plugin essPlugin = getServer().getPluginManager().getPlugin("Essentials");
        if (essPlugin instanceof Essentials) {
            this.essentials = (Essentials) essPlugin;
            getLogger().info("Essentials erkannt. Kann auf Essentials-API zugreifen!");
        } else {
            getLogger().warning("Essentials wurde nicht gefunden.");
            // Falls du dein Plugin beenden willst, wenn Essentials fehlt:
            // getServer().getPluginManager().disablePlugin(this);
        }
        this.configManager = new ConfigManager(this);
        this.economyManager = new EconomyManager(essentials);
        this.flyLogic = new FlyLogic(this);
        this.deadChestLogic = new DeadChestLogic(this);


        registerCommands();
        registerEvents();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerCommands() {
        getCommand("menu").setExecutor(new CommandListener(this));
        getCommand("wildlands").setExecutor(new CommandListener(this));
    }

    public void registerEvents() {

        getServer().getPluginManager().registerEvents(new GuiListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);

    }


    public Essentials getEssentials() {
        return essentials;
    }

    public EconomyManager getEconomyManager() {
        return economyManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public FlyLogic getFlyLogic() {
        return flyLogic;
    }
    public DeadChestLogic getDeadChestLogic() {
        return deadChestLogic;
    }

    public void reloadWildlands() {
        getLogger().info("🔄 Reloading WildlandsEssential...");

        // Konfigurationsdateien neu laden
        reloadConfig();

        // Falls du eine separate Config für Spielerdaten hast, lade sie neu
        if (configManager != null) {
            configManager.reloadPlayerData();
        }


        getLogger().info("✅ WildlandsEssential wurde erfolgreich neu geladen!");
    }

}
