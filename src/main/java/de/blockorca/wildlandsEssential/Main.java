package de.blockorca.wildlandsEssential;

import de.blockorca.wildlandsEssential.listener.CommandListener;
import de.blockorca.wildlandsEssential.listener.GuiListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
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
}
