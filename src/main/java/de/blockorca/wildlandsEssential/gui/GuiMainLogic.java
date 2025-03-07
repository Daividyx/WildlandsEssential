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

public class GuiMainLogic implements GuiMenu {

    Main main;
    Player player;

    public GuiMainLogic(Main main, Player player) {
        this.main = main;
        this.player = player;
    }

    @Override
    public void openMenu() {
        player.openInventory(createMenu());
    }

    @Override
    public Inventory createMenu() {
        Inventory mainMenu = Bukkit.createInventory(null, 45, "Main Menu");

        // Menü-Items erstellen
        ItemStack economy = createMenuItem("Economy", Material.GOLD_INGOT, "Hier kommst du zur Bank");
        ItemStack warps = createMenuItem("Warps", Material.ENDER_PEARL, "Hier kommst du zu den Warps");
        ItemStack deadChest = createMenuItem("Dead Chest", Material.CHEST, "Hier kommst du zur DeadChest");
        ItemStack buyableFunctions = createMenuItem("Buyable Functions", Material.NETHER_STAR, "Hier kommst du zu den BuyableFunctions");

        // Indexe aus der Config oder Standardwerte
        int economyindex = main.getConfig().getInt("EconomyItem.position", 20);
        int warpindex = main.getConfig().getInt("WarpItem.position", 21);
        int deadchestindex = main.getConfig().getInt("DeadChestItem.position", 22);
        int buyablefunctionsindex = main.getConfig().getInt("BuyableFunctionItem.position", 23);

        // Items ins Menü setzen
        mainMenu.setItem(economyindex, economy);
        mainMenu.setItem(warpindex, warps);
        mainMenu.setItem(deadchestindex, deadChest);
        mainMenu.setItem(buyablefunctionsindex, buyableFunctions);

        // Hintergrund setzen
        ItemStack background = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        for (int i = 0; i < 45; i++) {
            if (!(i == economyindex || i == warpindex || i == deadchestindex || i == buyablefunctionsindex)) {
                mainMenu.setItem(i, background);
            }
        }
        return mainMenu;
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
