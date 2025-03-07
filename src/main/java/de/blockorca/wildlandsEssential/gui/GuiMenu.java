package de.blockorca.wildlandsEssential.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface GuiMenu {

    void openMenu();  // Öffnet das Menü für den Spieler
    Inventory createMenu();  // Erstellt das Inventar
}
