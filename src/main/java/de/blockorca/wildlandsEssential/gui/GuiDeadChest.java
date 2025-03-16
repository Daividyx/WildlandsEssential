package de.blockorca.wildlandsEssential.gui;

import de.blockorca.wildlandsEssential.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GuiDeadChest extends AbstractMenu {

    public GuiDeadChest(Main main, Player player) {
        super(main, player, "DeadChest Menü", 45);
    }

    @Override
    protected void placeItems(Inventory inv) {
        // DeadChest-Koordinaten aus der Config laden
        String world = main.getConfig().getString("deadChest.world", "world");
        int x = main.getConfig().getInt("deadChest.x", 0);
        int y = main.getConfig().getInt("deadChest.y", 0);
        int z = main.getConfig().getInt("deadChest.z", 0);
        String coords = ChatColor.AQUA + "Koordinaten: " + ChatColor.RED + world + " (" + x + ", " + y + ", " + z + ")";

        // Kaufen-Item
        int buyIndex = main.getConfig().getInt("buyItem.position");
        String buyName = main.getConfig().getString("buyItem.name");
        int buyPrice = main.getConfig().getInt("buyItem.preis");
        ItemStack buyItem = createMenuItem(
                ChatColor.GOLD + buyName,
                Material.EMERALD,
                ChatColor.GREEN + "Preis: $" + buyPrice,
                coords,
                ChatColor.YELLOW + "Kaufe deine Items zurück!"
        );
        inv.setItem(buyIndex, buyItem);

        // Teleport-Item
        int teleportIndex = main.getConfig().getInt("teleportItem.position");
        String teleportName = main.getConfig().getString("teleportItem.name", "Teleport");
        int teleportPrice = main.getConfig().getInt("teleportItem.preis");
        ItemStack teleportItem = createMenuItem(
                ChatColor.GOLD + teleportName,
                Material.ENDER_PEARL,
                ChatColor.GREEN + "Preis: $" + teleportPrice,
                coords,
                ChatColor.YELLOW + "Teleportiere dich zu deiner DeadChest!"
        );
        inv.setItem(teleportIndex, teleportItem);

        // Zurück-Button
        int backIndex = main.getConfig().getInt("backItemDeadChestMenu.position");
        String backName = main.getConfig().getString("backItemDeadChestMenu.name", "Zurück");
        ItemStack backItem = createMenuItem(
                ChatColor.RED + backName,
                Material.BARRIER,
                ChatColor.YELLOW + "Zurück zum Hauptmenü"
        );
        inv.setItem(backIndex, backItem);
    }
}
