package de.blockorca.wildlandsEssential.logic;

import com.earth2me.essentials.Essentials;
import de.blockorca.wildlandsEssential.Main;
import de.blockorca.wildlandsEssential.data.ConfigManager;
import de.blockorca.wildlandsEssential.economy.EconomyManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Die Klasse {@code DeadChestLogic} enthält die gesamte Logik rund um die
 * Erzeugung, Sicherung, Freigabe und Entfernung einer sogenannten "DeadChest".
 * <p>
 * Eine DeadChest ist hier als Doppelkiste realisiert, die bei Bedarf (z. B.
 * nach dem Tod eines Spielers) erstellt wird. Sie kann anschließend durch
 * Barrieren gesichert und später wieder freigegeben oder entfernt werden.
 * <p>
 * Die Position der DeadChest wird in einer Config gespeichert (über den
 * {@link ConfigManager}). Dabei werden jeweils nur die Koordinaten der
 * ersten Kiste (linke Kiste) gesichert; die zweite Kiste wird anhand
 * von {@code x + 1} errechnet.
 */
public class DeadChestLogic {

    /** Referenz auf die Hauptklasse des Plugins. */
    private final Main main;
    /** Verwalter für die Plugin-Config, speichert u. a. DeadChest-Koordinaten. */
    private final ConfigManager configManager;
    private final EconomyManager economyManager;
    /** Essentials-API-Referenz, falls benötigt. */
    private final Essentials essentials;
    /** Welt, in der die DeadChest erstellt wird. Wird z. B. beim ersten Bedarf gesetzt. */
    private World world;

    /**
     * Konstruktor für die DeadChest-Logik. Erzeugt die benötigten Manager-Referenzen
     * aus der übergebenen Hauptklasse {@code Main}.
     *
     * @param main Hauptplugin-Klasse, um auf ConfigManager/EconomyManager usw. zuzugreifen.
     */
    public DeadChestLogic(Main main) {
        this.main = main;
        this.configManager = main.getConfigManager();
        this.economyManager = main.getEconomyManager();
        this.essentials = main.getEssentials();
    }


    /**
     * Überprüft, ob sich der Spieler in der Nähe der gespeicherten DeadChest befindet.
     * <p>
     * Die erlaubten Abstände betragen maximal 5 Blöcke in X- und Z-Richtung sowie
     * maximal 2 Blöcke in Y-Richtung. Überschreitet der Spieler eine dieser Grenzen,
     * gibt es eine entsprechende Fehlermeldung.
     *
     * @param player Spieler, dessen Position geprüft wird.
     * @return {@code true}, wenn der Spieler nah genug ist; sonst {@code false}.
     */
    public boolean isNearDeadChest(Player player) {
        Location playerLocation = player.getLocation();
        int deadChestX = configManager.getDeadChestX(player);
        int deadChestY = configManager.getDeadChestY(player);
        int deadChestZ = configManager.getDeadChestZ(player);

        int playerLocationX = playerLocation.getBlockX();
        int playerLocationY = playerLocation.getBlockY();
        int playerLocationZ = playerLocation.getBlockZ();

        int diffX = Math.abs(deadChestX - playerLocationX);
        int diffY = Math.abs(deadChestY - playerLocationY);
        int diffZ = Math.abs(deadChestZ - playerLocationZ);

        return (diffX <= 5 && diffY <= 2 && diffZ <= 5);
    }


