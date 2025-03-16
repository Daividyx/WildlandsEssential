package de.blockorca.wildlandsEssential.gui;

import de.blockorca.wildlandsEssential.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GuiConfirm extends AbstractMenu{

    ItemStack clickedItem;
    String previousInventoryTitle;
    public GuiConfirm(Main main, Player player,ItemStack clickedItem,String previousInventoryTitle) {
        super(main, player,"Kauf Bestätigen", 45);
        this.clickedItem = clickedItem;
        this.previousInventoryTitle = previousInventoryTitle;
    }

    @Override
    protected void placeItems(Inventory inv) {

        //confirm Item
        int confirmIndex = main.getConfig().getInt("confirmItem.position");
        String confirmName = main.getConfig().getString("confirmItem.name");
        ItemStack confirmItem = createMenuItem(ChatColor.GOLD + confirmName, Material.LIME_WOOL,ChatColor.GREEN+"Bestellung abschließen");
        inv.setItem(confirmIndex, confirmItem);

        //cancel Item
        int cancelIndex = main.getConfig().getInt("cancelItem.position");
        String cancelName = main.getConfig().getString("cancelItem.name");
        ItemStack cancelItem = createMenuItem(ChatColor.GOLD + cancelName, Material.RED_WOOL,ChatColor.GREEN+"Bestellung abbrechen");
        inv.setItem(cancelIndex, cancelItem);

    }

    public ItemStack getClickedItem() {
        return clickedItem;
    }

    public String getPreviousInventoryTitle() {
        return previousInventoryTitle;
    }
}
