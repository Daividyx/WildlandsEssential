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

    public boolean canAfford(Player player, long amount) {

        BigDecimal balance = essentials.getUser(player).getMoney();

        if(balance.compareTo(BigDecimal.valueOf(amount)) >= 0) {

                return true;
        }

        return false;




    }

    public void takeMoney(Player player, long amount) {
        User user = essentials.getUser(player);
        user.takeMoney(BigDecimal.valueOf(amount));
    }

/*
*
*
* Gestorben 0,0,0
* Kisten 0,1,0 // 1,1,0  Facing North
* Barrieren: (2,0,0)(2,1,0)(2,2,0)
*            (1,0,0)(1,2,0)
*            (-1,0-0)(-1,1,0)(-1,2.0)
*           ()()()()()()()()()()()()()()()
*
*
*
*
* */


}

