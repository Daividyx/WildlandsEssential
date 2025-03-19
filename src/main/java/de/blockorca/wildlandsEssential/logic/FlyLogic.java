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

public class FlyLogic {



    Main main;
    ConfigManager configManager;
    EconomyManager economyManager;
    Essentials essentials;

    public FlyLogic(Main main) {
        this.main = main;
        this.configManager = main.getConfigManager();
        this.economyManager = main.getEconomyManager();
        this.essentials = main.getEssentials();
    }

    public void enableFly(Player player) {


        if(!(economyManager.canAfford(player,configManager.getFlyPrice()))){

            player.sendMessage(ChatColor.RED + "Du hast nicht genug Geld um dir das zu kaufen");
            return;
        }
        if(configManager.getCanFly(player)) {
            player.sendMessage(ChatColor.GREEN + "Du kannst noch Fliegen. Bitte warte bis du nicht mehr fliegen kannst um es dir erneut zu kaufen");
            return;
        }
        BigDecimal price = BigDecimal.valueOf(configManager.getFlyPrice());
        User user = essentials.getUser(player);
        user.takeMoney(price);


        long currentTime = System.currentTimeMillis();
        //wieder auf 60000 setzen nicht vergessen
        long flytimeM = configManager.getFlyDuration();
        long flytimeMS = flytimeM * 60000;
        long flyEndTime = currentTime + flytimeMS;
        long noFallDamageEndTime = flyEndTime + 30000;

        configManager.setCanFly(player, true);
        configManager.setFlyEndTimeMS(player,flyEndTime);
        configManager.setNoFallDamageEndTimeMS(player,noFallDamageEndTime);
        player.setAllowFlight(true);
        player.setFlying(true);
        startFlyTimer(player);
        player.sendMessage(ChatColor.GREEN + " Du kannst jetzt für " +ChatColor.RED + flytimeM +" Minuten Fliegen");

    }

    // ✅ Startet die Überwachung für die Flugzeit
    public void startFlyTimer(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {


                if (isFlyTimeExpired(player)) {
                    System.out.println("fly abgelaufen");
                    disableFly(player);

                    cancel(); // Stoppe die Überwachung für diesen Spieler
                }
            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("WildlandsEssential"), 20L, 20L); // Alle 20 Ticks (1 Sekunde)
    }

    // ✅ Prüft, ob die Flugzeit abgelaufen ist
    public boolean isFlyTimeExpired(Player player) {
        long currentTime = System.currentTimeMillis();
        long flyEndTime = configManager.getFlyEndTimeMS(player);
        /*
        System.out.println("🚀 DEBUG: Prüfe Fly-Status für " + player.getName());
        System.out.println("Aktuelle Zeit: " + currentTime);
        System.out.println("FlyEndTime aus Config: " + flyEndTime);
        System.out.println("Differenz (FlyEndTime - CurrentTime): " + (flyEndTime - currentTime));
         */
        return flyEndTime <= currentTime;
    }
    // 🛠 Spieler das Fliegen deaktivieren
    public void disableFly(Player player) {
        player.setAllowFlight(false);
        player.setFlying(false);
        player.sendMessage(ChatColor.GREEN +" Dein Flugmodus wurde deaktiviert.");
        player.sendMessage(ChatColor.GREEN + " Du bekommst 30 Sekunden lang keinen Fallschaden");
        configManager.setCanFly(player, false);
    }


      public boolean hasFallDamageProtection(Player player){

        long currentTime = System.currentTimeMillis();
        long noFallDamageEndTime = configManager.getNoFallDamageEndTimeMS(player);
        return noFallDamageEndTime > currentTime;

      }

}
