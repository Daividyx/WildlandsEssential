package de.blockorca.wildlandsEssential.listener;

import de.blockorca.wildlandsEssential.Main;
import de.blockorca.wildlandsEssential.gui.*;
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
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "Only players can use this command!");
            return true;
        }

        Player player = (Player) commandSender;
        GuiMenu menu = null;

        switch (command.getName().toLowerCase()) {
            case "menu":
                menu = new GuiMainLogic(main, player);
                break;
            case "economy":
                menu = new GuiEconomyLogic(main, player);
                break;
            case "warps":
                menu = new GuiWarpLogic(main, player);
                break;
            case "deadchest":
                menu = new GuiDeadChestLogic(main, player);
                break;
            case "buyable":
                menu = new GuiBuyableLogic(main, player);
                break;
        }

        if (menu != null) {
            menu.openMenu();
        }
        return true;
    }
}
