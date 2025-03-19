package de.blockorca.wildlandsEssential.gui;

import de.blockorca.wildlandsEssential.Main;
import de.blockorca.wildlandsEssential.data.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Represents the DeadChest menu GUI.
 *
 * <p>This menu displays options for purchasing and teleporting to the player’s DeadChest,
 * along with its stored coordinates and status. A back button returns the player to the main menu.</p>
 */
public class GuiDeadChest extends AbstractMenu {

    private final ConfigManager configManager;

    /**
     * Constructs a new DeadChest menu for the given player.
     *
     * @param main   the main plugin instance used for configuration access
     * @param player the player for whom the menu is displayed
     */
    public GuiDeadChest(Main main, Player player) {
        super(main, player, "DeadChest Menü", 45);
        this.configManager = main.getConfigManager();
    }

    /**
     * Populates the inventory with DeadChest-related menu items.
     *
     * <p>This method retrieves the DeadChest coordinates and prices from the configuration,
     * creates items for purchasing and teleporting to the DeadChest, and adds a back button.</p>
     *
     * @param inv the inventory into which items will be placed
     */
    @Override
    protected void placeItems(Inventory inv) {
        int x = configManager.getDeadChestX(player);
        int y = configManager.getDeadChestY(player);
        int z = configManager.getDeadChestZ(player);
        String coords = ChatColor.AQUA + "Coordinates: " + ChatColor.RED + "(" + x + ", " + y + ", " + z + ")";

        // Purchase DeadChest item
        int buyIndex = main.getConfig().getInt("buyItem.position");
        String buyName = main.getConfig().getString("buyItem.name");
        int buyPrice = (int) configManager.getDeadChestOpenPrice();
        ItemStack buyItem = createMenuItem(
                ChatColor.GOLD + buyName,
                Material.EMERALD,
                ChatColor.GREEN + "Price: $" + buyPrice,
                ChatColor.AQUA + "DeadChest exists? " + ChatColor.RED + configManager.isDeadChest(player),
                coords,
                ChatColor.YELLOW + "Purchase your items back!",
                ChatColor.YELLOW + "You must be within 5 blocks of the chest to redeem."
        );
        inv.setItem(buyIndex, buyItem);

        // Teleport to DeadChest item
        int teleportIndex = main.getConfig().getInt("teleportItem.position");
        String teleportName = main.getConfig().getString("teleportItem.name", "Teleport");
        int teleportPrice = (int) configManager.getDeadChestTeleportPrice();
        ItemStack teleportItem = createMenuItem(
                ChatColor.GOLD + teleportName,
                Material.ENDER_PEARL,
                ChatColor.GREEN + "Price: $" + teleportPrice,
                coords,
                ChatColor.YELLOW + "Teleport to your DeadChest!",
                ChatColor.YELLOW + "You still need to purchase access after teleporting."
        );
        inv.setItem(teleportIndex, teleportItem);

        // Back button to main menu
        int backIndex = main.getConfig().getInt("backItemDeadChestMenu.position");
        String backName = main.getConfig().getString("backItemDeadChestMenu.name", "Zurück");
        ItemStack backItem = createMenuItem(
                ChatColor.RED + backName,
                Material.BARRIER,
                ChatColor.YELLOW + "Return to Main Menu"
        );
        inv.setItem(backIndex, backItem);
    }
}
