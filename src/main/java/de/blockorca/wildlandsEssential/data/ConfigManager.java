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

/**
 * Manages persistent player data stored in a YAML file.
 *
 * <p>This class loads, creates, and saves a “playerdata.yml” file in the plugin data folder.
 * It provides methods for reading and writing player-specific settings (prices, purchase statuses,
 * coordinates, and limits) and ensures default data is initialized on player join.</p>
 */
@SuppressWarnings("SpellCheckingInspection")
public class ConfigManager implements Listener {

    private final File playerDataFile;
    private YamlConfiguration playerData;
    private final Main main;

    /**
     * Constructs a ConfigManager and ensures the player data file exists.
     *
     * @param main the main plugin instance used to locate the data folder
     */
    public ConfigManager(Main main) {
        this.main = main;
        this.playerDataFile = new File(main.getDataFolder(), "playerdata.yml");
        createPlayerData();
    }

    /**
     * Reloads the player data configuration from disk.
     */
    public void reloadPlayerData() {
        playerData = YamlConfiguration.loadConfiguration(playerDataFile);
        System.out.println("🔄 Player configuration reloaded!");
    }

    /**
     * Creates the player data file and folder if they do not already exist, then loads its contents.
     */
    @SuppressWarnings("Result")

    private void createPlayerData() {
        if (!main.getDataFolder().exists()) {
            main.getDataFolder().mkdirs();
        }

        if (!playerDataFile.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                playerDataFile.createNewFile();
            } catch (IOException e) {
                //noinspection CallToPrintStackTrace
                e.printStackTrace();
            }
        }