    /**
     * Erstellt eine neue DeadChest (Doppelkiste) an der Position des Spielers
     * und legt die übergebenen Items hinein. Ist bereits eine DeadChest vorhanden,
     * wird diese vorher entfernt.
     * <p>
     * Danach wird die neue DeadChest mittels {@link #secureDeadChest(Player, Location, Location)}
     * mit Barrieren umhüllt.
     *
     * @param player Spieler, an dessen Position (bzw. {@code Y+1}) die Kiste erzeugt wird.
     * @param items  Liste von ItemStacks, die in die neue DeadChest eingefügt werden.
     */
    public void generateDeadChest(Player player, List<ItemStack> items) {

        // Welt ermitteln, in der der Spieler sich befindet
        World world = player.getWorld();

        // Prüfen, ob bereits eine DeadChest existiert. Falls ja, entfernen wir sie.
        if (configManager.isDeadChest(player)) {
            int x = configManager.getDeadChestX(player);
            int y = configManager.getDeadChestY(player);
            int z = configManager.getDeadChestZ(player);
            Location oldLocation = new Location(world, x, y, z);

            player.sendMessage("[DEBUG / CreateDeadchest] Entferne vorhandene DeadChest bei " + oldLocation);
            removeDeadChest(player);
        }

        // Aktuelle Spielerposition
        int playerX = player.getLocation().getBlockX();
        int playerY = player.getLocation().getBlockY();
        int playerZ = player.getLocation().getBlockZ();

        /*
         * Doppelkiste erstellen:
         *   chestLoc1 = (x,   y+1, z)
         *   chestLoc2 = (x+1, y+1, z)
         */
        Location chestLoc1 = new Location(world, playerX,     playerY + 1, playerZ);
        Location chestLoc2 = new Location(world, playerX + 1, playerY + 1, playerZ);

        // Blöcke vorher auf AIR setzen
        chestLoc1.getBlock().setType(Material.AIR);
        chestLoc2.getBlock().setType(Material.AIR);

        // Beide Blöcke zu CHEST setzen
        chestLoc1.getBlock().setType(Material.CHEST);
        chestLoc2.getBlock().setType(Material.CHEST);

        // Chest-BlockData beider Kisten holen, um eine Doppelkiste explizit zu erzwingen
        Block chestBlock1 = chestLoc1.getBlock();
        Block chestBlock2 = chestLoc2.getBlock();

        org.bukkit.block.data.type.Chest chestData1 = (org.bukkit.block.data.type.Chest) chestBlock1.getBlockData();
        org.bukkit.block.data.type.Chest chestData2 = (org.bukkit.block.data.type.Chest) chestBlock2.getBlockData();

        // Ausrichtung beider Kisten, z.B. nach Norden
        chestData1.setFacing(BlockFace.NORTH);
        chestData2.setFacing(BlockFace.NORTH);

        // Erste Kiste = LEFT, zweite Kiste = RIGHT -> garantiert Doppelkiste
        chestData1.setType(org.bukkit.block.data.type.Chest.Type.LEFT);
        chestData2.setType(org.bukkit.block.data.type.Chest.Type.RIGHT);

        // BlockData anwenden
        chestBlock1.setBlockData(chestData1);
        chestBlock2.setBlockData(chestData2);

        Chest deadChest = (Chest) chestLoc1.getBlock().getState();
        Inventory chestInventory = deadChest.getInventory();

        for (ItemStack i : items) {
            chestInventory.addItem(i);
        }
        /*
        // Jetzt warten wir einen Tick, bevor wir Items einfügen und Barrieren setzen
        Bukkit.getScheduler().runTask(main, () -> {
            // 1) Items einfügen
            Chest deadChest = (Chest) chestLoc1.getBlock().getState();
            Inventory chestInventory = deadChest.getInventory();

            for (ItemStack i : items) {
                chestInventory.addItem(i);
            }

            // 2) Kiste sichern (Barrieren setzen)
            secureDeadChest(player, chestLoc1, chestLoc2);

            player.sendMessage(ChatColor.GREEN + "[DEBUG] DeadChest erstellt und gesichert!");
        });
        */

        secureDeadChest(player, chestLoc1, chestLoc2);

        //DeadChest und Coordinaten in die config Schreiben
        configManager.setIsDeadChest(player, true);
        configManager.setDeadChestCoordinates(player,chestLoc1.getBlockX(), chestLoc1.getBlockY(), chestLoc1.getBlockZ());
    }


