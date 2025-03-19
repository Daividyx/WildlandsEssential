package de.blockorca.wildlandsEssential.gui;

import de.blockorca.wildlandsEssential.Main;
import de.blockorca.wildlandsEssential.data.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Represents the Homes GUI menu.
 *
 * <p>This menu displays various home point options available for the player.
 * Each home point shows its name, price, and instructions for setting, deleting, and viewing homes.
 * A back button is also provided to return to the main menu.</p>
 */
public class GuiHome extends AbstractMenu {

    private final ConfigManager configManager;

    /**
     * Constructs a new GuiHome instance.
     *
     * @param main   the main plugin instance used for configuration access.
     * @param player the player for whom the menu is displayed.
     */
    public GuiHome(Main main, Player player) {
        super(main, player, "Homes", 45);
        this.configManager = main.getConfigManager();
    }

    /**
     * Places the home menu items into the inventory.
     *
     * <p>This method retrieves configuration values for each home option, including position, name, and price.
     * It then creates a menu item for each home point with appropriate display text and instructions,
     * and sets the items in their designated slots in the inventory.
     * Finally, a back button is added to allow returning to the main menu.</p>
     *
     * @param inv the inventory where the menu items are placed.
     */
    @Override
    protected void placeItems(Inventory inv) {
        // Home 1
        int home1Index = main.getConfig().getInt("home1.position");
        String home1Name = main.getConfig().getString("home1.name");
        int price1 = (int) configManager.getHome1Price();
        ItemStack home1Item = createMenuItem(
                ChatColor.GOLD + home1Name,
                Material.COMPASS,
                ChatColor.GREEN + "The first home point costs: " + ChatColor.RED + price1,
                ChatColor.AQUA + "Set a home point with " + ChatColor.RED + " /sethome <name>",
                ChatColor.AQUA + "Delete a home point with " + ChatColor.RED + " /delhome <name>",
                ChatColor.AQUA + "View your homes with " + ChatColor.RED + " /homes"
        );
        inv.setItem(home1Index, home1Item);

        // Home 2
        int home2Index = main.getConfig().getInt("home2.position");
        String home2Name = main.getConfig().getString("home2.name");
        int price2 = (int) configManager.getHome2Price();
        ItemStack home2Item = createMenuItem(
                ChatColor.GOLD + home2Name,
                Material.COMPASS,
                ChatColor.GREEN + "The second home point costs: " + ChatColor.RED + price2,
                ChatColor.AQUA + "Set a home point with " + ChatColor.RED + " /sethome <name>",
                ChatColor.AQUA + "Delete a home point with " + ChatColor.RED + " /delhome <name>",
                ChatColor.AQUA + "View your homes with " + ChatColor.RED + " /homes"
        );
        inv.setItem(home2Index, home2Item);

        // Home 3
        int home3Index = main.getConfig().getInt("home3.position");
        String home3Name = main.getConfig().getString("home3.name");
        int price3 = (int) configManager.getHome3Price();
        ItemStack home3Item = createMenuItem(
                ChatColor.GOLD + home3Name,
                Material.COMPASS,
                ChatColor.GREEN + "The third home point costs: " + ChatColor.RED + price3,
                ChatColor.AQUA + "Set a home point with " + ChatColor.RED + " /sethome <name>",
                ChatColor.AQUA + "Delete a home point with " + ChatColor.RED + " /delhome <name>",
                ChatColor.AQUA + "View your homes with " + ChatColor.RED + " /homes"
        );
        inv.setItem(home3Index, home3Item);

        // Home 4
        int home4Index = main.getConfig().getInt("home4.position");
        String home4Name = main.getConfig().getString("home4.name");
        int price4 = (int) configManager.getHome4Price();
        ItemStack home4Item = createMenuItem(
                ChatColor.GOLD + home4Name,
                Material.COMPASS,
                ChatColor.GREEN + "The fourth home point costs: " + ChatColor.RED + price4,
                ChatColor.AQUA + "Set a home point with " + ChatColor.RED + " /sethome <name>",
                ChatColor.AQUA + "Delete a home point with " + ChatColor.RED + " /delhome <name>",
                ChatColor.AQUA + "View your homes with " + ChatColor.RED + " /homes"
        );
        inv.setItem(home4Index, home4Item);

        // Home 5
        int home5Index = main.getConfig().getInt("home5.position");
        String home5Name = main.getConfig().getString("home5.name");
        int price5 = (int) configManager.getHome5Price();
        ItemStack home5Item = createMenuItem(
                ChatColor.GOLD + home5Name,
                Material.COMPASS,
                ChatColor.GREEN + "The fifth home point costs: " + ChatColor.RED + price5,
                ChatColor.AQUA + "Set a home point with " + ChatColor.RED + " /sethome <name>",
                ChatColor.AQUA + "Delete a home point with " + ChatColor.RED + " /delhome <name>",
                ChatColor.AQUA + "View your homes with " + ChatColor.RED + " /homes"
        );
        inv.setItem(home5Index, home5Item);

        // Back button to return to the main menu
        int backIndex = main.getConfig().getInt("backItemEconomyMenu.position");
        String backItemName = main.getConfig().getString("backItemEconomyMenu.name");
        ItemStack backItem = createMenuItem(
                ChatColor.GOLD + backItemName,
                Material.BARRIER,
                ChatColor.GREEN + "Return to Main Menu"
        );
        inv.setItem(backIndex, backItem);
    }
}
