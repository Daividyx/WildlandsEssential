package de.blockorca.wildlandsEssential.gui;

import de.blockorca.wildlandsEssential.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GuiDeadChestLogic implements GuiMenu {

    Main main;
    Player player;

    public GuiDeadChestLogic(Main main, Player player) {
        this.main = main;
        this.player = player;
    }

    @Override
    public void openMenu() {
        player.openInventory(createMenu());
    }

    @Override
    public Inventory createMenu() {
        Inventory deadChestMenu = Bukkit.createInventory(null, 45, "DeadChest Menu");

        // Menü-Items erstellen
        ItemStack buyBack = createMenuItem("Kaufen", Material.EMERALD, "Kaufe deine Items zurück");
        ItemStack info = createMenuItem("Info", Material.BOOK, "Informationen zur DeadChest");
        ItemStack back = createBackItem();

        // Indexe aus der Config oder Standardwerte nutzen
        int buyBackIndex = main.getConfig().getInt("BuyBackItem.position");
        int infoIndex = main.getConfig().getInt("InfoItem.position");
        int backIndex = main.getConfig().getInt("BackItemDeadChestMenu.position", 36);

        // Items ins Menü setzen
        deadChestMenu.setItem(buyBackIndex, buyBack);
        deadChestMenu.setItem(infoIndex, info);
        deadChestMenu.setItem(backIndex, back);

        // Hintergrund setzen
        ItemStack background = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        for (int i = 0; i < 45; i++) {
            if (!(i == buyBackIndex || i == infoIndex || i == backIndex)) {
                deadChestMenu.setItem(i, background);
            }
        }
        return deadChestMenu;
    }

    private ItemStack createMenuItem(String name, Material material, String loreText) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + name);
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GREEN + loreText);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack createBackItem() {
        ItemStack back = new ItemStack(Material.BARRIER);
        ItemMeta backMeta = back.getItemMeta();
        backMeta.setDisplayName(ChatColor.RED + "Zurück");
        List<String> backLore = new ArrayList<>();
        backLore.add(ChatColor.GRAY + "Zurück zum Hauptmenü");
        backMeta.setLore(backLore);
        back.setItemMeta(backMeta);
        return back;
    }
}
