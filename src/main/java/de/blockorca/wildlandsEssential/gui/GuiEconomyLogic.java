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

    public GuiEconomyLogic(Main main,Player player) {
        this.main=main;
        this.player=player;
    }
    @Override
    public void openMenu() {

        player.openInventory(createMenu());
    }

    @Override
    public Inventory createMenu() {



        // Erzeugen des Balance Items mit Namen und lore
        ItemStack balance = new ItemStack(Material.GOLD_INGOT);
        ItemMeta balanceMeta = balance.getItemMeta();
        List<String> balanceLore = new ArrayList<>();
        balanceLore.add(ChatColor.GREEN + "Dein Kontostand: ");
        balanceMeta.setDisplayName(ChatColor.GOLD + "Kontostand");
        balanceMeta.setLore(balanceLore);
        balance.setItemMeta(balanceMeta);


        // Erzeugen des Info Items mit Namen und lore
        ItemStack info = new ItemStack(Material.BOOK);
        ItemMeta infoMeta = info.getItemMeta();
        List<String> infoLore = new ArrayList<>();
        infoLore.add(ChatColor.GREEN + "Hier kommst du zu den Informationen ");
        infoMeta.setDisplayName(ChatColor.GOLD + "Info");
        infoMeta.setLore(infoLore);
        info.setItemMeta(infoMeta);

        // Erzeugen des Zurück Items mit Namen und lore
        ItemStack back = new ItemStack(Material.BARRIER);
        ItemMeta backMeta = back.getItemMeta();
        List<String> backLore = new ArrayList<>();
        backLore.add(ChatColor.GREEN + "Hier kommst du zurück zum Hauptmenü ");
        backMeta.setDisplayName(ChatColor.GOLD + "Zurück");
        backMeta.setLore(backLore);
        back.setItemMeta(backMeta);




        ItemStack background = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);


        //Indexe von den Menü Items holen
        int balanceindex = main.getConfig().getInt("balanceItem.position");
        int infoindex = main.getConfig().getInt("infoItem.position");
        int backindex = main.getConfig().getInt("backItem.position");

        // Erzeugen des Inventars und Items darin platzieren

        Inventory economyMenu = Bukkit.createInventory(null, 45, "Economy Menü");
        economyMenu.setItem(balanceindex, balance);
        economyMenu.setItem(infoindex, info);
        economyMenu.setItem(backindex, back);


        for (int i = 0;i<45;i++){

            if (!(i ==20 || i==21 || i==22 || i==23 || i==24 || i==36)){
                economyMenu.setItem(i, background);

            }
        }






        return economyMenu;
    }
}
