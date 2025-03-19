package de.blockorca.wildlandsEssential.economy;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import org.bukkit.entity.Player;

import java.math.BigDecimal;

/**
 * Manages economic interactions using the Essentials API.
 *
 * <p>This class provides methods to check if a player can afford a certain amount
 * and to deduct money from a player's balance.</p>
 */
public class EconomyManager {

    private final Essentials essentials;

    /**
     * Constructs a new EconomyManager with the given Essentials instance.
     *
     * @param essentials the Essentials API instance used for managing player balances
     */
    public EconomyManager(Essentials essentials) {
        this.essentials = essentials;
    }

    /**
     * Checks if the player has at least the specified amount of money.
     *
     * @param player the player whose balance is checked
     * @param amount the amount to compare against the player's balance
     * @return true if the player's balance is greater than or equal to the amount; false otherwise
     */
    public boolean canAfford(Player player, long amount) {
        BigDecimal balance = essentials.getUser(player).getMoney();
        return balance.compareTo(BigDecimal.valueOf(amount)) >= 0;
    }

    /**
     * Deducts the specified amount of money from the player's balance.
     *
     * @param player the player whose balance will be reduced
     * @param amount the amount to deduct
     */
    public void takeMoney(Player player, long amount) {
        User user = essentials.getUser(player);
        user.takeMoney(BigDecimal.valueOf(amount));
    }
}
