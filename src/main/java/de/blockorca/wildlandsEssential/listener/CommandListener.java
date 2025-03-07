package de.blockorca.wildlandsEssential.listener;

import de.blockorca.wildlandsEssential.Main;
import de.blockorca.wildlandsEssential.gui.GuiMainLogic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandListener implements CommandExecutor {

    Main main;

    public CommandListener(Main main) {
        this.main = main;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {


        if(command.getName().equalsIgnoreCase("menu")) {

            GuiMainLogic guiMain = new GuiMainLogic( main, commandSender,command,s,strings);
            guiMain.openMenu();


        }


    }
}
