package de.blockorca.wildlandsEssential;

import com.earth2me.essentials.Essentials;
import de.blockorca.wildlandsEssential.listener.CommandListener;
import de.blockorca.wildlandsEssential.listener.GuiListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private Essentials essentials;

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


        registerCommands();
        registerEvents();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerCommands() {
        getCommand("menu").setExecutor(new CommandListener(this));
        getCommand("economy").setExecutor(new CommandListener(this));
        getCommand("warps").setExecutor(new CommandListener(this));
        getCommand("deadchest").setExecutor(new CommandListener(this));
        getCommand("buyable").setExecutor(new CommandListener(this));
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new GuiListener(this), this);
    }


    public Essentials getEssentials() {
        return essentials;
    }
}