    /**
     * Entfernt eine existierende DeadChest (falls vorhanden). Dabei wird
     * zunächst {@link #unlockDeadChest(Player)} aufgerufen, um die Barrieren
     * im entsprechenden Bereich zu entfernen. Anschließend werden beide
     * Kistenblöcke auf AIR gesetzt und die Config-Einträge zurückgesetzt.
     *
     * @param player Spieler, dessen DeadChest entfernt werden soll.
     */
    public void removeDeadChest(Player player) {
        // 1) Welt ermitteln
        World currentWorld = player.getWorld();
        int deadChestX = configManager.getDeadChestX(player);
        int deadChestY = configManager.getDeadChestY(player);
        int deadChestZ = configManager.getDeadChestZ(player);

        // 2) Beide Kistenblöcke entfernen
        Location chestLoc1 = new Location(currentWorld, deadChestX, deadChestY, deadChestZ);
        Location chestLoc2 = new Location(currentWorld, deadChestX + 1, deadChestY, deadChestZ);

        Block block1 = chestLoc1.getBlock();
        Block block2 = chestLoc2.getBlock();

        if (block1.getType() == Material.CHEST) {
            block1.setType(Material.AIR);
        }
        if (block2.getType() == Material.CHEST) {
            block2.setType(Material.AIR);
        }

        // 3) Config-Einträge zurücksetzen
        configManager.setIsDeadChest(player, false);
        configManager.setDeadChestCoordinates(player, 123456789, 123456789, 123456789);
    }


