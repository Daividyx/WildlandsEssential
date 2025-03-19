package de.blockorca.wildlandsEssential.gui;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import de.blockorca.wildlandsEssential.Main;
import de.blockorca.wildlandsEssential.economy.EconomyManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GuiEconomy extends AbstractMenu{



    public GuiEconomy (Main main, Player player){

        super(main,player,"Bank",45);

    }
    Essentials essentials = main.getEssentials();

    protected void placeItems(Inventory inv) {

        Essentials essentials = main.getEssentials();
       User user= essentials.getUser(player);
        //Balance Item
        int balanceIndex = main.getConfig().getInt("balanceItem.position");
        String balanceItemName = main.getConfig().getString("balanceItem.name");
        ItemStack balanceItem = createMenuItem(ChatColor.GOLD + balanceItemName,Material.GOLD_INGOT,ChatColor.GREEN + "Dein aktueller Kontostand: " + ChatColor.RED +"$"+ user.getMoney().toString());
        inv.setItem(balanceIndex, balanceItem);
        //Info Item
        int infoIndex = main.getConfig().getInt("economyInfoItem.position");
        String infoItemName = main.getConfig().getString("economyInfoItem.name");
        ItemStack infoItem = createMenuItem(ChatColor.GOLD + infoItemName,Material.BOOK,ChatColor.GREEN+"Hier gibts die Infos zum Zahlungssystem",
                ChatColor.AQUA+"Nutze " + ChatColor.RED + " /pay <spielername>" + ChatColor.AQUA + " um jemandem etwas zu überweisen");
        inv.setItem(infoIndex, infoItem);

        int backIndex = main.getConfig().getInt("backItemHomeMenu.position");
        String backItemName = main.getConfig().getString("backItemHomeMenu.name");
        ItemStack backItem = createMenuItem(ChatColor.GOLD + backItemName,Material.BARRIER,ChatColor.GREEN + "Zurück ins Hauptmenü");
        inv.setItem(backIndex, backItem);

    }
}
