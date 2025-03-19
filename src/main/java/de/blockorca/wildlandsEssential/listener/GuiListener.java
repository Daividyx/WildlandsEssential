package de.blockorca.wildlandsEssential.listener;

import com.earth2me.essentials.User;
import de.blockorca.wildlandsEssential.Main;
import de.blockorca.wildlandsEssential.economy.EconomyManager;
import de.blockorca.wildlandsEssential.gui.*;
import de.blockorca.wildlandsEssential.logic.DeadChestLogic;
import de.blockorca.wildlandsEssential.logic.FlyLogic;
import net.ess3.api.MaxMoneyException;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GuiListener implements Listener {

    private final Main main;

    public GuiListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        String[] blockedMenu = {"Hauptmenü","Bank","Homes","Warp Menü","DeadChest Menü","Kaufbare Funktionen","Kauf Bestätigen"};
        if(Arrays.asList(blockedMenu).contains(event.getView().getTitle())) {
            if (!(event.getWhoClicked() instanceof Player)) return;

            Player player = (Player) event.getWhoClicked();
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

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
    }

    // ============================
    // 1) HAUPTMENÜ
    // ============================
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

    // ============================
    // 2) BANK (Beispiel)
    // ============================
    private void handleEconomyMenuClick(Player player, ItemStack clickedItem) {
        if (clickedItem.getType() == Material.BARRIER) {
            new GuiMainMenu(main, player).openMenu();
        }
    }

    // ============================
    // 3) HOMES
    // ============================
    private void handleHomesMenuClick(Player player, ItemStack clickedItem) {
        String previousInventoryTitle = "Homes";
        String itemName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());

        switch (itemName) {
            case "Zurück":
                new GuiMainMenu(main, player).openMenu();
                break;
            case "Home 1":
            case "Home 2":
            case "Home 3":
            case "Home 4":
            case "Home 5":

                break;
        }
    }

    // ============================
    // 4) WARP
    // ============================
    private void handleWarpMenuClick(Player player, ItemStack clickedItem) {
        String previousInventoryTitle = "Warp Menü";
        String itemName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());

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

    // ============================
    // 5) DEADCHEST
    // ============================
    private void handleDeadChestMenuClick(Player player, ItemStack clickedItem) {
        String previousInventoryTitle = "DeadChest Menü";
        String itemName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName());

        switch (itemName) {
            case "Zurück":
                new GuiMainMenu(main, player).openMenu();
                break;
            case "DeadChest Kaufen":
                new DeadChestLogic(main).buyDeadchestUnlock(player);
            case "Teleport":


                break;
        }
    }

    // ============================
    // 6) KAUFBARE FUNKTIONEN
    // ============================
    private void handleBuyableMenuClick(Player player, ItemStack clickedItem) {
        String previousInventoryTitle = "Kaufbare Funktionen";

        switch (clickedItem.getType()) {
            case BARRIER:
                new GuiMainMenu(main, player).openMenu();
                break;
            case FEATHER:
                // Fliegen
                FlyLogic flyLogic = new FlyLogic(main);
                flyLogic.enableFly(player);
                player.closeInventory();
            case RED_BED:
                // Alleine schlafen

            case LANTERN:
                // Item 1

            case SOUL_LANTERN:
                // Item 2

            case SOUL_CAMPFIRE:
                // Item 3

        }
    }

    // ============================
    // 7) CONFIRM (Ja/Nein)
    // ============================
    private void handleConfirmMenuClick(Player player, ItemStack clickedItem) {
        // Klick auf LIME_WOOL => Bestätigen, Klick auf RED_WOOL => Abbrechen
        if (clickedItem.getType() == Material.LIME_WOOL) {
            // Lese Lore aus
            List<String> lore = clickedItem.getItemMeta().getLore();
            if (lore == null || lore.isEmpty()) {
                player.sendMessage(ChatColor.RED + "Fehler: Keine Lore-Daten gefunden!");
                return;
            }
            // Letzte Zeile z.B. "Homes;Home 1"
            String dataLine = ChatColor.stripColor(lore.get(lore.size() - 1));
            String[] parts = dataLine.split(";");
            if (parts.length < 2) {
                player.sendMessage(ChatColor.RED + "Fehler: Ungültige Daten: " + dataLine);
                return;
            }
            String previousMenu = parts[0];
            String itemName = parts[1];

            // KAUF-LOGIK
           // player.sendMessage(ChatColor.GREEN + "Du hast " + itemName + " aus " + previousMenu + " gekauft!");
            switch (itemName) {
                case "Home 1":
                case "Home 2":
                case "Home 3":
                case "Home 4":
                case "Home 5":

                    //Warp Logic
                case "Warp 1":
                case "Warp 2":
                case "Warp 3":
                case "Warp 4":
                case "Warp 5":
                    //DeadChest
                case "a":
                case "aa":
                case "aaa":
                case "aaaa":
                case "aaaaa":
                    //buyable
                case "Fliegen kaufen":
                    FlyLogic flyLogic = new FlyLogic(main);
                    flyLogic.enableFly(player);
                    player.closeInventory();

            }

            } else if (clickedItem.getType() == Material.RED_WOOL) {
                // Abbrechen => Evtl. kein Lore => Dann Standardbehandlung
                // Du könntest hier z.B. "Zurück zum Hauptmenü" machen oder
                // Lore abfragen wie oben.
                // Ich zeige dir hier einfach "Zurück zum Hauptmenü":
                new GuiMainMenu(main, player).openMenu();
            }
        }
    }