    /**
     * Sichert die Doppelkiste, indem ein fester 4×3×2-Bereich um die beiden
     * Kistenblöcke herum mit {@link Material#BARRIER} gefüllt wird. Die
     * Kistenblöcke selbst werden dabei ausgespart.
     * <p>
     * Dieser Bereich reicht in X-Richtung von {@code baseX} bis {@code baseX + 3},
     * in Y-Richtung von {@code baseY} bis {@code baseY + 2} und in Z-Richtung
     * von {@code baseZ} bis {@code baseZ + 1}.
     *
     * @param player    Spieler, für den Debug-Ausgaben erfolgen.
     * @param chestLoc1 Position der ersten Kiste.
     * @param chestLoc2 Position der zweiten Kiste.
     */
    public void secureDeadChest(Player player, Location chestLoc1, Location chestLoc2) {
        // Validierungen
        if (chestLoc1 == null || chestLoc2 == null) {
            player.sendMessage(ChatColor.RED + "Fehler: Eine der Kisten-Positionen ist null!");
            return;
        }
        World world = chestLoc1.getWorld();
        if (world == null) {
            player.sendMessage(ChatColor.RED + "Fehler: Welt ist null!");
            return;
        }

        // Koordinaten der beiden Kisten auslesen
        int c1x = chestLoc1.getBlockX();
        int c1y = chestLoc1.getBlockY();
        int c1z = chestLoc1.getBlockZ();

        int c2x = chestLoc2.getBlockX();
        int c2y = chestLoc2.getBlockY();
        int c2z = chestLoc2.getBlockZ();

        // Minimal- und Maximalwerte ermitteln
        int minX = Math.min(c1x, c2x) - 1;  // 1 Block links/kleiner
        int maxX = Math.max(c1x, c2x) + 1;  // 1 Block rechts/größer
        int minY = c1y - 1;                 // 1 Block tiefer
        int maxY = c1y + 1;                 // 1 Block höher
        int minZ = c1z - 1;                 // 1 Block "vorne"
        int maxZ = c1z + 1;                 // 1 Block "hinten"

        /*
         * Jetzt iterieren wir durch diesen Bereich (x = minX..maxX,
         * y = minY..maxY, z = minZ..maxZ) und setzen überall Barrieren,
         * außer an den beiden Kistenblöcken selbst.
         */
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    // Wenn es einer der Kistenblöcke ist: überspringen
                    if ((x == c1x && y == c1y && z == c1z) ||
                            (x == c2x && y == c2y && z == c2z)) {
                        continue;
                    }

                    Block block = world.getBlockAt(x, y, z);
                    block.setType(Material.GLASS);
                }
            }
        }

        player.sendMessage(ChatColor.GREEN + "Kiste ohne Hohlraum vollständig umhüllt!");
    }

    /**
     * Entfernt alle Barrieren im selben 4×3×2-Bereich, der in
     * {@link #secureDeadChest(Player, Location, Location)} verwendet wird.
     * Die Kistenblöcke selbst bleiben erhalten.
     * <p>
     * Wird z. B. aufgerufen, wenn der Spieler die DeadChest "kauft" und
     * somit freien Zugriff auf die Kiste haben soll.
     *
     * @param player Spieler, dessen DeadChest-Barrieren entfernt werden sollen.
     */
    public void unlockDeadChest(Player player) {
        // 1) Welt + erste Kistenposition (chestLoc1) aus der Config laden
        World world = player.getWorld();
        int c1x = configManager.getDeadChestX(player);
        int c1y = configManager.getDeadChestY(player);
        int c1z = configManager.getDeadChestZ(player);
        Location chestLoc1 = new Location(world, c1x, c1y, c1z);

        // 2) Zweite Kiste = chestLoc1 + 1 in X-Richtung
        Location chestLoc2 = chestLoc1.clone().add(1, 0, 0);

        // 3) Bounding Box um beide Kisten berechnen: 1 Block größer in alle Richtungen
        int c2x = chestLoc2.getBlockX();
        int c2y = chestLoc2.getBlockY();
        int c2z = chestLoc2.getBlockZ();

        int minX = Math.min(c1x, c2x) - 1;
        int maxX = Math.max(c1x, c2x) + 1;
        int minY = c1y - 1;
        int maxY = c1y + 1;
        int minZ = c1z - 1;
        int maxZ = c1z + 1;

        // 4) Alle Barrieren in diesem Bereich entfernen, Kisten selbst überspringen
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    // Überspringe die beiden Kistenblöcke
                    boolean isChest1 = (x == c1x && y == c1y && z == c1z);
                    boolean isChest2 = (x == c2x && y == c2y && z == c2z);
                    if (isChest1 || isChest2) {
                        continue;
                    }

                    Block block = world.getBlockAt(x, y, z);
                    if (block.getType() == Material.GLASS) {
                        block.setType(Material.AIR);
                    }
                }
            }
        }
    }

    // Checks if Player has enough money to buy the Deadchest
    // is TRUE then it will run unlockDeadChest
    public void buyDeadchestUnlock(Player player) {
        // 🔹 1) Ist der Spieler überhaupt in der Nähe der DeadChest?
        if (!isNearDeadChest(player)) {
            player.sendMessage(ChatColor.RED + "❌ Du bist nicht in der Nähe deiner DeadChest!");
            return;
        }

        // 🔹 2) Existiert überhaupt eine DeadChest für diesen Spieler?
        if (!configManager.isDeadChest(player)) {
            player.sendMessage(ChatColor.RED + "❌ Es gibt keine DeadChest für dich!");
            return;
        }

        // 🔹 3) Hat der Spieler genug Geld, um die DeadChest zu kaufen?
        long price = configManager.getDeadChestOpenPrice();
        if (!economyManager.canAfford(player, price)) {
            player.sendMessage(ChatColor.RED + "❌ Du hast nicht genug Geld! (Kosten: $" + price);
            return;
        }

        // 🔹 4) Geld vom Spieler abziehen
        economyManager.takeMoney(player, price);
        player.sendMessage(ChatColor.GREEN + "✅ Du hast deine DeadChest für $" + price + "freigeschaltet!");

        // 🔹 5) DeadChest freischalten (Barrieren entfernen)
        unlockDeadChest(player);
    }


}