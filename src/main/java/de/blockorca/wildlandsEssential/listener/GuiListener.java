package de.blockorca.wildlandsEssential.listener;

import de.blockorca.wildlandsEssential.Main;
import de.blockorca.wildlandsEssential.gui.GuiEconomyLogic;
import de.blockorca.wildlandsEssential.gui.GuiMainLogic;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class GuiListener implements Listener {

    private Main main;
    public GuiListener(Main main) {
        this.main = main;
    }



    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {


        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;


        // Prüft, ob der Spieler in dem Hauptmenü GUI-Menü ist
        if (event.getView().getTitle().equalsIgnoreCase("Main Menu")) {
            // Verhindert das Herausnehmen von Items
            event.setCancelled(true);
            // Wenn Gold, dann Economy Menü
            if (event.getCurrentItem().getType() == Material.GOLD_INGOT) {
                new GuiEconomyLogic(main, player).openMenu();
            } else if (event.getCurrentItem().getType() == Material.ENDER_PEARL) {
               // new GuiWarpLogic(main, player).openMenu();
            } else if (event.getCurrentItem().getType() == Material.CHEST) {
               // new GuiDeadChestLogic(main, player).openMenu();
            } else if (event.getCurrentItem().getType() == Material.NETHER_STAR) {
               // new GuiBuyableLogic(main, player).openMenu();
            } else if (event.getCurrentItem().getType() == Material.BARRIER) {
                new GuiMainLogic(main, player).openMenu(); // Zurück-Button öffnet Hauptmenü
            }





            if (event.getView().getTitle().equalsIgnoreCase("Economy Menu")) {
                // Verhindert das Herausnehmen von Items
                event.setCancelled(true);

            }
        }


    }
}
