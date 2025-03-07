package de.blockorca.wildlandsEssential.listener;

import de.blockorca.wildlandsEssential.Main;
import de.blockorca.wildlandsEssential.gui.GuiEconomyLogic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ClickListener implements Listener {

    Main main;
    Player player;

    public ClickListener(Main main, Player player) {
        this.main = main;
        this.player = player;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){



        if(event.getClickedInventory() == null) return;
        if(event.getCurrentItem() == null) return;
        if(event.getCurrentItem().getItemMeta().getItemName().equalsIgnoreCase("Economy")){


            GuiEconomyLogic guiEconomy = new GuiEconomyLogic( main, player);
            guiEconomy.openMenu();



        }



    }

}
