package de.blockorca.wildlandsEssential.listener;

import de.blockorca.wildlandsEssential.Main;
import de.blockorca.wildlandsEssential.gui.GuiEconomyLogic;
import de.blockorca.wildlandsEssential.gui.GuiMainLogic;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandListener implements CommandExecutor {

    Main main;

    public CommandListener(Main main) {
        this.main = main;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        //menu
        if(command.getName().equalsIgnoreCase("menu")) {
            if(!(commandSender instanceof Player)){
                commandSender.sendMessage(ChatColor.RED + "Only players can use this command!");
                return true;
            }
            Player player = (Player) commandSender;
            GuiMainLogic guiMain = new GuiMainLogic( main, player);
            guiMain.openMenu();

            return true;

        }

        if(command.getName().equalsIgnoreCase("ecomenu")) {
            if(!(commandSender instanceof Player)){
                commandSender.sendMessage(ChatColor.RED + "Only players can use this command!");
                return true;
            }
            Player player = (Player) commandSender;
            GuiEconomyLogic guiEconomy = new GuiEconomyLogic( main, player);
            guiEconomy.openMenu();

            return true;

        }




        return false;
    }





}
