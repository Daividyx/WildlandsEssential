package de.blockorca.wildlandsEssential.economy;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import net.ess3.api.MaxMoneyException;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.math.BigDecimal;

public class EconomyManager {


    private final Essentials essentials;

    public EconomyManager(Essentials essentials) {
        this.essentials = essentials;
    }

    public BigDecimal getBalance(Player player) {

        User user = essentials.getUser(player);
        if(user == null){
            return BigDecimal.ZERO;
        }
        return user.getMoney();
    }

    public void setBalance(Player player, BigDecimal amount) {
        User user = essentials.getUser(player);
        if (user == null) {
            return;
        }
        try {
            user.setMoney(amount);

        }
        catch (MaxMoneyException e) {
            player.sendMessage(ChatColor.RED + "Du kannst nicht mehr als 10000000000000 Dollar besitzen!");
        }

}

    public void addBalance(Player player, BigDecimal amount) {

        User user = essentials.getUser(player);
        if (user == null) {
            return;
        }
        BigDecimal balance = user.getMoney();
        if(amount.compareTo(BigDecimal.ZERO) < 0) {
            try {
                user.setMoney(balance.add(amount));
            } catch (MaxMoneyException e) {
                player.sendMessage("Da ist was schief gelaufen. Ich glaube die Zahl war zu hoch");
            }
        }
        if(amount.compareTo(BigDecimal.ZERO) > 0) {
            try {
                user.setMoney(balance.add(amount));
            } catch (MaxMoneyException e) {
                player.sendMessage("Da ist was schief gelaufen. Ich glaube die Zahl war zu hoch");
            }
        }
    }




}

