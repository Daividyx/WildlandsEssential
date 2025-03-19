package de.blockorca.wildlandsEssential.gui;

import de.blockorca.wildlandsEssential.Main;
import de.blockorca.wildlandsEssential.data.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Represents the Warp Menu GUI.
 *
 * <p>This class creates a graphical user interface displaying multiple warp options.
 * Each warp option is represented as an item that includes its name, cost, and teleportation instructions.
 * A back button is also provided to allow the player to return to the main menu.</p>
 */
public class GuiWarp extends AbstractMenu {

    private final ConfigManager configManager;

    /**
     * Constructs a new GuiWarp instance.
     *
     * @param main   the main plugin instance used for configuration and utility access.
     * @param player the player for whom the GUI is displayed.
     */
    public GuiWarp(Main main, Player player) {
        super(main, player, "Warp Menü", 45);
        this.configManager = main.getConfigManager();
    }

    /**
     * Places the warp menu items in the inventory.
     *
     * <p>This method retrieves configuration settings for each warp option including position, name, and cost.
     * It creates an item for each warp option and assigns it to the specified slot in the inventory.
     * Finally, it creates a back button for returning to the main menu.</p>
     *
     * @param inv the inventory in which the menu items are placed.
     */
    @Override
    protected void placeItems(Inventory inv) {
        // Create Warp 1 option
        int warp1Index = main.getConfig().getInt("warp1.position");
        String warp1Name = main.getConfig().getString("warp1.name");
        int price1 = (int) configManager.getWarp1Price();
        ItemStack warp1Item = createMenuItem(
                ChatColor.GOLD + warp1Name,
                Material.ENDER_PEARL,
                ChatColor.GREEN + "Cost: " + ChatColor.RED + price1,
                ChatColor.AQUA + "Use " + ChatColor.RED + " /warp " + warp1Name + ChatColor.AQUA + " to teleport"
        );
        inv.setItem(warp1Index, warp1Item);

        // Create Warp 2 option
        int warp2Index = main.getConfig().getInt("warp2.position");
        String warp2Name = main.getConfig().getString("warp2.name");
        int price2 = (int) configManager.getWarp2Price();
        ItemStack warp2Item = createMenuItem(
                ChatColor.GOLD + warp2Name,
                Material.ENDER_PEARL,
                ChatColor.GREEN + "Cost: " + ChatColor.RED + price2,
                ChatColor.AQUA + "Use " + ChatColor.RED + " /warp " + warp2Name + ChatColor.AQUA + " to teleport"
        );
        inv.setItem(warp2Index, warp2Item);

        // Create Warp 3 option
        int warp3Index = main.getConfig().getInt("warp3.position");
        String warp3Name = main.getConfig().getString("warp3.name");
        int price3 = (int) configManager.getWarp3Price();
        ItemStack warp3Item = createMenuItem(
                ChatColor.GOLD + warp3Name,
                Material.ENDER_PEARL,
                ChatColor.GREEN + "Cost: " + ChatColor.RED + price3,
                ChatColor.AQUA + "Use " + ChatColor.RED + " /warp " + warp3Name + ChatColor.AQUA + " to teleport"
        );
        inv.setItem(warp3Index, warp3Item);

        // Create Warp 4 option
        int warp4Index = main.getConfig().getInt("warp4.position");
        String warp4Name = main.getConfig().getString("warp4.name");
        int price4 = (int) configManager.getWarp4Price();
        ItemStack warp4Item = createMenuItem(
                ChatColor.GOLD + warp4Name,
                Material.ENDER_PEARL,
                ChatColor.GREEN + "Cost: " + ChatColor.RED + price4,
                ChatColor.AQUA + "Use " + ChatColor.RED + " /warp " + warp4Name + ChatColor.AQUA + " to teleport"
        );
        inv.setItem(warp4Index, warp4Item);

        // Create Warp 5 option
        int warp5Index = main.getConfig().getInt("warp5.position");
        String warp5Name = main.getConfig().getString("warp5.name");
        int price5 = (int) configManager.getWarp5Price();
        ItemStack warp5Item = createMenuItem(
                ChatColor.GOLD + warp5Name,
                Material.ENDER_PEARL,
                ChatColor.GREEN + "Cost: " + ChatColor.RED + price5,
                ChatColor.AQUA + "Use " + ChatColor.RED + " /warp " + warp5Name + ChatColor.AQUA + " to teleport"
        );
        inv.setItem(warp5Index, warp5Item);

        // Create back button to return to the main menu
        int backIndex = main.getConfig().getInt("backItemWarpMenu.position");
        String backItemName = main.getConfig().getString("backItemWarpMenu.name");
        ItemStack backItem = createMenuItem(
                ChatColor.GOLD + backItemName,
                Material.BARRIER,
                ChatColor.GREEN + "Return to Main Menu"
        );
        inv.setItem(backIndex, backItem);
    }
}
