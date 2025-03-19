package de.blockorca.wildlandsEssential.gui;

import de.blockorca.wildlandsEssential.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Abstract base class for creating inventory‑based GUI menus.
 *
 * <p>This class handles common functionality such as opening inventories, setting a background,
 * and providing a helper method for creating menu items. Subclasses must implement {@link #placeItems(Inventory)}
 * to populate the inventory with custom items.</p>
 */
public abstract class AbstractMenu implements GuiMenu {

    protected final Main main;
    protected final Player player;
    private final String title;
    private final int size;

    /**
     * Constructs a new AbstractMenu.
     *
     * @param main   the main plugin instance used for configuration and utilities
     * @param player the player for whom this menu will be displayed
     * @param title  the title to display at the top of the inventory
     * @param size   the size of the inventory (must be a multiple of 9)
     */
    public AbstractMenu(Main main, Player player, String title, int size) {
        this.main = main;
        this.player = player;
        this.title = title;
        this.size = size;
    }

    /**
     * Opens this menu for the player by creating and displaying the inventory.
     */
    @Override
    public void openMenu() {
        player.openInventory(createMenu());
    }

    /**
     * Creates the inventory for this menu, fills it with a black stained glass pane background,
     * and calls {@link #placeItems(Inventory)} to add custom items.
     *
     * @return the newly created inventory representing this GUI
     */
    @Override
    public Inventory createMenu() {
        Inventory inv = Bukkit.createInventory(null, size, title);
        ItemStack background = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);

        for (int i = 0; i < size; i++) {
            inv.setItem(i, background);
        }

        placeItems(inv);
        return inv;
    }

    /**
     * Populates the given inventory with menu items specific to the subclass.
     *
     * @param inv the inventory to populate
     */
    protected abstract void placeItems(Inventory inv);

    /**
     * Creates a menu item with a display name, material, and optional lore lines.
     *
     * @param name      the display name of the item
     * @param material  the material type of the item
     * @param loreLines one or more lines of lore describing the item
     * @return a fully configured ItemStack for use in the GUI
     */
    protected ItemStack createMenuItem(String name, Material material, String... loreLines) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

            assert meta != null;
            meta.setDisplayName(name);


        List<String> lore = new ArrayList<>(Arrays.asList(loreLines));
        meta.setLore(lore);

        item.setItemMeta(meta);
        return item;
    }
}
