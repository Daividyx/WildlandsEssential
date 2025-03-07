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

public class GuiEconomyLogic implements GuiMenu {

    Main main;
    Player player;

    public GuiEconomyLogic(Main main, Player player) {
        this.main = main;
        this.player = player;
    }

    @Override
    public void openMenu() {
        player.openInventory(createMenu());
    }

    @Override
    public Inventory createMenu() {
        Inventory economyMenu = Bukkit.createInventory(null, 45, "Economy Menu");

        // Menü-Items aus der Config erstellen
        ItemStack balance = createMenuItem(main.getConfig().getString("balanceItem.name", "Kontostand"), Material.GOLD_INGOT, main.getConfig().getString("balanceItem.lore", "Dein aktueller Kontostand"));
        ItemStack info = createMenuItem(main.getConfig().getString("infoItem.name", "Info"), Material.BOOK, main.getConfig().getString("infoItem.lore", "Hier gibt es Infos zum Economy-System"));
        ItemStack back = createBackItem();

        // Indexe aus der Config oder Standardwerte nutzen
        int balanceIndex = main.getConfig().getInt("balanceItem.position");
        int infoIndex = main.getConfig().getInt("infoItem.position");
        int backIndex = main.getConfig().getInt("backItemEconomyMenu.position", 36);

        // Items ins Menü setzen
        economyMenu.setItem(balanceIndex, balance);
        economyMenu.setItem(infoIndex, info);
        economyMenu.setItem(backIndex, back);

        // Hintergrund setzen
        ItemStack background = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        for (int i = 0; i < 45; i++) {
            if (!(i == balanceIndex || i == infoIndex || i == backIndex)) {
                economyMenu.setItem(i, background);
            }
        }
        return economyMenu;
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
