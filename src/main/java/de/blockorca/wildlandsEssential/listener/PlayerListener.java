package de.blockorca.wildlandsEssential.listener;

import de.blockorca.wildlandsEssential.Main;
import de.blockorca.wildlandsEssential.data.ConfigManager;
import de.blockorca.wildlandsEssential.logic.DeadChestLogic;
import de.blockorca.wildlandsEssential.logic.FlyLogic;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PlayerListener implements Listener {

    Main main;
    FlyLogic flyLogic;
    DeadChestLogic deadChestLogic;

    public PlayerListener(Main main) {
        this.main = main;
        this.flyLogic = main.getFlyLogic();
        this.deadChestLogic = main.getDeadChestLogic();
    }
    // Fallschaden nach Fly ignorieren
    @EventHandler(ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {

        if(!(event.getEntity() instanceof Player)) {
            return;
        }
        if(event.getCause()== EntityDamageEvent.DamageCause.FALL) {
            if(flyLogic.hasFallDamageProtection((Player) event.getEntity())) {
                event.setCancelled(true);
                event.getEntity().sendMessage(ChatColor.AQUA + "Gut dass du 30 Sekunden nach dem Ende von fly Fallschaden Schutz hast!");
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent event) {

        Player player = event.getEntity();
        List<ItemStack> drops = event.getDrops();
        deadChestLogic.generateDeadChest(player, drops);
        event.getDrops().clear();

    }


}
