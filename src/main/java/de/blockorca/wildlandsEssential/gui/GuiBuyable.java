package de.blockorca.wildlandsEssential.gui;

import de.blockorca.wildlandsEssential.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GuiBuyable extends AbstractMenu {

    public GuiBuyable(Main main, Player player) {
        super(main, player, "Kaufbare Funktionen", 45);
    }

    @Override
    protected void placeItems(Inventory inv) {
        // Fliegen kaufen
        int flyIndex = main.getConfig().getInt("buyableItem1.position");
        String flyName = main.getConfig().getString("buyableItem1.name");
        int flyPrice = main.getConfig().getInt("buyableItem1.preis");
        ItemStack flyItem = createMenuItem(ChatColor.GOLD + flyName, Material.FEATHER,
                ChatColor.GREEN + "Preis: " + ChatColor.RED + flyPrice,
                ChatColor.AQUA + "Kaufe dir die Fähigkeit zu fliegen für 1 Stunde.");
        inv.setItem(flyIndex, flyItem);

        // Alleine schlafen
        int sleepIndex = main.getConfig().getInt("buyableItem2.position");
        String sleepName = main.getConfig().getString("buyableItem2.name");
        int sleepPrice = main.getConfig().getInt("buyableItem2.preis");
        ItemStack sleepItem = createMenuItem(ChatColor.GOLD + sleepName, Material.RED_BED,
                ChatColor.GREEN + "Preis: " + ChatColor.RED + sleepPrice,
                ChatColor.AQUA + "Ermöglicht es dir, alleine die Nacht zu überspringen.");
        inv.setItem(sleepIndex, sleepItem);

        // Kaufbares Item 1
        int item1Index = main.getConfig().getInt("buyableItem3.position");
        String item1Name = main.getConfig().getString("buyableItem3.name");
        int item1Price = main.getConfig().getInt("buyableItem3.preis");
        ItemStack item1 = createMenuItem(ChatColor.GOLD + item1Name, Material.LANTERN,
                ChatColor.GREEN + "Preis: " + ChatColor.RED + item1Price,
                ChatColor.AQUA + "Ein zusätzlich kaufbares Item.");
        inv.setItem(item1Index, item1);

        // Kaufbares Item 2
        int item2Index = main.getConfig().getInt("buyableItem4.position");
        String item2Name = main.getConfig().getString("buyableItem4.name");
        int item2Price = main.getConfig().getInt("buyableItem4.preis");
        ItemStack item2 = createMenuItem(ChatColor.GOLD + item2Name, Material.SOUL_LANTERN,
                ChatColor.GREEN + "Preis: " + ChatColor.RED + item2Price,
                ChatColor.AQUA + "Ein weiteres kaufbares Item.");
        inv.setItem(item2Index, item2);

        // Kaufbares Item 3
        int item3Index = main.getConfig().getInt("buyableItem5.position");
        String item3Name = main.getConfig().getString("buyableItem5.name");
        int item3Price = main.getConfig().getInt("buyableItem5.preis");
        ItemStack item3 = createMenuItem(ChatColor.GOLD + item3Name, Material.SOUL_CAMPFIRE,
                ChatColor.GREEN + "Preis: " + ChatColor.RED + item3Price,
                ChatColor.AQUA + "Ein seltenes kaufbares Item.");
        inv.setItem(item3Index, item3);

        // Zurück-Button
        int backIndex = main.getConfig().getInt("backItemBuyableMenu.position");
        String backItemName = main.getConfig().getString("backItemBuyableMenu.name");
        ItemStack backItem = createMenuItem(ChatColor.GOLD + backItemName, Material.BARRIER,
                ChatColor.GREEN + "Zurück zum Hauptmenü");
        inv.setItem(backIndex, backItem);
    }
}
