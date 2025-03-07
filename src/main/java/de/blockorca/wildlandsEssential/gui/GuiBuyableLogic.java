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

public class GuiBuyableLogic implements GuiMenu {

    Main main;
    Player player;

    public GuiBuyableLogic(Main main, Player player) {
        this.main = main;
        this.player = player;
    }

    @Override
    public void openMenu() {
        player.openInventory(createMenu());
    }

    @Override
    public Inventory createMenu() {
        Inventory buyableMenu = Bukkit.createInventory(null, 45, "Buyable Menu");

        // Menü-Items aus der Config erstellen
        ItemStack fly = createMenuItem(main.getConfig().getString("FlyItem.name", "Fly"), Material.FEATHER, "Fliegen für 24h kaufen");
        ItemStack sleep = createMenuItem(main.getConfig().getString("SleepItem.name", "Allein Schlafen"), Material.RED_BED, "Dauerhaft allein schlafen können");
        ItemStack item1 = createMenuItem(main.getConfig().getString("BuyableItem1.name", "Item 1"), Material.RED_STAINED_GLASS_PANE, "Zusätzliches kaufbares Item 1");
        ItemStack item2 = createMenuItem(main.getConfig().getString("BuyableItem2.name", "Item 2"), Material.RED_STAINED_GLASS_PANE, "Zusätzliches kaufbares Item 2");
        ItemStack item3 = createMenuItem(main.getConfig().getString("BuyableItem3.name", "Item 3"), Material.RED_STAINED_GLASS_PANE, "Zusätzliches kaufbares Item 3");
        ItemStack back = createBackItem();

        // Indexe aus der Config oder Standardwerte nutzen
        int flyIndex = main.getConfig().getInt("FlyItem.position", 20);
        int sleepIndex = main.getConfig().getInt("SleepItem.position", 21);
        int item1Index = main.getConfig().getInt("BuyableItem1.position", 22);
        int item2Index = main.getConfig().getInt("BuyableItem2.position", 23);
        int item3Index = main.getConfig().getInt("BuyableItem3.position", 24);
        int backIndex = main.getConfig().getInt("backItemBuyableMenu.position", 36);

        // Items ins Menü setzen
        buyableMenu.setItem(flyIndex, fly);
        buyableMenu.setItem(sleepIndex, sleep);
        buyableMenu.setItem(item1Index, item1);
        buyableMenu.setItem(item2Index, item2);
        buyableMenu.setItem(item3Index, item3);
        buyableMenu.setItem(backIndex, back);

        // Hintergrund setzen
        ItemStack background = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        for (int i = 0; i < 45; i++) {
            if (!(i == flyIndex || i == sleepIndex || i == item1Index || i == item2Index || i == item3Index || i == backIndex)) {
                buyableMenu.setItem(i, background);
            }
        }
        return buyableMenu;
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
