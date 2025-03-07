package de.blockorca.wildlandsEssential.gui;

import de.blockorca.wildlandsEssential.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GuiMainLogic {

    Main main;
    CommandSender commandSender;
    Command command;
    String s;
    String[] strings;

    public GuiMainLogic(Main main, CommandSender commandSender, Command command, String s, String[] strings) {

            this.main = main;
            this.commandSender = commandSender;
            this.command = command;
            this.s = s;
            this.strings = strings;





    }

    public boolean isPlayer(CommandSender sender) {

        if(!(sender instanceof Player)){

            sender.sendMessage(ChatColor.RED + "Only players can use this command!");
            return false;
        }
        Player player = (Player) sender;
        return true;
    }

    public void openMenu(Player player) {


            player.openInventory(createMainMenu());
    }
    public Inventory createMainMenu() {


        // Erzeugen des Economy Items mit Namen und lore
        ItemStack economy = new ItemStack(Material.GOLD_INGOT);
        ItemMeta economyMeta = economy.getItemMeta();
        List<String> economyLore = new ArrayList<>();
        economyLore.add(ChatColor.GREEN + "Hier kommst du zur bank ");
        economyMeta.setDisplayName(ChatColor.GOLD + "Economy");
        economyMeta.setLore(economyLore);
        economy.setItemMeta(economyMeta);


        // Erzeugen des Warps Items mit Namen und lore
        ItemStack warps = new ItemStack(Material.ENDER_PEARL);
        ItemMeta warpsMeta = warps.getItemMeta();
        List<String> warpsLore = new ArrayList<>();
        warpsLore.add(ChatColor.GREEN + "Hier kommst du zu den Warps ");
        warpsMeta.setDisplayName(ChatColor.GOLD + "Warps");
        warpsMeta.setLore(warpsLore);
        warps.setItemMeta(warpsMeta);

        // Erzeugen des DeadChest Items mit Namen und lore
        ItemStack deadChest = new ItemStack(Material.CHEST);
        ItemMeta deadChestMeta = deadChest.getItemMeta();
        List<String> deadChestLore = new ArrayList<>();
        deadChestLore.add(ChatColor.GREEN + "Hier kommst du zur DeadChest ");
        deadChestMeta.setDisplayName(ChatColor.GOLD + "Dead Chest");
        deadChestMeta.setLore(deadChestLore);
        deadChest.setItemMeta(deadChestMeta);

        // Erzeugen des buyable Functions Items mit Namen und lore
        ItemStack buyableFunctions = new ItemStack(Material.NETHER_STAR);
        ItemMeta buyableFunctionsMeta = buyableFunctions.getItemMeta();
        List<String> buyableFunctionsLore = new ArrayList<>();
        buyableFunctionsLore.add(ChatColor.GREEN + "Hier kommst du zu den BuyableFunctions");
        buyableFunctionsMeta.setDisplayName(ChatColor.GOLD + "Buyable Functions");
        buyableFunctionsMeta.setLore(buyableFunctionsLore);
        buyableFunctions.setItemMeta(buyableFunctionsMeta);


        ItemStack background = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);


        //Indexe von den Menü Items holen
        int economyindex = main.getConfig().getInt("EconomyItem");
        int warpindex = main.getConfig().getInt("WarpsItem");
        int deadchestindex = main.getConfig().getInt("DeadChestItem");
        int buyablefunctionsindex = main.getConfig().getInt("BuyableFunctionsItem");


        // Erzeugen des Inventars und Items darin platzieren


        Inventory mainMenu = Bukkit.createInventory(null, 45, "Main Menu");
        mainMenu.setItem(economyindex, economy);
        mainMenu.setItem(warpindex, warps);
        mainMenu.setItem(deadchestindex, deadChest);
        mainMenu.setItem(buyablefunctionsindex, buyableFunctions);

        for (int i = 0;i<45;i++){

            if (!(i ==20 || i==21 || i==22 || i==23)){
                mainMenu.setItem(i, background);

            }
        }

        return mainMenu;

    }




}
