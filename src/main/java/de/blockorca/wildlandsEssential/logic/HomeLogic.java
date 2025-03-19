package de.blockorca.wildlandsEssential.logic;

import de.blockorca.wildlandsEssential.Main;
import de.blockorca.wildlandsEssential.data.ConfigManager;
import de.blockorca.wildlandsEssential.economy.EconomyManager;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Die Klasse {@code HomeLogic} verwaltet den Kauf von zusätzlichen Homes.
 * <p>
 * - Der Spieler kann ein Home kaufen, wenn er genug Geld hat.
 * - Die Anzahl der Homes wird durch EssentialsX-Permissions (`essentials.sethome.multiple.X`) geregelt.
 * - LuckPerms speichert die Permission dauerhaft.
 * - Jedes weitere Home kostet mehr als das vorherige (Preise werden aus der ConfigManager-Klasse geholt).
 * <p>
 * Diese Klasse besitzt eine **zentrale Methode**, die von außen aufgerufen wird:
 * {@link #buyHome(Player, int)}. Die Methode übernimmt alle Prüfungen.
 */
public class HomeLogic {

    private final Main main;
    private final ConfigManager configManager;
    private final EconomyManager economyManager;

    /**
     * Konstruktor für die Home-Logik.
     *
     * @param main Hauptplugin-Klasse, um auf ConfigManager und EconomyManager zuzugreifen.
     */
    public HomeLogic(Main main) {
        this.main = main;
        this.configManager = main.getConfigManager();
        this.economyManager = main.getEconomyManager();
    }

    /**
     * Prüft, ob der Spieler ein weiteres Home kaufen kann, zieht das Geld ab und
     * setzt die EssentialsX-Permission über LuckPerms.
     *
     * @param player Spieler, der ein Home kaufen möchte.
     * @param homeNumber Die Nummer des Homes, das gekauft werden soll (z. B. 2 für das zweite Home).
     */
    public void buyHome(Player player, int homeNumber) {
        // 🔹 1) Preis für das gewünschte Home aus der Config holen (mit Switch-Case)
        long price;
        switch (homeNumber) {
            case 1 -> price = configManager.getHome1Price();
            case 2 -> price = configManager.getHome2Price();
            case 3 -> price = configManager.getHome3Price();
            case 4 -> price = configManager.getHome4Price();
            case 5 -> price = configManager.getHome5Price();
            default -> {
                player.sendMessage(ChatColor.RED + "❌ Fehler: Keine Preisangabe für Home #" + homeNumber + " in der Config!");
                return;
            }
        }

        // 🔹 2) Prüfen, ob der Spieler genug Geld hat
        if (!economyManager.canAfford(player, price)) {
            player.sendMessage(ChatColor.RED + "❌ Du hast nicht genug Geld für Home #" + homeNumber + "! (Kosten: $" + price + ")");
            return;
        }

        // 🔹 3) Geld vom Spieler abziehen
        economyManager.takeMoney(player, price);
        player.sendMessage(ChatColor.GREEN + "✅ Du hast erfolgreich Home #" + homeNumber + " gekauft! Du kannst nun " + homeNumber + " Homes setzen.");

        // 🔹 4) Neue Home-Anzahl in der Config speichern
        configManager.setMaxHomes(player, homeNumber);

        // 🔹 5) LuckPerms verwenden, um die EssentialsX-Permission dauerhaft zu setzen
        setHomePermission(player, homeNumber);
    }

    /**
     * Setzt die EssentialsX-Permission für die maximale Anzahl an Homes über LuckPerms.
     *
     * @param player       Spieler, der ein Home kauft.
     * @param newHomeLimit Neue maximale Anzahl an Homes.
     */
    private void setHomePermission(Player player, int newHomeLimit) {
        LuckPerms api = LuckPermsProvider.get();
        User user = api.getUserManager().getUser(player.getUniqueId());

        if (user != null) {
            // 🔹 1) Alte Home-Permissions entfernen
            for (int i = 1; i <= 100; i++) { // Bis zu 100 Homes, falls nötig
                String oldPermission = "essentials.sethome.multiple." + i;
                user.data().remove(Node.builder(oldPermission).build());
            }

            // 🔹 2) Neue Home-Permission setzen
            String newPermission = "essentials.sethome.multiple." + newHomeLimit;
            user.data().add(Node.builder(newPermission).build());

            // 🔹 3) Änderungen speichern
            api.getUserManager().saveUser(user);

            // 🔹 4) Erfolgsmeldung an den Spieler senden
            player.sendMessage(ChatColor.GREEN + "✅ Deine neue Home-Grenze ist nun: " + newHomeLimit);
        } else {
            player.sendMessage(ChatColor.RED + "❌ Fehler: Konnte deine Home-Permission nicht aktualisieren.");
        }
    }
}
