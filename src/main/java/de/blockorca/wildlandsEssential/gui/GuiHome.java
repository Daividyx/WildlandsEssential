package de.blockorca.wildlandsEssential.gui;

import de.blockorca.wildlandsEssential.Main;
import de.blockorca.wildlandsEssential.data.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GuiHome extends AbstractMenu{

    private ConfigManager configManager;


    public  GuiHome (Main main, Player player){

        super(main,player,"Homes",45);
        this.configManager = main.getConfigManager();
    }


    @Override
    protected void placeItems(Inventory inv) {

        //home 1
        int home1Index = main.getConfig().getInt("home1.position");
        String home1Name = main.getConfig().getString("home1.name");
        int price1 = (int) configManager.getHome1Price();
        ItemStack home1Item = createMenuItem(ChatColor.GOLD + home1Name,Material.COMPASS,ChatColor.GREEN + "Der erste Home Punkt kostet: " + ChatColor.RED + price1,
                ChatColor.AQUA + "Setze eine Home Punkt mit " + ChatColor.RED + " /sethome <name>",
                ChatColor.AQUA + "Lösche eine Home Punkt mit " + ChatColor.RED + " /delhome <name>",
                ChatColor.AQUA + "Schaue welche Homes du hast mit " + ChatColor.RED + " /homes");
        inv.setItem(home1Index, home1Item);
        //home 2
        int home2Index = main.getConfig().getInt("home2.position");
        String home2Name = main.getConfig().getString("home2.name");
        int price2 = (int) configManager.getHome2Price();
        ItemStack home2Item = createMenuItem(ChatColor.GOLD + home2Name,Material.COMPASS,ChatColor.GREEN + "Der zweite Home Punkt kostet: " + ChatColor.RED + price2,
                ChatColor.AQUA + "Setze eine Home Punkt mit " + ChatColor.RED + " /sethome <name>",
                ChatColor.AQUA + "Lösche eine Home Punkt mit " + ChatColor.RED + " /delhome <name>",
                ChatColor.AQUA + "Schaue welche Homes du hast mit " + ChatColor.RED + " /homes");
        inv.setItem(home2Index, home2Item);
        //home 3
        int home3Index = main.getConfig().getInt("home3.position");
        String home3Name = main.getConfig().getString("home3.name");
        int price3 = (int) configManager.getHome3Price();
        ItemStack home3Item = createMenuItem(ChatColor.GOLD + home3Name,Material.COMPASS,ChatColor.GREEN + "Der dritte Home Punkt kostet: " + ChatColor.RED + price3,
                ChatColor.AQUA + "Setze eine Home Punkt mit " + ChatColor.RED + " /sethome <name>",
                ChatColor.AQUA + "Lösche eine Home Punkt mit " + ChatColor.RED + " /delhome <name>",
                ChatColor.AQUA + "Schaue welche Homes du hast mit " + ChatColor.RED + " /homes");
        inv.setItem(home3Index, home3Item);
        //home 4
        int home4Index = main.getConfig().getInt("home4.position");
        String home4Name = main.getConfig().getString("home4.name");
        int price4 = (int) configManager.getHome4Price();
        ItemStack home4Item = createMenuItem(ChatColor.GOLD + home4Name,Material.COMPASS,ChatColor.GREEN + "Der vierte Home Punkt kostet: " + ChatColor.RED + price4,
                ChatColor.AQUA + "Setze eine Home Punkt mit " + ChatColor.RED + " /sethome <name>",
                ChatColor.AQUA + "Lösche eine Home Punkt mit " + ChatColor.RED + " /delhome <name>",
                ChatColor.AQUA + "Schaue welche Homes du hast mit " + ChatColor.RED + " /homes");
        inv.setItem(home4Index, home4Item);
        //home 5
        int home5Index = main.getConfig().getInt("home5.position");
        String home5Name = main.getConfig().getString("home5.name");
        int price5 = (int) configManager.getHome5Price();
        ItemStack home5Item = createMenuItem(ChatColor.GOLD + home5Name,Material.COMPASS,ChatColor.GREEN + "Der fünfte Home Punkt kostet: " + ChatColor.RED + price5,
                ChatColor.AQUA + "Setze eine Home Punkt mit " + ChatColor.RED + " /sethome <name>",
                ChatColor.AQUA + "Lösche eine Home Punkt mit " + ChatColor.RED + " /delhome <name>",
                ChatColor.AQUA + "Schaue welche Homes du hast mit " + ChatColor.RED + " /homes");
        inv.setItem(home5Index, home5Item);

        int backIndex = main.getConfig().getInt("backItemEconomyMenu.position");
        String backItemName = main.getConfig().getString("backItemEconomyMenu.name");
        ItemStack backItem = createMenuItem(ChatColor.GOLD + backItemName,Material.BARRIER,ChatColor.GREEN + "Zurück ins Hauptmenü");
        inv.setItem(backIndex, backItem);
    }


}
