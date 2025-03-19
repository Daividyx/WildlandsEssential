package de.blockorca.wildlandsEssential.gui;

import de.blockorca.wildlandsEssential.Main;
import de.blockorca.wildlandsEssential.data.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GuiWarp extends AbstractMenu {

    private ConfigManager configManager;

    public GuiWarp(Main main, Player player) {
        super(main, player, "Warp Menü", 45);
        this.configManager = main.getConfigManager();
    }

    @Override
    protected void placeItems(Inventory inv) {
        // Warp 1
        int warp1Index = main.getConfig().getInt("warp1.position");
        String warp1Name = main.getConfig().getString("warp1.name");
        int price1 = (int) configManager.getWarp1Price();
        ItemStack warp1Item = createMenuItem(ChatColor.GOLD + warp1Name, Material.ENDER_PEARL,
                ChatColor.GREEN + "Kosten: " + ChatColor.RED + price1,
                ChatColor.AQUA + "Nutze " + ChatColor.RED + " /warp " + warp1Name + ChatColor.AQUA + " zum Teleportieren");
        inv.setItem(warp1Index, warp1Item);

        // Warp 2
        int warp2Index = main.getConfig().getInt("warp2.position");
        String warp2Name = main.getConfig().getString("warp2.name");
        int price2 = (int) configManager.getWarp2Price();
        ItemStack warp2Item = createMenuItem(ChatColor.GOLD + warp2Name, Material.ENDER_PEARL,
                ChatColor.GREEN + "Kosten: " + ChatColor.RED + price2,
                ChatColor.AQUA + "Nutze " + ChatColor.RED + " /warp " + warp2Name + ChatColor.AQUA + " zum Teleportieren");
        inv.setItem(warp2Index, warp2Item);

        // Warp 3
        int warp3Index = main.getConfig().getInt("warp3.position");
        String warp3Name = main.getConfig().getString("warp3.name");
        int price3 = (int) configManager.getWarp3Price();
        ItemStack warp3Item = createMenuItem(ChatColor.GOLD + warp3Name, Material.ENDER_PEARL,
                ChatColor.GREEN + "Kosten: " + ChatColor.RED + price3,
                ChatColor.AQUA + "Nutze " + ChatColor.RED + " /warp " + warp3Name + ChatColor.AQUA + " zum Teleportieren");
        inv.setItem(warp3Index, warp3Item);

        // Warp 4
        int warp4Index = main.getConfig().getInt("warp4.position");
        String warp4Name = main.getConfig().getString("warp4.name");
        int price4 = (int) configManager.getWarp4Price();
        ItemStack warp4Item = createMenuItem(ChatColor.GOLD + warp4Name, Material.ENDER_PEARL,
                ChatColor.GREEN + "Kosten: " + ChatColor.RED + price4,
                ChatColor.AQUA + "Nutze " + ChatColor.RED + " /warp " + warp4Name + ChatColor.AQUA + " zum Teleportieren");
        inv.setItem(warp4Index, warp4Item);

        // Warp 5
        int warp5Index = main.getConfig().getInt("warp5.position");
        String warp5Name = main.getConfig().getString("warp5.name");
        int price5 = (int) configManager.getWarp5Price();
        ItemStack warp5Item = createMenuItem(ChatColor.GOLD + warp5Name, Material.ENDER_PEARL,
                ChatColor.GREEN + "Kosten: " + ChatColor.RED + price5,
                ChatColor.AQUA + "Nutze " + ChatColor.RED + " /warp " + warp5Name + ChatColor.AQUA + " zum Teleportieren");
        inv.setItem(warp5Index, warp5Item);

        // Zurück-Button
        int backIndex = main.getConfig().getInt("backItemWarpMenu.position");
        String backItemName = main.getConfig().getString("backItemWarpMenu.name");
        ItemStack backItem = createMenuItem(ChatColor.GOLD + backItemName, Material.BARRIER,
                ChatColor.GREEN + "Zurück zum Hauptmenü");
        inv.setItem(backIndex, backItem);
    }
}
