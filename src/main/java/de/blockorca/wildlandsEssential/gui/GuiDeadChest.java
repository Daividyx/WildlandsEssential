package de.blockorca.wildlandsEssential.gui;

import de.blockorca.wildlandsEssential.Main;
import de.blockorca.wildlandsEssential.data.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GuiDeadChest extends AbstractMenu {

    private ConfigManager configManager;

    public GuiDeadChest(Main main, Player player) {
        super(main, player, "DeadChest Menü", 45);
        this.configManager = main.getConfigManager();
    }

    @Override
    protected void placeItems(Inventory inv) {
        // DeadChest-Koordinaten aus der Config laden
        String world = main.getConfig().getString("deadChest.world", "world");
        int x = configManager.getDeadChestX(player);
        int y = configManager.getDeadChestY(player);
        int z = configManager.getDeadChestZ(player);
        String coords = ChatColor.AQUA + "Koordinaten: " + ChatColor.RED + " (" + x + ", " + y + ", " + z + ")";

        // Kaufen-Item
        int buyIndex = main.getConfig().getInt("buyItem.position");
        String buyName = main.getConfig().getString("buyItem.name");
        int buyPrice = (int) configManager.getDeadChestOpenPrice();
        ItemStack buyItem = createMenuItem(
                ChatColor.GOLD + buyName,
                Material.EMERALD,
                ChatColor.GREEN + "Preis: $" + buyPrice,
                ChatColor.AQUA + "Hast du eine DeadChest? [True / False]" + ChatColor.RED + configManager.isDeadChest(player),
                coords,
                ChatColor.YELLOW + "Kaufe deine Items zurück!",
                ChatColor.YELLOW + "Zum zurückkaufen muss sich die Kiste innerhalb von 5 Blöcken um dich herum befinden!"
        );
        inv.setItem(buyIndex, buyItem);

        // Teleport-Item
        int teleportIndex = main.getConfig().getInt("teleportItem.position");
        String teleportName = main.getConfig().getString("teleportItem.name", "Teleport");
        int teleportPrice = (int) configManager.getDeadChestTeleportPrice();
        ItemStack teleportItem = createMenuItem(
                ChatColor.GOLD + teleportName,
                Material.ENDER_PEARL,
                ChatColor.GREEN + "Preis: $" + teleportPrice,
                coords,
                ChatColor.YELLOW + "Teleportiere dich zu deiner DeadChest!",
                ChatColor.YELLOW + "Du musst sie aber trotzdem noch kaufen auch wenn du dich hinteleportierst"
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
