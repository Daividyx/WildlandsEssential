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

public class GuiConfirmPurchase implements GuiMenu {

    Main main;
    Player player;

    public GuiConfirmPurchase(Main main, Player player) {
        this.main = main;
        this.player = player;
    }

    @Override
    public void openMenu() {
        player.openInventory(createMenu());
    }

    @Override
    public Inventory createMenu() {
        Inventory confirmMenu = Bukkit.createInventory(null, 45, "Confirm Purchase");

        // Menü-Items erstellen
        ItemStack confirm = createMenuItem("Kaufen", Material.LIME_WOOL, "Bestätige den Kauf");
        ItemStack cancel = createMenuItem("Abbrechen", Material.RED_WOOL, "Kauf abbrechen und zurück");

        // Indexe aus der Config oder Standardwerte nutzen
        int confirmIndex = main.getConfig().getInt("ConfirmItem.position");
        int cancelIndex = main.getConfig().getInt("CancelItem.position");

        // Items ins Menü setzen
        confirmMenu.setItem(confirmIndex, confirm);
        confirmMenu.setItem(cancelIndex, cancel);

        // Hintergrund setzen
        ItemStack background = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        for (int i = 0; i < 45; i++) {
            if (!(i == confirmIndex || i == cancelIndex)) {
                confirmMenu.setItem(i, background);
            }
        }
        return confirmMenu;
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
}
