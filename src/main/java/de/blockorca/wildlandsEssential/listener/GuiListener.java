package de.blockorca.wildlandsEssential.listener;

import de.blockorca.wildlandsEssential.Main;
import de.blockorca.wildlandsEssential.gui.*;
import org.bukkit.ChatColor;
import org.bukkit.block.data.type.Barrel;
import org.bukkit.block.data.type.Switch;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

public class GuiListener implements Listener {

    private final Main main;

    public GuiListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        String inventoryTitle = event.getView().getTitle();

        if (clickedItem == null || clickedItem.getType() == Material.AIR) {
            return;
        }

        String menuTitle = event.getView().getTitle();
        event.setCancelled(true); // Verhindert das Herausnehmen von Items

        switch (menuTitle) {
            case "Hauptmenü":
                handleMainMenuClick(player, clickedItem);
                break;
            case "Bank":
                handleEconomyMenuClick(player, clickedItem);
                break;
            case "Homes":
                handleHomesMenuClick(player, clickedItem);
                break;
            case "Warp Menü":
                handleWarpMenuClick(player, clickedItem);
                break;
            case "DeadChest Menü":
                handleDeadChestMenuClick(player, clickedItem);
                break;
            case "Kaufbare Funktionen":
                handleBuyableMenuClick(player, clickedItem);
                break;
            case "Kauf Bestätigen":
                handleConfirmMenuClick(player, clickedItem);
                break;
        }
    }

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
                // Falls keiner der obigen Fälle zutrifft, passiert nichts
                break;
        }
    }


    private void handleEconomyMenuClick(Player player, ItemStack clickedItem) {
        if (clickedItem.getType() == Material.BARRIER) {

            new GuiMainMenu(main, player).openMenu();
        }
    }

    private void handleHomesMenuClick(Player player, ItemStack clickedItem) {

        switch (ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName())) {

            case "Zurück":
                new GuiMainMenu(main, player).openMenu();
                break;
            case "Home 1":
                new GuiConfirm(main, player,clickedItem,"Homes").openMenu();
                break;
            case "Home 2":
                new GuiConfirm(main, player,clickedItem,"Homes").openMenu();
                break;
            case "Home 3":
                new GuiConfirm(main, player,clickedItem,"Homes").openMenu();
                break;
            case "Home 4":
                new GuiConfirm(main, player,clickedItem,"Homes").openMenu();
                break;
            case "Home 5":
                new GuiConfirm(main, player,clickedItem,"Homes").openMenu();
                break;
        }
    }

    private void handleWarpMenuClick(Player player, ItemStack clickedItem) {
        switch (ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName())) {
            case "Zurück":
                new GuiMainMenu(main, player).openMenu();
                break;
            case "Warp 1":
                new GuiConfirm(main, player,clickedItem,"Warp Menü").openMenu();
                break;
            case "Warp 2":
                new GuiConfirm(main, player,clickedItem,"Warp Menü").openMenu();
                break;
            case "Warp 3":
                new GuiConfirm(main, player,clickedItem,"Warp Menü").openMenu();
                break;
            case "Warp 4":
                new GuiConfirm(main, player,clickedItem,"Warp Menü").openMenu();
                break;
            case "Warp 5":
                new GuiConfirm(main, player,clickedItem,"Warp Menü").openMenu();
                break;
        }
    }

    private void handleDeadChestMenuClick(Player player, ItemStack clickedItem) {
        switch (ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName())) {
            case "Zurück":
                new GuiMainMenu(main, player).openMenu();
                break;
            case "Kaufen":
                new GuiConfirm(main, player,clickedItem,"DeadChest Menü").openMenu();
                break;
            case "Teleport":
                new GuiConfirm(main, player,clickedItem,"DeadChest Menü").openMenu();
                break;
        }
    }

    private void handleBuyableMenuClick(Player player, ItemStack clickedItem) {
        switch (clickedItem.getType()) {
            //Zurück
            case BARRIER:
                new GuiMainMenu(main, player).openMenu();
                break;
            //Fliegen
            case FEATHER:
                new GuiConfirm(main, player,clickedItem,"Kaufbare Funktionen").openMenu();
                break;
            //Alleine Schlafen
            case RED_BED:
                new GuiConfirm(main, player,clickedItem,"Kaufbare Funktionen").openMenu();
                break;
            //Item 1
            case LANTERN:
                new GuiConfirm(main, player,clickedItem,"Kaufbare Funktionen").openMenu();
                break;
            //Item 2
            case SOUL_LANTERN:
                new GuiConfirm(main, player,clickedItem,"Kaufbare Funktionen").openMenu();
                break;
            //Item 3
            case SOUL_CAMPFIRE:
                new GuiConfirm(main, player,clickedItem,"Kaufbare Funktionen").openMenu();
                break;

        }
    }

    private void handleConfirmMenuClick(Player player, ItemStack clickedItem) {
        // Holen wir die aktuell geöffnete GUI-Instanz
        GuiConfirm confirmGui = new GuiConfirm(main, player, clickedItem, ""); // Platzhalter
        String previousInventory = confirmGui.getPreviousInventoryTitle();
        ItemStack originalItem = confirmGui.getClickedItem(); // **DAS Item, das wirklich gekauft wird!**

        // Falls originalItem null ist, brechen wir ab
        if (originalItem == null || !originalItem.hasItemMeta() || !originalItem.getItemMeta().hasDisplayName()) {
            player.sendMessage(ChatColor.RED + "Fehler: Kein gültiges Kauf-Item gefunden!");
            return;
        }

        String originalItemName = ChatColor.stripColor(originalItem.getItemMeta().getDisplayName()); // Name ohne Farbcodes

        if (clickedItem.getType() == Material.LIME_WOOL) { // Spieler bestätigt Kauf
            player.sendMessage(ChatColor.GREEN + "Du hast " + originalItemName + " gekauft!");

            // **Hier kommt die Kauf-Logik für das gekaufte Item**
            switch (originalItemName) {
                case "Fliegen kaufen":
                    player.sendMessage(ChatColor.GREEN + "Du kannst jetzt fliegen!");
                    player.setAllowFlight(true);
                    break;

                case "Alleine schlafen":
                    player.sendMessage(ChatColor.GREEN + "Du kannst jetzt alleine schlafen!");
                    // Setze irgendeine Permission oder Variable
                    break;

                case "Warp 1":
                    player.sendMessage(ChatColor.GREEN + "Du hast Warp 1 freigeschaltet!");
                    // Speichere den Warp für den Spieler
                    break;

                case "Kaufen":
                    player.sendMessage(ChatColor.GREEN + "Du hast eine DeadChest gekauft!");
                    // Logik für DeadChest-Kauf
                    break;

                case "Teleport":
                    player.sendMessage(ChatColor.GREEN + "Du wirst zu deiner DeadChest teleportiert!");
                    // Spieler zu gespeicherten Koordinaten teleportieren
                    break;

                default:
                    player.sendMessage(ChatColor.RED + "Unbekannter Kauf: " + originalItemName);
                    break;
            }

        } else if (clickedItem.getType() == Material.RED_WOOL) { // Spieler bricht Kauf ab
            switch (previousInventory) {
                case "Homes":
                    new GuiHome(main, player).openMenu();
                    break;
                case "Warp Menü":
                    new GuiWarp(main, player).openMenu();
                    break;
                case "DeadChest Menü":
                    new GuiDeadChest(main, player).openMenu();
                    break;
                case "Kaufbare Funktionen":
                    new GuiBuyable(main, player).openMenu();
                    break;
            }
        }
    }





}


