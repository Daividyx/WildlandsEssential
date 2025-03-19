package de.blockorca.wildlandsEssential.gui;


import org.bukkit.inventory.Inventory;

/**
 * Defines the contract for GUI menus in the WildlandsEssential plugin.
 *
 * <p>This interface provides methods for creating and opening custom inventories
 * that serve as graphical user interfaces for players.</p>
 */
public interface GuiMenu {

    /**
     * Opens the GUI menu for the associated player.
     */
    void openMenu();

    /**
     * Creates and returns the inventory representing the GUI menu.
     *
     * @return the inventory used as the GUI menu.
     */
    Inventory createMenu();
}
