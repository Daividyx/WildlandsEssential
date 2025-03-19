package de.blockorca.wildlandsEssential.listener;

import de.blockorca.wildlandsEssential.Main;
import de.blockorca.wildlandsEssential.data.ConfigManager;
import de.blockorca.wildlandsEssential.gui.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandListener implements CommandExecutor {

    Main main;
    ConfigManager configManager;

    public CommandListener(Main main) {
        this.main = main;
        this.configManager = main.getConfigManager();
    }

    /*  Commands:

    *   /wildlands menu
        /wildlands playerdata
        wildlands reload
        /wildlands help
        /wildlands (eco/economy)
        /wildlands homes
        /wildlands warps
        /wildlands deadchest
        /wildlands (buy/buyable)
    * */
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "Only players can use this command!");
            return true;
        }

        Player player = (Player) commandSender;
        GuiMenu menu = null;


        if (command.getName().equalsIgnoreCase("wildlands")) {
            switch (args[0].toLowerCase()) {

                case "menu":
                    menu = new GuiMainMenu(main, player);
                    menu.openMenu();
                    break;
                case "playerdata":
                    if (args.length > 1) {
                        //Prüfen ob Sender die Berechtigung wildlands.* hat
                        if (!(player.hasPermission("wildlandsEssential.*"))) {
                            player.sendMessage(ChatColor.RED + "Dazu hast du leider keine Berechtigung!");
                            break;
                        }
                        //prüfen ob ein Name angegeben wurde. wenn ja dann ausführen

                        configManager.getPlayerData(player, args[1]);
                        break;
                    }
                case "reload":
                    if (args.length > 0) {
                        //Prüfen ob Sender die Berechtigung wildlands.* hat
                        if (!(player.hasPermission("wildlandsEssential.*"))) {
                            player.sendMessage(ChatColor.RED + "Dazu hast du leider keine Berechtigung!");
                            break;
                        }

                        main.reloadWildlands();
                        player.sendMessage(ChatColor.GREEN + "🔄 WildlandsEssential wurde neu geladen!");
                        break;
                    }
                case "help":
                    if (args.length > 0) {
                        player.sendMessage(ChatColor.WHITE + " Folgende Commands kannst du nutzen");
                        player.sendMessage(ChatColor.AQUA + " /wildlands menu" + ChatColor.AQUA + "      Öffnet das Wildlands Menü");
                        player.sendMessage(ChatColor.AQUA + " /wildlands help" + ChatColor.AQUA + "      Hilft dir hiermit auf die Sprünge");
                        player.sendMessage(ChatColor.AQUA + " /wildlands (eco/economy)" + ChatColor.AQUA + "      Öffnet Economy Menü");
                        player.sendMessage(ChatColor.AQUA + " /wildlands homes" + ChatColor.AQUA + "      Öffnet das Homes Menü");
                        player.sendMessage(ChatColor.AQUA + " /wildlands warps" + ChatColor.AQUA + "      Öffnet das warps Menü");
                        player.sendMessage(ChatColor.AQUA + " /wildlands (dead/deadchest)" + ChatColor.AQUA + "      Öffnet das Dead Chest Menü");
                        player.sendMessage(ChatColor.AQUA + " /wildlands (buy/buyable)" + ChatColor.AQUA + "      Öffnet das Kaufbare Funktionen Menü");
                        break;
                    }
                case "economy":
                case "eco":
                    if (args.length > 0) {
                        menu = new GuiEconomy(main, player);
                        menu.openMenu();
                        break;
                    }
                case "homes":
                case "home":
                    if (args.length > 0) {
                        menu = new GuiHome(main, player);
                        menu.openMenu();
                        break;
                    }
                case "warps":
                case "warp":
                    if (args.length > 0) {
                        menu = new GuiWarp(main, player);
                        menu.openMenu();
                        break;
                    }
                case "deadchest":
                case "dead":
                    if (args.length > 0) {
                        menu = new GuiDeadChest(main, player);
                        menu.openMenu();
                        break;
                    }
                case "buyable":
                case "buy":
                    if(args.length > 0) {
                        menu = new GuiBuyable(main, player);
                        menu.openMenu();
                        break;
                    }


            }
                return true;
        }




        return false;
    }
}