        playerData = YamlConfiguration.loadConfiguration(playerDataFile);
    }


    /**
     * Saves the in-memory YAML configuration back to disk.
     */
    private void saveConfig() {
        try {
            playerData.save(playerDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes default configuration entries for a new player if none exist.
     *
     * @param player the player to add to the configuration
     */
    public void addUser(Player player) {
        String playerPath = "players." + player.getUniqueId();
        if (!playerData.contains(playerPath)) {
            playerData.set(playerPath + ".name", player.getName());
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

    /**
     * Event handler to ensure each joining player has an entry in the data file.
     *
     * @param event the join event
     */
    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        addUser(event.getPlayer());
    }

    /**
     * Returns the base configuration path for a given player.
     *
     * @param player the player
     * @return the config path prefix (e.g., "players.<UUID>")
     */
    public String getPlayerPath(Player player) {
        return "players." + player.getUniqueId();
    }

    // Price getters
    public long getHome1Price() { return playerData.getLong("homes.home1", 9999); }
    public long getHome2Price() { return playerData.getLong("homes.home2", 9999); }
    public long getHome3Price() { return playerData.getLong("homes.home3", 9999); }
    public long getHome4Price() { return playerData.getLong("homes.home4", 9999); }
    public long getHome5Price() { return playerData.getLong("homes.home5", 9999); }
    public long getWarp1Price() { return playerData.getLong("warps.warp1", 9999); }
    public long getWarp2Price() { return playerData.getLong("warps.warp2", 9999); }
    public long getWarp3Price() { return playerData.getLong("warps.warp3", 9999); }
    public long getWarp4Price() { return playerData.getLong("warps.warp4", 9999); }
    public long getWarp5Price() { return playerData.getLong("warps.warp5", 9999); }
    public long getDeadChestOpenPrice() { return playerData.getLong("deadChest.open", 9999); }
    public long getDeadChestTeleportPrice() { return playerData.getLong("deadChest.teleport", 9999); }
    public long getAloneSleepPrice() { return playerData.getLong("aloneSleep.aloneSleep", 9999); }
    public long getFlyPrice() { return playerData.getLong("buyable.fly.price", 9999); }
    public long getFlyDuration() { return playerData.getLong("buyable.fly.duration", 9999); }
    public long getBuyable1Price() { return playerData.getLong("buyable.buyable1", 9999); }
    public long getBuyable2Price() { return playerData.getLong("buyable.buyable2", 9999); }
    public long getBuyable3Price() { return playerData.getLong("buyable.buyable3", 9999); }

    // Fly state
    public void setCanFly(Player player, boolean hasBoughtFly) {
        playerData.set(getPlayerPath(player) + ".fly.hasBoughtFly", hasBoughtFly);
        saveConfig();
    }
    public boolean getCanFly(Player player) {
        return playerData.getBoolean(getPlayerPath(player) + ".fly.hasBoughtFly", false);
    }
    public void setFlyEndTimeMS(Player player, long time) {
        playerData.set(getPlayerPath(player) + ".fly.flyEndTimeMS", time);
        saveConfig();
    }
    public long getFlyEndTimeMS(Player player) {
        return playerData.getLong(getPlayerPath(player) + ".fly.flyEndTimeMS", 0);
    }
    public void setNoFallDamageEndTimeMS(Player player, long time) {
        playerData.set(getPlayerPath(player) + ".fly.noFallDamageEndTimeMS", time);
        saveConfig();
    }
    public long getNoFallDamageEndTimeMS(Player player) {
        return playerData.getLong(getPlayerPath(player) + ".fly.noFallDamageEndTimeMS", 0);
    }

    // DeadChest state
    @SuppressWarnings("unused")
    public void setBoughtDeadChest(Player player, boolean bought) {
        playerData.set(getPlayerPath(player) + ".deadChest.baughtDeadchest", bought);
        saveConfig();
    }
    public boolean hasBoughtDeadChest(Player player) {
        return playerData.getBoolean(getPlayerPath(player) + ".deadChest.baughtDeadchest", false);
    }
    public void setIsDeadChest(Player player, boolean isDead) {
        playerData.set(getPlayerPath(player) + ".deadChest.isDeadChest", isDead);
        saveConfig();
    }
    public boolean isDeadChest(Player player) {
        return playerData.getBoolean(getPlayerPath(player) + ".deadChest.isDeadChest", false);
    }
    public void setDeadChestCoordinates(Player player, int x, int y, int z) {
        playerData.set(getPlayerPath(player) + ".deadChest.coordinates.x", x);
        playerData.set(getPlayerPath(player) + ".deadChest.coordinates.y", y);
        playerData.set(getPlayerPath(player) + ".deadChest.coordinates.z", z);
        saveConfig();
    }
    public int getDeadChestX(Player player) { return playerData.getInt(getPlayerPath(player) + ".deadChest.coordinates.x", 0); }
    public int getDeadChestY(Player player) { return playerData.getInt(getPlayerPath(player) + ".deadChest.coordinates.y", 0); }
    public int getDeadChestZ(Player player) { return playerData.getInt(getPlayerPath(player) + ".deadChest.coordinates.z", 0); }

    // Homes & Warps limits
    @SuppressWarnings("unused")
    public void setMaxHomes(Player player, int maxHomes) {
        playerData.set(getPlayerPath(player) + ".homes.maxHomes", maxHomes);
        saveConfig();
    }
    public int getMaxHomes(Player player) {
        return playerData.getInt(getPlayerPath(player) + ".homes.maxHomes", 0);
    }
    @SuppressWarnings("unused")
    public void setMaxWarps(Player player, int maxWarps) {
        playerData.set(getPlayerPath(player) + ".warps.maxWarps", maxWarps);
        saveConfig();
    }
    public int getMaxWarps(Player player) {
        return playerData.getInt(getPlayerPath(player) + ".warps.maxWarps", 0);
    }

    // Solo sleep purchase status
    @SuppressWarnings("unused")
    public void setBoughtAloneSleep(Player player, boolean bought) {
        playerData.set(getPlayerPath(player) + ".aloneSleep.baughtAloneSleep", bought);
        saveConfig();
    }
    public boolean hasBoughtAloneSleep(Player player) {
        return playerData.getBoolean(getPlayerPath(player) + ".aloneSleep.baughtAloneSleep", false);
    }

    /**
     * Displays a player’s stored data for debugging purposes.
     *
     * @param sender     the command sender receiving the output
     * @param playerName the name of the target player
     */
    @SuppressWarnings("deprecation")
    public void getPlayerData(CommandSender sender, String playerName) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);
        String basePath = "players." + offlinePlayer.getUniqueId();

        if (!playerData.contains(basePath)) {
            sender.sendMessage(ChatColor.RED + "No data for player '" + playerName + "'!");
            return;
        }

        sender.sendMessage(ChatColor.YELLOW + "Data for " + playerName + ":");
        sender.sendMessage(ChatColor.YELLOW + "Has bought fly: " + getCanFly(offlinePlayer.getPlayer()));
        sender.sendMessage(ChatColor.YELLOW + "Has bought DeadChest: " + hasBoughtDeadChest(offlinePlayer.getPlayer()));
        sender.sendMessage(ChatColor.YELLOW + "DeadChest coords: " +
                getDeadChestX(offlinePlayer.getPlayer()) + ", " +
                getDeadChestY(offlinePlayer.getPlayer()) + ", " +
                getDeadChestZ(offlinePlayer.getPlayer()));
        sender.sendMessage(ChatColor.YELLOW + "Max homes: " + getMaxHomes(offlinePlayer.getPlayer()));
        sender.sendMessage(ChatColor.YELLOW + "Max warps: " + getMaxWarps(offlinePlayer.getPlayer()));
        sender.sendMessage(ChatColor.YELLOW + "Alone sleep purchased: " + hasBoughtAloneSleep(offlinePlayer.getPlayer()));
        sender.sendMessage(ChatColor.GREEN + "End of data for " + playerName);
    }
}
