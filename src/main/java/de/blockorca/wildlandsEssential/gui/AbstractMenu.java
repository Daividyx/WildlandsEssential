package de.blockorca.wildlandsEssential.gui;

import de.blockorca.wildlandsEssential.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMenu implements GuiMenu {

    protected final Main main;
    protected final Player player;
    private final String title;
    private final int size;

    public AbstractMenu(Main main, Player player, String title, int size) {
        this.main = main;
        this.player = player;
        this.title = title;
        this.size = size;

    }


    @Override
    public void openMenu() {


        player.openInventory(createMenu());

    }

    @Override
    public Inventory createMenu() {


        // create a new Inventory
        Inventory inv = Bukkit.createInventory(null, size, title);
        //create Background Item
        ItemStack background = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        //set the beackground Item
        for(int i=0;i<size;i++){
            inv.setItem(i, background);
        }
        placeItems(inv);

        return inv;
    }

    protected abstract void placeItems(Inventory inv);
    // Method to create a Menu item with given name,material and multiple lore lines
    protected ItemStack createMenuItem(String name,Material material,String...loreLines){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        List<String> lore = new ArrayList<>();
        for(String line : loreLines){
            lore.add(line);
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }


}
