package de.blockorca.wildlandsEssential.listener;

import de.blockorca.wildlandsEssential.Main;
import de.blockorca.wildlandsEssential.gui.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
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

        if (clickedItem == null || clickedItem.getType() == Material.AIR) {
            return;
        }

        String menuTitle = event.getView().getTitle();
        event.setCancelled(true); // Verhindert das Herausnehmen von Items

        switch (menuTitle) {
            case "Main Menu":
                handleMainMenuClick(player, clickedItem);
                break;
            case "Economy Menu":
                handleEconomyMenuClick(player, clickedItem);
                break;
            case "Warp Menu":
                handleWarpMenuClick(player, clickedItem);
                break;
            case "DeadChest Menu":
                handleDeadChestMenuClick(player, clickedItem);
                break;
            case "Buyable Menu":
                handleBuyableMenuClick(player, clickedItem);
                break;
            case "Confirm Purchase":
                handleConfirmMenuClick(player, clickedItem);
                break;
        }
    }

    private void handleMainMenuClick(Player player, ItemStack clickedItem) {
        if (clickedItem.getType() == Material.GOLD_INGOT) {
            new GuiEconomyLogic(main, player).openMenu();
        } else if (clickedItem.getType() == Material.ENDER_PEARL) {
            new GuiWarpLogic(main, player).openMenu();
        } else if (clickedItem.getType() == Material.CHEST) {
            new GuiDeadChestLogic(main, player).openMenu();
        } else if (clickedItem.getType() == Material.NETHER_STAR) {
            new GuiBuyableLogic(main, player).openMenu();
        }
    }

    private void handleEconomyMenuClick(Player player, ItemStack clickedItem) {
        if (clickedItem.getType() == Material.BARRIER) {
            new GuiMainLogic(main, player).openMenu();
        }
    }

    private void handleWarpMenuClick(Player player, ItemStack clickedItem) {
        if (clickedItem.getType() == Material.BARRIER) {
            new GuiMainLogic(main, player).openMenu();
        }
    }

    private void handleDeadChestMenuClick(Player player, ItemStack clickedItem) {
        if (clickedItem.getType() == Material.BARRIER) {
            new GuiMainLogic(main, player).openMenu();
        }   else if (clickedItem.getType() == Material.EMERALD) {
        new GuiConfirmPurchase(main, player).openMenu();
    }
    }

    private void handleBuyableMenuClick(Player player, ItemStack clickedItem) {
        if (clickedItem.getType() == Material.BARRIER) {
            new GuiMainLogic(main, player).openMenu();
        } else if (clickedItem.getType() == Material.FEATHER || clickedItem.getType() == Material.RED_BED) {
            new GuiConfirmPurchase(main, player).openMenu();
        }
    }

    private void handleConfirmMenuClick(Player player, ItemStack clickedItem) {
        if (clickedItem.getType() == Material.RED_WOOL) {
            new GuiBuyableLogic(main, player).openMenu();
        }
    }
}
