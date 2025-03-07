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

public class GuiWarpLogic implements GuiMenu {

    Main main;
    Player player;

    public GuiWarpLogic(Main main, Player player) {
        this.main = main;
        this.player = player;
    }

    @Override
    public void openMenu() {
        player.openInventory(createMenu());
    }

    @Override
    public Inventory createMenu() {
        Inventory warpMenu = Bukkit.createInventory(null, 45, "Warp Menu");

        // Menü-Items erstellen
        ItemStack warp1 = createMenuItem("Spawn", Material.ENDER_PEARL, "Preis: 100 Coins");
        ItemStack warp2 = createMenuItem("Farmwelt", Material.ENDER_PEARL, "Preis: 200 Coins");
        ItemStack warp3 = createMenuItem("Nether", Material.ENDER_PEARL, "Preis: 300 Coins");
        ItemStack warp4 = createMenuItem("End", Material.ENDER_PEARL, "Preis: 400 Coins");
        ItemStack warp5 = createMenuItem("PvP Arena", Material.ENDER_PEARL, "Preis: 500 Coins");
        ItemStack back = createBackItem();

        // Indexe aus der Config oder Standardwerte nutzen
        int warp1index = main.getConfig().getInt("Warp1Item.position", 20);
        int warp2index = main.getConfig().getInt("Warp2Item.position", 21);
        int warp3index = main.getConfig().getInt("Warp3Item.position", 22);
        int warp4index = main.getConfig().getInt("Warp4Item.position", 23);
        int warp5index = main.getConfig().getInt("Warp5Item.position", 24);
        int backindex = main.getConfig().getInt("backItemWarpMenu.position", 36);

        // Items ins Menü setzen
        warpMenu.setItem(warp1index, warp1);
        warpMenu.setItem(warp2index, warp2);
        warpMenu.setItem(warp3index, warp3);
        warpMenu.setItem(warp4index, warp4);
        warpMenu.setItem(warp5index, warp5);
        warpMenu.setItem(backindex, back);

        // Hintergrund setzen
        ItemStack background = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        for (int i = 0; i < 45; i++) {
            if (!(i == warp1index || i == warp2index || i == warp3index || i == warp4index || i == warp5index || i == backindex)) {
                warpMenu.setItem(i, background);
            }
        }
        return warpMenu;
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
