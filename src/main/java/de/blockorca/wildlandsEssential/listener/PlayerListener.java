package de.blockorca.wildlandsEssential.listener;

import de.blockorca.wildlandsEssential.Main;

import de.blockorca.wildlandsEssential.logic.DeadChestLogic;
import de.blockorca.wildlandsEssential.logic.FlyLogic;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Listener class for handling player events.
 *
 * <p>This class processes events such as entity damage and player death.
 * It cancels fall damage for players under flight protection and generates a DeadChest with the player's items upon death.</p>
 */
public class PlayerListener implements Listener {

    Main main;
    FlyLogic flyLogic;
    DeadChestLogic deadChestLogic;

    /**
     * Constructs a new PlayerListener.
     *
     * @param main the main plugin instance used to retrieve required logic components.
     */
    public PlayerListener(Main main) {
        this.main = main;
        this.flyLogic = main.getFlyLogic();
        this.deadChestLogic = main.getDeadChestLogic();
    }

    /**
     * Handles entity damage events.
     *
     * <p>If the damage is caused by falling and the player has active fall damage protection,
     * the damage is cancelled and a notification is sent to the player.</p>
     *
     * @param event the entity damage event
     */
    @EventHandler(ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            if (flyLogic.hasFallDamageProtection((Player) event.getEntity())) {
                event.setCancelled(true);
                event.getEntity().sendMessage(ChatColor.AQUA + "You are protected from fall damage for 30 seconds after flight!");
            }
        }
    }

    /**
     * Handles player death events.
     *
     * <p>Upon a player's death, this method generates a DeadChest at the player's location containing their dropped items,
     * and clears the drops to prevent items from being left on the ground.</p>
     *
     * @param event the player death event
     */
    @EventHandler(ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        List<ItemStack> drops = event.getDrops();
        deadChestLogic.generateDeadChest(player, drops);
        event.getDrops().clear();
    }


    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // ➤ Prüfe direkt beim Join: Falls die Flugzeit abgelaufen ist, deaktiviere Flug und setze Config‑Flag zurück
        if (main.getFlyLogic().isFlyTimeExpired(player)) {
            main.getFlyLogic().disableFly(player);
        }

        // bestehende Logik: neuen Spieler in Config anlegen
        main.getConfigManager().addUser(player);
    }





}
