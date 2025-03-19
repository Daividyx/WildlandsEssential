package de.blockorca.wildlandsEssential.gui;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import de.blockorca.wildlandsEssential.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Represents the Economy GUI ("Bank") for the WildlandsEssential plugin.
 *
 * <p>This menu displays the player's current balance along with payment system information.
 * It also provides a back button for returning to the main menu.</p>
 */
public class GuiEconomy extends AbstractMenu {

    // Retrieve the Essentials instance from the main plugin.


    /**
     * Constructs a new GuiEconomy instance.
     *
     * @param main   the main plugin instance used for configuration and utilities.
     * @param player the player for whom the menu is displayed.
     */
    public GuiEconomy(Main main, Player player) {
        super(main, player, "Bank", 45);
    }

    /**
     * Populates the economy GUI with menu items.
     *
     * <p>This method adds items to the inventory representing the player's balance,
     * information about the payment system, and a back button to return to the main menu.</p>
     *
     * @param inv the inventory to which the menu items are added.
     */
    @Override
    protected void placeItems(Inventory inv) {
        // Retrieve the Essentials instance and associated user for the player.
        Essentials essentials = main.getEssentials();
        User user = essentials.getUser(player);

        // Create the balance item displaying the player's current money.
        int balanceIndex = main.getConfig().getInt("balanceItem.position");
        String balanceItemName = main.getConfig().getString("balanceItem.name");
        ItemStack balanceItem = createMenuItem(
                ChatColor.GOLD + balanceItemName,
                Material.GOLD_INGOT,
                ChatColor.GREEN + "Your current balance: " + ChatColor.RED + "$" + user.getMoney().toString()
        );
        inv.setItem(balanceIndex, balanceItem);

        // Create the info item with details about the payment system.
        int infoIndex = main.getConfig().getInt("economyInfoItem.position");
        String infoItemName = main.getConfig().getString("economyInfoItem.name");
        ItemStack infoItem = createMenuItem(
                ChatColor.GOLD + infoItemName,
                Material.BOOK,
                ChatColor.GREEN + "Information about the payment system",
                ChatColor.AQUA + "Use " + ChatColor.RED + " /pay <playername>" + ChatColor.AQUA + " to transfer funds"
        );
        inv.setItem(infoIndex, infoItem);

        // Create the back button to return to the main menu.
        int backIndex = main.getConfig().getInt("backItemHomeMenu.position");
        String backItemName = main.getConfig().getString("backItemHomeMenu.name");
        ItemStack backItem = createMenuItem(
                ChatColor.GOLD + backItemName,
                Material.BARRIER,
                ChatColor.GREEN + "Return to Main Menu"
        );
        inv.setItem(backIndex, backItem);
    }
}
