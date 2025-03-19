package de.blockorca.wildlandsEssential.logic;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import de.blockorca.wildlandsEssential.Main;
import de.blockorca.wildlandsEssential.data.ConfigManager;
import de.blockorca.wildlandsEssential.economy.EconomyManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Manages the flight functionality for players.
 *
 * <p>This class handles enabling flight for a player, monitoring the flight duration,
 * disabling flight when the duration expires, and providing a temporary period of fall damage protection.</p>
 */
public class FlyLogic {

    Main main;
    ConfigManager configManager;
    EconomyManager economyManager;
    Essentials essentials;

    /**
     * Constructs a new FlyLogic instance.
     *
     * @param main The main plugin instance used to obtain required managers.
     */
    public FlyLogic(Main main) {
        this.main = main;
        this.configManager = main.getConfigManager();
        this.economyManager = main.getEconomyManager();
        this.essentials = main.getEssentials();
    }

    /**
     * Enables flight for the specified player.
     *
     * <p>This method checks if the player can afford the flight fee and is not already allowed to fly.
     * If the checks pass, it deducts the flight fee from the player's balance, sets the flight duration,
     * enables flight, and starts a timer to monitor the flight period.</p>
     *
     * @param player The player for whom flight is to be enabled.
     */
    public void enableFly(Player player) {

        if (!(economyManager.canAfford(player, configManager.getFlyPrice()))) {
            player.sendMessage(ChatColor.RED + "You do not have enough money to purchase flight.");
            return;
        }
        if (configManager.getCanFly(player)) {
            player.sendMessage(ChatColor.GREEN + "You are already allowed to fly. Please wait until your current flight period expires to purchase again.");
            return;
        }
        BigDecimal price = BigDecimal.valueOf(configManager.getFlyPrice());
        User user = essentials.getUser(player);
        user.takeMoney(price);

        long currentTime = System.currentTimeMillis();
        // Multiply the flight duration (in minutes) by 60000 to convert to milliseconds.
        long flytimeM = configManager.getFlyDuration();
        long flytimeMS = flytimeM * 60000;
        long flyEndTime = currentTime + flytimeMS;
        long noFallDamageEndTime = flyEndTime + 30000;

        configManager.setCanFly(player, true);
        configManager.setFlyEndTimeMS(player, flyEndTime);
        configManager.setNoFallDamageEndTimeMS(player, noFallDamageEndTime);
        player.setAllowFlight(true);
        player.setFlying(true);
        startFlyTimer(player);
        player.sendMessage(ChatColor.GREEN + "You can now fly for " + ChatColor.RED + flytimeM + " minutes.");
    }

    /**
     * Starts a timer that monitors the player's flight duration.
     *
     * <p>This method schedules a repeating task that checks every second whether the player's flight time
     * has expired. Once expired, it disables flight and cancels the timer.</p>
     *
     * @param player The player whose flight duration is being monitored.
     */
    public void startFlyTimer(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (isFlyTimeExpired(player)) {
                    System.out.println("Flight time expired");
                    disableFly(player);
                    cancel(); // Stop monitoring this player.
                }
            }
        }.runTaskTimer(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("WildlandsEssential")), 20L, 20L); // Runs every 20 ticks (approximately 1 second).
    }

    /**
     * Checks whether the player's flight time has expired.
     *
     * @param player The player whose flight duration is being checked.
     * @return True if the flight time has expired, false otherwise.
     */
    public boolean isFlyTimeExpired(Player player) {
        long currentTime = System.currentTimeMillis();
        long flyEndTime = configManager.getFlyEndTimeMS(player);
        return flyEndTime <= currentTime;
    }

    /**
     * Disables flight for the specified player.
     *
     * <p>This method disables the player's ability to fly, informs the player that flight mode has been disabled,
     * and notifies them about the temporary period of fall damage protection.</p>
     *
     * @param player The player for whom flight is being disabled.
     */
    public void disableFly(Player player) {
        player.setAllowFlight(false);
        player.setFlying(false);
        player.sendMessage(ChatColor.GREEN + "Your flight mode has been disabled.");
        player.sendMessage(ChatColor.GREEN + "You will have fall damage protection for 30 seconds.");
        configManager.setCanFly(player, false);
    }

    /**
     * Determines if the player still has fall damage protection.
     *
     * <p>This method compares the current time with the end time of the fall damage protection period.</p>
     *
     * @param player The player to check for fall damage protection.
     * @return True if the fall damage protection period is still active, false otherwise.
     */
    public boolean hasFallDamageProtection(Player player) {
        long currentTime = System.currentTimeMillis();
        long noFallDamageEndTime = configManager.getNoFallDamageEndTimeMS(player);
        return noFallDamageEndTime > currentTime;
    }
}
