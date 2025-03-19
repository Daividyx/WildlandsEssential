package de.blockorca.wildlandsEssential.listener;


import de.blockorca.wildlandsEssential.Main;

import de.blockorca.wildlandsEssential.gui.*;
import de.blockorca.wildlandsEssential.logic.DeadChestLogic;
import de.blockorca.wildlandsEssential.logic.FlyLogic;

import de.blockorca.wildlandsEssential.logic.HomeLogic;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;


import java.util.Arrays;
import java.util.Objects;

/**
 * Listener for handling GUI interactions.
 *
 * <p>This class intercepts inventory click events for specific GUI menus and routes the events
 * to the appropriate handler methods based on the menu title and the clicked item.</p>
 */
public class GuiListener implements Listener {

    private final Main main;

    /**
     * Constructs a new GuiListener.
     *
     * @param main the main plugin instance used to access various components and GUIs.
     */
    public GuiListener(Main main) {
        this.main = main;
    }

    /**
     * Handles inventory click events for blocked GUI menus.
     *
     * <p>If the inventory title matches one of the predefined blocked menus, the event is cancelled
     * and the appropriate handler method is invoked based on the menu title.</p>
     *
     * @param event the inventory click event.
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        String[] blockedMenu = {"Hauptmenü", "Bank", "Homes", "Warp Menü", "DeadChest Menü", "Kaufbare Funktionen", "Kauf Bestätigen"};
        if (Arrays.asList(blockedMenu).contains(event.getView().getTitle())) {
            if (!(event.getWhoClicked() instanceof Player player)) return;

            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

            String menuTitle = event.getView().getTitle();
            event.setCancelled(true); // Prevent item removal from the GUI

            switch (menuTitle) {
                case "Hauptmenü" -> handleMainMenuClick(player, clickedItem);


                case "Bank" -> handleEconomyMenuClick(player, clickedItem);


                case "Homes" -> handleHomesMenuClick(player, clickedItem);


                case "Warp Menü" -> handleWarpMenuClick(player, clickedItem);


                case "DeadChest Menü" -> handleDeadChestMenuClick(player, clickedItem);


                case "Kaufbare Funktionen" -> handleBuyableMenuClick(player, clickedItem);


            }
        }
    }

    /**
     * Processes clicks in the main menu.
     *
     * <p>The method opens the corresponding GUI based on the clicked item type.</p>
     *
     * @param player      the player who clicked.
     * @param clickedItem the item that was clicked.
     */
    private void handleMainMenuClick(Player player, ItemStack clickedItem) {
        switch (clickedItem.getType()) {
            case GOLD_INGOT:
                new GuiEconomy(main, player).openMenu();
                break;
            case RED_BED:
                new GuiHome(main, player).openMenu();
                break;
            case ENDER_PEARL:
                new GuiWarp(main, player).openMenu();
                break;
            case CHEST:
                new GuiDeadChest(main, player).openMenu();
                break;
            case NETHER_STAR:
                new GuiBuyable(main, player).openMenu();
                break;
            default:
                break;
        }
    }

    /**
     * Processes clicks in the economy (bank) menu.
     *
     * <p>If the clicked item is a barrier, the main menu is reopened.</p>
     *
     * @param player      the player who clicked.
     * @param clickedItem the item that was clicked.
     */
    private void handleEconomyMenuClick(Player player, ItemStack clickedItem) {
        if (clickedItem.getType() == Material.BARRIER) {
            new GuiMainMenu(main, player).openMenu();
        }
    }

    /**
     * Processes clicks in the homes menu.
     *
     * <p>The method checks the display name of the clicked item and opens the main menu when "Zurück" is clicked.
     * For other home items ("Home 1" to "Home 5"), additional logic can be implemented.</p>
     *
     * @param player      the player who clicked.
     * @param clickedItem the item that was clicked.
     */
    private void handleHomesMenuClick(Player player, ItemStack clickedItem) {
        String itemName = ChatColor.stripColor(Objects.requireNonNull(clickedItem.getItemMeta()).getDisplayName());

        switch (itemName) {
            case "Zurück" -> new GuiMainMenu(main, player).openMenu();
            case "Home 1" -> new HomeLogic(main).buyHome(player, 1);
            case "Home 2" -> new HomeLogic(main).buyHome(player, 2);
            case "Home 3" -> new HomeLogic(main).buyHome(player, 3);
            case "Home 4" -> new HomeLogic(main).buyHome(player, 4);
            case "Home 5" -> new HomeLogic(main).buyHome(player, 5);


        }
    }

    /**
     * Processes clicks in the warp menu.
     *
     * <p>If "Zurück" is clicked, the main menu is reopened. For warp options ("Warp 1" to "Warp 5"),
     * the confirm GUI is opened with the corresponding data.</p>
     *
     * @param player      the player who clicked.
     * @param clickedItem the item that was clicked.
     */
    private void handleWarpMenuClick(Player player, ItemStack clickedItem) {
        String previousInventoryTitle = "Warp Menü";
        String itemName = ChatColor.stripColor(Objects.requireNonNull(clickedItem.getItemMeta()).getDisplayName());

        switch (itemName) {
            case "Zurück":
                new GuiMainMenu(main, player).openMenu();
                break;
            case "Warp 1":
            case "Warp 2":
            case "Warp 3":
            case "Warp 4":
            case "Warp 5":
                String combined = previousInventoryTitle + ";" + itemName;
                new GuiConfirm(main, player, combined).openMenu();
                break;
        }
    }

    /**
     * Processes clicks in the DeadChest menu.
     *
     * <p>If "Zurück" is clicked, the main menu is reopened. If "DeadChest Kaufen" is clicked,
     * the DeadChest unlock purchase process is initiated. Additional functionality for "Teleport"
     * can be added as needed.</p>
     *
     * @param player      the player who clicked.
     * @param clickedItem the item that was clicked.
     */
    private void handleDeadChestMenuClick(Player player, ItemStack clickedItem) {
        String itemName = ChatColor.stripColor(Objects.requireNonNull(clickedItem.getItemMeta()).getDisplayName());

        switch (itemName) {
            case "Zurück":
                new GuiMainMenu(main, player).openMenu();
                break;
            case "DeadChest Kaufen":
                new DeadChestLogic(main).buyDeadchestUnlock(player);
                // Note: Missing break statement intentionally if "Teleport" should be processed in succession.
            case "Teleport":
                // Implement teleport logic here if required
                break;
        }
    }

    /**
     * Processes clicks in the buyable functions menu.
     *
     * <p>This method handles purchase actions based on the type of the clicked item.
     * For example, clicking a feather enables flight, while clicking a barrier returns the player
     * to the main menu. Additional cases can be implemented for other buyable items.</p>
     *
     * @param player      the player who clicked.
     * @param clickedItem the item that was clicked.
     */
    private void handleBuyableMenuClick(Player player, ItemStack clickedItem) {

        switch (clickedItem.getType()) {
            case BARRIER:
                new GuiMainMenu(main, player).openMenu();
                break;
            case FEATHER:
                // Enable flight functionality
                FlyLogic flyLogic = new FlyLogic(main);
                flyLogic.enableFly(player);
                player.closeInventory();
                break;
            case RED_BED:
                // Logic for sleeping alone can be implemented here
                break;
            case LANTERN:
                // Additional buyable item logic can be added here
                break;
            case SOUL_LANTERN:
                // Additional buyable item logic can be added here
                break;
            case SOUL_CAMPFIRE:
                // Additional buyable item logic can be added here
                break;
            default:
                break;
        }
    }

}
