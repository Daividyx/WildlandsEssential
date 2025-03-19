package de.blockorca.wildlandsEssential.data;

import de.blockorca.wildlandsEssential.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ConfigManager implements Listener {

    private File playerDataFile;
    private YamlConfiguration playerData;
    private Main main;

    public ConfigManager(Main main) {
        this.main = main;

        /* Load playerData.yml file */
        playerDataFile = new File(main.getDataFolder(), "playerdata.yml");
        createPlayerData();
    }
    public void reloadPlayerData() {
        playerData = YamlConfiguration.loadConfiguration(playerDataFile);
        System.out.println("🔄 Spieler-Konfiguration neu geladen!");
    }

    private void createPlayerData() {
        if (!main.getDataFolder().exists()) {
            main.getDataFolder().mkdirs();
        }

        if (!playerDataFile.exists()) {
            try {
                playerDataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        playerData = YamlConfiguration.loadConfiguration(playerDataFile);
    }

    private void saveConfig() {
        try {
            playerData.save(playerDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addUser(Player player) {
        UUID uuid = player.getUniqueId();
        String name = player.getName();
        String playerPath = "players." + uuid;

        if (!playerData.contains(playerPath)) {
            playerData.set(playerPath + ".name", name);
            playerData.set(playerPath + ".fly.hasBoughtFly", false);
            playerData.set(playerPath + ".fly.flyEndTimeMS", 0);
            playerData.set(playerPath + ".fly.noFallDamageEndTimeMS", 0);
            playerData.set(playerPath + ".deadChest.baughtDeadchest", false);
            playerData.set(playerPath + ".deadChest.isDeadChest", false);
            playerData.set(playerPath + ".deadChest.coordinates.x", null);
            playerData.set(playerPath + ".deadChest.coordinates.y", null);
            playerData.set(playerPath + ".deadChest.coordinates.z", null);
            playerData.set(playerPath + ".homes.maxHomes", 0);
            playerData.set(playerPath + ".warps.maxWarps", 0);
            playerData.set(playerPath + ".aloneSleep.baughtAloneSleep", false);

            saveConfig();
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        addUser(player);
    }

    public String getPlayerPath(Player player) {
        return "players." + player.getUniqueId();
    }

    // Preise für Home-Punkte
    public long getHome1Price() { return playerData.getLong("homes.home1", 9999); }
    public long getHome2Price() { return playerData.getLong("homes.home2", 9999); }
    public long getHome3Price() { return playerData.getLong("homes.home3", 9999); }
    public long getHome4Price() { return playerData.getLong("homes.home4", 9999); }
    public long getHome5Price() { return playerData.getLong("homes.home5", 9999); }


    // Preise für Warp-Punkte
    public long getWarp1Price() { return playerData.getLong("warps.warp1", 9999); }
    public long getWarp2Price() { return playerData.getLong("warps.warp2", 9999); }
    public long getWarp3Price() { return playerData.getLong("warps.warp3", 9999); }
    public long getWarp4Price() { return playerData.getLong("warps.warp4", 9999); }
    public long getWarp5Price() { return playerData.getLong("warps.warp5", 9999); }

    // Preise für DeadChest
    public long getDeadChestOpenPrice() { return playerData.getLong("deadChest.open", 9999); }
    public long getDeadChestTeleportPrice() { return playerData.getLong("deadChest.teleport", 9999); }

    // Preis für Alleine Schlafen
    public long getAloneSleepPrice() { return playerData.getLong("aloneSleep.aloneSleep", 9999); }

    // Fly-Kosten und Dauer
    public long getFlyPrice() { return playerData.getLong("buyable.fly.price", 9999); }
    public long getFlyDuration() { return playerData.getLong("buyable.fly.duration", 9999); }

    // Weitere kaufbare Funktionen (falls du sie später nutzt)
    public long getBuyable1Price() { return playerData.getLong("buyable.buyable1", 9999); }
    public long getBuyable2Price() { return playerData.getLong("buyable.buyable2", 9999); }
    public long getBuyable3Price() { return playerData.getLong("buyable.buyable3", 9999); }

    // Fly-Status
    public void setCanFly(Player player, boolean hasBoughtFly) {
        playerData.set(getPlayerPath(player) + ".fly.hasBoughtFly", hasBoughtFly);
        saveConfig();
    }
    public boolean getCanFly(Player player) {
        return playerData.getBoolean(getPlayerPath(player) + ".fly.hasBoughtFly", false);
    }

    // Fly-Endzeit
    public void setFlyEndTimeMS(Player player, long time) {
        playerData.set(getPlayerPath(player) + ".fly.flyEndTimeMS", time);
        saveConfig();
    }
    public long getFlyEndTimeMS(Player player) {
        return playerData.getLong(getPlayerPath(player) + ".fly.flyEndTimeMS", 0);
    }

    // Kein Fallschaden-Endzeit
    public void setNoFallDamageEndTimeMS(Player player, long time) {
        playerData.set(getPlayerPath(player) + ".fly.noFallDamageEndTimeMS", time);
        saveConfig();
    }
    public long getNoFallDamageEndTimeMS(Player player) {
        return playerData.getLong(getPlayerPath(player) + ".fly.noFallDamageEndTimeMS", 0);
    }

    // DeadChest - Kaufstatus
    public void setBoughtDeadChest(Player player, boolean bought) {
        playerData.set(getPlayerPath(player) + ".deadChest.baughtDeadchest", bought);
        saveConfig();
    }
    public boolean hasBoughtDeadChest(Player player) {
        return playerData.getBoolean(getPlayerPath(player) + ".deadChest.baughtDeadchest", false);
    }

    // DeadChest - Aktiver Zustand
    public void setIsDeadChest(Player player, boolean isDead) {
        playerData.set(getPlayerPath(player) + ".deadChest.isDeadChest", isDead);
        saveConfig();
    }
    public boolean isDeadChest(Player player) {
        return playerData.getBoolean(getPlayerPath(player) + ".deadChest.isDeadChest", false);
    }

    // DeadChest-Koordinaten
    public void setDeadChestCoordinates(Player player, int x, int y, int z) {
        playerData.set(getPlayerPath(player) + ".deadChest.coordinates.x", x);
        playerData.set(getPlayerPath(player) + ".deadChest.coordinates.y", y);
        playerData.set(getPlayerPath(player) + ".deadChest.coordinates.z", z);
        saveConfig();
    }
    public int getDeadChestX(Player player) {
        return playerData.getInt(getPlayerPath(player) + ".deadChest.coordinates.x", 0);
    }

    public int getDeadChestY(Player player) {
        return playerData.getInt(getPlayerPath(player) + ".deadChest.coordinates.y", 0);
    }

    public int getDeadChestZ(Player player) {
        return playerData.getInt(getPlayerPath(player) + ".deadChest.coordinates.z", 0);
    }

    // Maximale Homes & Warps
    public void setMaxHomes(Player player, int maxHomes) {
        playerData.set(getPlayerPath(player) + ".homes.maxHomes", maxHomes);
        saveConfig();
    }
    public int getMaxHomes(Player player) {
        return playerData.getInt(getPlayerPath(player) + ".homes.maxHomes", 0);
    }
    public void setMaxWarps(Player player, int maxWarps) {
        playerData.set(getPlayerPath(player) + ".warps.maxWarps", maxWarps);
        saveConfig();
    }
    public int getMaxWarps(Player player) {
        return playerData.getInt(getPlayerPath(player) + ".warps.maxWarps", 0);
    }

    // Alleine Schlafen - Status
    public void setBoughtAloneSleep(Player player, boolean bought) {
        playerData.set(getPlayerPath(player) + ".aloneSleep.baughtAloneSleep", bought);
        saveConfig();
    }
    public boolean hasBoughtAloneSleep(Player player) {
        return playerData.getBoolean(getPlayerPath(player) + ".aloneSleep.baughtAloneSleep", false);
    }

    //get PlayerData
    public void getPlayerData(CommandSender sender, String playerName) {
        // 1) Versuche, den OfflinePlayer zu ermitteln (auch wenn er gerade nicht online ist)
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);

        // In neueren Bukkit-Versionen ist offlinePlayer nie null,
        // aber falls du eine ältere Version hast oder sicher sein willst:
        if (offlinePlayer == null) {
            sender.sendMessage(ChatColor.RED + "Spieler '" + playerName + "' nicht gefunden!");
            return;
        }

        // 2) Bestimme den Basis-Pfad in der Config, z. B. "players.<UUID>"
        String uuidString = offlinePlayer.getUniqueId().toString();
        String basePath = "players." + uuidString;

        // 3) Prüfe, ob in der Config überhaupt Daten für diesen Spieler existieren
        if (!playerData.contains(basePath)) {
            sender.sendMessage(ChatColor.RED + "Keine Daten für Spieler '" + playerName + "' (UUID: " + uuidString + ") gefunden!");
            return;
        }

        // 4) Lies jetzt die Daten aus und gib sie Zeile für Zeile aus

        // Name
        String name = playerData.getString(basePath + ".name", "unbekannt");
        sender.sendMessage(ChatColor.YELLOW + "Name: " + name);

        // Fly-Daten
        long flyEndTimeMS = playerData.getLong(basePath + ".fly.flyEndTimeMS", 0);
        int flyEndTimeMinutes = playerData.getInt(basePath + ".fly.flyEndTimeMinutes", 0);
        long noFallDamageEndTimeMS = playerData.getLong(basePath + ".fly.noFallDamageEndTimeMS", 0);
        int noFallDamageEndTimeMinutes = playerData.getInt(basePath + ".fly.noFallDamageEndTimeMinutes", 0);
        boolean hasBoughtFly = playerData.getBoolean(basePath + ".fly.hasBoughtFly", false);

        sender.sendMessage(ChatColor.YELLOW + "flyEndTimeMS: " + flyEndTimeMS);
        sender.sendMessage(ChatColor.YELLOW + "flyEndTimeMinutes: " + flyEndTimeMinutes);
        sender.sendMessage(ChatColor.YELLOW + "noFallDamageEndTimeMS: " + noFallDamageEndTimeMS);
        sender.sendMessage(ChatColor.YELLOW + "noFallDamageEndTimeMinutes: " + noFallDamageEndTimeMinutes);
        sender.sendMessage(ChatColor.YELLOW + "hasBoughtFly: " + hasBoughtFly);

        // DeadChest-Daten
        boolean hasBoughtDeadChest = playerData.getBoolean(basePath + ".deadChest.hasBoughtDeadChest", false);
        boolean isDeadChest = playerData.getBoolean(basePath + ".deadChest.isDeadChest", false);
        int x = playerData.getInt(basePath + ".deadChest.coordinates.x", 0);
        int y = playerData.getInt(basePath + ".deadChest.coordinates.y", 0);
        int z = playerData.getInt(basePath + ".deadChest.coordinates.z", 0);

        sender.sendMessage(ChatColor.YELLOW + "hasBoughtDeadChest: " + hasBoughtDeadChest);
        sender.sendMessage(ChatColor.YELLOW + "isDeadChest: " + isDeadChest);
        sender.sendMessage(ChatColor.YELLOW + "DeadChest-Koordinaten: x=" + x + ", y=" + y + ", z=" + z);

        // Homes-Daten
        int maxHomes = playerData.getInt(basePath + ".homes.maxHomes", 0);
        int maxWarps = playerData.getInt(basePath + ".homes.maxWarps", 0);

        sender.sendMessage(ChatColor.YELLOW + "maxHomes: " + maxHomes);
        sender.sendMessage(ChatColor.YELLOW + "maxWarps: " + maxWarps);

        // aloneSleep
        boolean aloneSleep = playerData.getBoolean(basePath + ".aloneSleep", false);
        sender.sendMessage(ChatColor.YELLOW + "aloneSleep: " + aloneSleep);

        // Abschluss
        sender.sendMessage(ChatColor.GREEN + "[DEBUG] Ende der PlayerData-Ausgabe für " + playerName);
    }

}
