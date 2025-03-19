package de.blockorca.wildlandsEssential.gui;

import de.blockorca.wildlandsEssential.Main;
import de.blockorca.wildlandsEssential.data.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Represents the Buyable Functions menu GUI.
 *
 * <p>This menu displays various purchasable features for the player,
 * including flight ability, solo sleep, and additional custom items.
 * A back button allows returning to the main menu.</p>
 */
public class GuiBuyable extends AbstractMenu {

    private final ConfigManager configManager;

    /**
     * Constructs a new GuiBuyable instance.
     *
     * @param main   the main plugin instance used for configuration access
     * @param player the player for whom the menu is displayed
     */
    public GuiBuyable(Main main, Player player) {
        super(main, player, "Kaufbare Funktionen", 45);
        this.configManager = main.getConfigManager();
    }

    /**
     * Populates the inventory with buyable feature items.
     *
     * <p>This method retrieves each feature's configuration for position, name, and price,
     * then creates and places corresponding menu items into the inventory.
     * A back button is added to return to the main menu.</p>
     *
     * @param inv the inventory in which menu items are placed
     */
    @Override
    protected void placeItems(Inventory inv) {
        // Flight purchase
        int flyIndex = main.getConfig().getInt("buyableItem1.position");
        String flyName = main.getConfig().getString("buyableItem1.name");
        int flyPrice = (int) configManager.getFlyPrice();
        ItemStack flyItem = createMenuItem(
                ChatColor.GOLD + flyName,
                Material.FEATHER,
                ChatColor.GREEN + "Price: " + ChatColor.RED + flyPrice,
                ChatColor.AQUA + "Purchase the ability to fly for 1 hour."
        );
        inv.setItem(flyIndex, flyItem);

        // Solo sleep purchase
        int sleepIndex = main.getConfig().getInt("buyableItem2.position");
        String sleepName = main.getConfig().getString("buyableItem2.name");
        int sleepPrice = (int) configManager.getAloneSleepPrice();
        ItemStack sleepItem = createMenuItem(
                ChatColor.GOLD + sleepName,
                Material.RED_BED,
                ChatColor.GREEN + "Price: " + ChatColor.RED + sleepPrice,
                ChatColor.AQUA + "Allows you to skip the night alone."
        );
        inv.setItem(sleepIndex, sleepItem);

        // Custom buyable item 1
        int item1Index = main.getConfig().getInt("buyableItem3.position");
        String item1Name = main.getConfig().getString("buyableItem3.name");
        int item1Price = (int) configManager.getBuyable1Price();
        ItemStack item1 = createMenuItem(
                ChatColor.GOLD + item1Name,
                Material.LANTERN,
                ChatColor.GREEN + "Price: " + ChatColor.RED + item1Price,
                ChatColor.AQUA + "A custom buyable item."
        );
        inv.setItem(item1Index, item1);

        // Custom buyable item 2
        int item2Index = main.getConfig().getInt("buyableItem4.position");
        String item2Name = main.getConfig().getString("buyableItem4.name");
        int item2Price = (int) configManager.getBuyable2Price();
        ItemStack item2 = createMenuItem(
                ChatColor.GOLD + item2Name,
                Material.SOUL_LANTERN,
                ChatColor.GREEN + "Price: " + ChatColor.RED + item2Price,
                ChatColor.AQUA + "Another custom buyable item."
        );
        inv.setItem(item2Index, item2);

        // Custom buyable item 3
        int item3Index = main.getConfig().getInt("buyableItem5.position");
        String item3Name = main.getConfig().getString("buyableItem5.name");
        int item3Price = (int) configManager.getBuyable3Price();
        ItemStack item3 = createMenuItem(
                ChatColor.GOLD + item3Name,
                Material.SOUL_CAMPFIRE,
                ChatColor.GREEN + "Price: " + ChatColor.RED + item3Price,
                ChatColor.AQUA + "A rare custom buyable item."
        );
        inv.setItem(item3Index, item3);

        // Back button to main menu
        int backIndex = main.getConfig().getInt("backItemBuyableMenu.position");
        String backItemName = main.getConfig().getString("backItemBuyableMenu.name");
        ItemStack backItem = createMenuItem(
                ChatColor.GOLD + backItemName,
                Material.BARRIER,
                ChatColor.GREEN + "Return to Main Menu"
        );
        inv.setItem(backIndex, backItem);
    }
}
