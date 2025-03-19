package de.blockorca.wildlandsEssential.gui;

import de.blockorca.wildlandsEssential.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Represents a confirmation GUI for purchase actions.
 *
 * <p>This menu displays two options: confirm (lime wool) or cancel (red wool).
 * The confirm item stores a data string in its lore which is used to process the purchase.</p>
 */
public class GuiConfirm extends AbstractMenu {

    private final String dataString;

    /**
     * Constructs a new confirmation menu.
     *
     * @param main       the main plugin instance for configuration access
     * @param player     the player viewing the menu
     * @param dataString a semicolon‑delimited string containing menu and item identifiers for processing
     */
    public GuiConfirm(Main main, Player player, String dataString) {
        super(main, player, "Kauf Bestätigen", 45);
        this.dataString = dataString;
    }

    /**
     * Places the confirm and cancel items into the inventory.
     *
     * <p>The confirm item (lime wool) includes the data string in its lore for later retrieval.
     * The cancel item (red wool) simply closes the purchase process.</p>
     *
     * @param inv the inventory to populate with menu items
     */
    @Override
    protected void placeItems(Inventory inv) {
        int confirmIndex = main.getConfig().getInt("confirmItem.position");
        String confirmName = main.getConfig().getString("confirmItem.name");
        ItemStack confirmItem = createMenuItem(
                ChatColor.GOLD + confirmName,
                Material.LIME_WOOL,
                ChatColor.GREEN + "Confirm purchase",
                ChatColor.DARK_GRAY + dataString
        );
        inv.setItem(confirmIndex, confirmItem);

        int cancelIndex = main.getConfig().getInt("cancelItem.position");
        String cancelName = main.getConfig().getString("cancelItem.name");
        ItemStack cancelItem = createMenuItem(
                ChatColor.GOLD + cancelName,
                Material.RED_WOOL,
                ChatColor.GREEN + "Cancel purchase"
        );
        inv.setItem(cancelIndex, cancelItem);
    }
}
