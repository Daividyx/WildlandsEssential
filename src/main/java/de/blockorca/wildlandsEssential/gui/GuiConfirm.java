package de.blockorca.wildlandsEssential.gui;

import de.blockorca.wildlandsEssential.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GuiConfirm extends AbstractMenu {

    // Hier speichern wir den Daten-String (z.B. "Homes;Home 1")
    private final String dataString;

    // Neuer Konstruktor: Du übergibst "dataString" statt clickedItem/previousInventory
    public GuiConfirm(Main main, Player player, String dataString) {
        super(main, player, "Kauf Bestätigen", 45);
        this.dataString = dataString;
    }

    @Override
    protected void placeItems(Inventory inv) {

        // confirm Item (LIME_WOOL)
        int confirmIndex = main.getConfig().getInt("confirmItem.position");
        String confirmName = main.getConfig().getString("confirmItem.name");
        // 1) Erzeuge das Item
        // 2) Schreibe dataString in die Lore
        ItemStack confirmItem = createMenuItem(
                ChatColor.GOLD + confirmName,
                Material.LIME_WOOL,
                ChatColor.GREEN + "Bestellung abschließen",
                // HIER packen wir den dataString rein
                ChatColor.DARK_GRAY + dataString
        );
        inv.setItem(confirmIndex, confirmItem);

        // cancel Item (RED_WOOL)
        int cancelIndex = main.getConfig().getInt("cancelItem.position");
        String cancelName = main.getConfig().getString("cancelItem.name");
        ItemStack cancelItem = createMenuItem(
                ChatColor.GOLD + cancelName,
                Material.RED_WOOL,
                ChatColor.GREEN + "Bestellung abbrechen"
        );
        inv.setItem(cancelIndex, cancelItem);
    }
}
