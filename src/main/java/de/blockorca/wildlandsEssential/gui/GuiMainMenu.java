package de.blockorca.wildlandsEssential.gui;

import de.blockorca.wildlandsEssential.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Represents the Main Menu GUI for the WildlandsEssential plugin.
 *
 * <p>This menu provides players with access to core functionalities such as Economy, Homes, Warps,
 * DeadChest, and Buyable Functions.</p>
 */
public class GuiMainMenu extends AbstractMenu {

    /**
     * Constructs a new GuiMainMenu instance.
     *
     * @param main   the main plugin instance used to retrieve configuration settings.
     * @param player the player for whom the menu is displayed.
     */
    public GuiMainMenu(Main main, Player player) {
        super(main, player, "Hauptmenü", 45);
    }

    /**
     * Places the menu items in the inventory.
     *
     * <p>This method retrieves configuration values for positions, names, and lore for each menu item,
     * creates the corresponding items, and assigns them to their designated slots in the inventory.</p>
     *
     * @param inv the inventory in which the menu items are placed.
     */
    @Override
    protected void placeItems(Inventory inv) {
        // Economy item
        int economyIndex = main.getConfig().getInt("economyItem.position");
        String economyItemName = main.getConfig().getString("economyItem.name");
        String economyItemLore = main.getConfig().getString("economyItem.lore");
        ItemStack economyItem = createMenuItem(
                ChatColor.GOLD + economyItemName,
                Material.GOLD_INGOT,
                ChatColor.GREEN + economyItemLore
        );
        inv.setItem(economyIndex, economyItem);

        // Homes item
        int homeIndex = main.getConfig().getInt("homeItem.position");
        String homeItemName = main.getConfig().getString("homeItem.name");
        String homeItemLore = main.getConfig().getString("homeItem.lore");
        ItemStack homeItem = createMenuItem(
                ChatColor.GOLD + homeItemName,
                Material.RED_BED,
                ChatColor.GREEN + homeItemLore
        );
        inv.setItem(homeIndex, homeItem);

        // Warps item
        int warpIndex = main.getConfig().getInt("warpItem.position");
        String warpItemName = main.getConfig().getString("warpItem.name");
        String warpItemLore = main.getConfig().getString("warpItem.lore");
        ItemStack warpItem = createMenuItem(
                ChatColor.GOLD + warpItemName,
                Material.ENDER_PEARL,
                ChatColor.GREEN + warpItemLore
        );
        inv.setItem(warpIndex, warpItem);

        // DeadChest item
        int deadChestIndex = main.getConfig().getInt("deadChestItem.position");
        String deadChestItemName = main.getConfig().getString("deadChestItem.name");
        String deadChestItemLore = main.getConfig().getString("deadChestItem.lore");
        ItemStack deadChestItem = createMenuItem(
                ChatColor.GOLD + deadChestItemName,
                Material.CHEST,
                ChatColor.GREEN + deadChestItemLore
        );
        inv.setItem(deadChestIndex, deadChestItem);

        // Buyable Functions item
        int buyableFunctionsIndex = main.getConfig().getInt("buyableItem.position");
        String buyableItemName = main.getConfig().getString("buyableItem.name");
        String buyableItemLore = main.getConfig().getString("buyableItem.lore");
        ItemStack buayable = createMenuItem(
                ChatColor.GOLD + buyableItemName,
                Material.NETHER_STAR,
                ChatColor.GREEN + buyableItemLore
        );
        inv.setItem(buyableFunctionsIndex, buayable);
    }
}
