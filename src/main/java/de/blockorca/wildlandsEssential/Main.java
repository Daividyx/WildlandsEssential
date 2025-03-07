package de.blockorca.wildlandsEssential;

import de.blockorca.wildlandsEssential.listener.ClickListener;
import de.blockorca.wildlandsEssential.listener.CommandListener;
import de.blockorca.wildlandsEssential.listener.GuiListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import javax.imageio.stream.ImageInputStream;

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


    public void registerCommands(){

        getCommand("menu").setExecutor(new CommandListener(this));
        getCommand("ecomenu").setExecutor(new CommandListener(this));

    }

    public void registerEvents(){

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new GuiListener(this), this);


    }

}
