package de.blockorca.wildlandsEssential.listener;

import de.blockorca.wildlandsEssential.Main;
import de.blockorca.wildlandsEssential.data.ConfigManager;
import de.blockorca.wildlandsEssential.gui.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * CommandListener handles the commands for the WildlandsEssential plugin.
 *
 * <p>This class processes various subcommands issued by players, including opening different GUI menus,
 * retrieving player data, reloading the plugin, and displaying help information.</p>
 */
public class CommandListener implements CommandExecutor {

    Main main;
    ConfigManager configManager;

    /**
     * Constructs a new CommandListener.
     *
     * @param main the main plugin instance used to access configuration and GUI managers.
     */
    public CommandListener(Main main) {
        this.main = main;
        this.configManager = main.getConfigManager();
    }

    /**
     * Processes commands sent to the plugin.
     *
     * <p>This method supports the following subcommands:
     * <ul>
     *   <li><strong>menu</strong> - Opens the main GUI menu.</li>
     *   <li><strong>playerdata</strong> - Retrieves player data for a specified player (requires permission).</li>
     *   <li><strong>reload</strong> - Reloads the plugin configuration (requires permission).</li>
     *   <li><strong>help</strong> - Displays available command information.</li>
     *   <li><strong>eco/economy</strong> - Opens the Economy GUI menu.</li>
     *   <li><strong>homes/home</strong> - Opens the Homes GUI menu.</li>
     *   <li><strong>warps/warp</strong> - Opens the Warps GUI menu.</li>
     *   <li><strong>deadchest/dead</strong> - Opens the DeadChest GUI menu.</li>
     *   <li><strong>buyable/buy</strong> - Opens the Buyable Functions GUI menu.</li>
     * </ul></p>
     *
     * @param commandSender the sender of the command.
     * @param command       the command that was executed.
     * @param label         the alias of the command.
     * @param args          the command arguments.
     * @return true if the command was processed successfully, false otherwise.
     */
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage(ChatColor.RED + "Only players can use this command!");
            return true;
        }

        GuiMenu menu;

        if (command.getName().equalsIgnoreCase("wildlands")) {
            // Process the subcommand based on the first argument
            switch (args[0].toLowerCase()) {
                case "menu":
                    menu = new GuiMainMenu(main, player);
                    menu.openMenu();
                    break;
                case "playerdata":
                    if (args.length > 1) {
                        // Verify the player has the required permission to access player data
                        if (!player.hasPermission("wildlandsEssential.*")) {
                            player.sendMessage(ChatColor.RED + "You do not have permission to perform this command!");
                            break;
                        }
                        // Retrieve and display player data for the specified player name
                        configManager.getPlayerData(player, args[1]);
                        break;
                    }
                case "reload":
                    if (args.length > 0) {
                        // Verify the player has permission to reload the plugin
                        if (!player.hasPermission("wildlands.essential.*")) {
                            player.sendMessage(ChatColor.RED + "You do not have permission to perform this command!");
                            break;
                        }
                        main.reloadWildlands();
                        player.sendMessage(ChatColor.GREEN + "🔄 WildlandsEssential has been reloaded!");
                        break;
                    }
                case "help":
                    if (args.length > 0) {
                        // Display the list of available commands to the player
                        player.sendMessage(ChatColor.WHITE + "The following commands are available:");
                        player.sendMessage(ChatColor.AQUA + " /wildlands menu" + ChatColor.AQUA + "      Opens the Wildlands menu");
                        player.sendMessage(ChatColor.AQUA + " /wildlands help" + ChatColor.AQUA + "      Displays this help message");
                        player.sendMessage(ChatColor.AQUA + " /wildlands (eco/economy)" + ChatColor.AQUA + "      Opens the Economy menu");
                        player.sendMessage(ChatColor.AQUA + " /wildlands homes" + ChatColor.AQUA + "      Opens the Homes menu");
                        player.sendMessage(ChatColor.AQUA + " /wildlands warps" + ChatColor.AQUA + "      Opens the Warps menu");
                        player.sendMessage(ChatColor.AQUA + " /wildlands (dead/deadchest)" + ChatColor.AQUA + "      Opens the DeadChest menu");
                        player.sendMessage(ChatColor.AQUA + " /wildlands (buy/buyable)" + ChatColor.AQUA + "      Opens the Buyable Functions menu");
                        player.sendMessage(ChatColor.AQUA + " /sethome <name>" + ChatColor.AQUA + "      Make your actual location to a home ");
                        player.sendMessage(ChatColor.AQUA + " /delhome <name>" + ChatColor.AQUA + "      deletes the named home");
                        player.sendMessage(ChatColor.AQUA + " /home <name>" + ChatColor.AQUA + "      teleports you to the named home");
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
                    if (args.length > 0) {
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
