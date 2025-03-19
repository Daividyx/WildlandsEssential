package de.blockorca.wildlandsEssential.logic;

import com.earth2me.essentials.Essentials;
import de.blockorca.wildlandsEssential.Main;
import de.blockorca.wildlandsEssential.data.ConfigManager;
import de.blockorca.wildlandsEssential.economy.EconomyManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Handles the logic for managing a DeadChest.
 *
 * <p>A DeadChest is implemented as a double chest that can be generated at the player's location,
 * secured with barriers, unlocked, or removed. The coordinates and state of the DeadChest are stored
 * in the configuration.</p>
 */
public class DeadChestLogic {

    /**
     * Main plugin instance.
     */
    private final Main main;
    /**
     * Manager for configuration settings and DeadChest coordinates.
     */
    private final ConfigManager configManager;
    /**
     * Manager for economic transactions.
     */
    private final EconomyManager economyManager;
    /**
     * Essentials API reference.
     */
    private final Essentials essentials;
    /**
     * The world in which the DeadChest is created. This may be set during first use.
     */
    private World world;

    /**
     * Constructs a DeadChestLogic instance.
     *
     * @param main the main plugin instance used to retrieve managers and other dependencies
     */
    public DeadChestLogic(Main main) {
        this.main = main;
        this.configManager = main.getConfigManager();
        this.economyManager = main.getEconomyManager();
        this.essentials = main.getEssentials();
    }

    /**
     * Checks if the player is within proximity of their DeadChest.
     *
     * <p>The allowed range is up to 5 blocks in the X and Z directions and 2 blocks in the Y direction.
     * Returns true if the player is close enough, otherwise false.</p>
     *
     * @param player the player whose location is being checked
     * @return true if the player is near the DeadChest; false otherwise
     */
    public boolean isNearDeadChest(Player player) {
        Location playerLocation = player.getLocation();
        int deadChestX = configManager.getDeadChestX(player);
        int deadChestY = configManager.getDeadChestY(player);
        int deadChestZ = configManager.getDeadChestZ(player);

        int playerLocationX = playerLocation.getBlockX();
        int playerLocationY = playerLocation.getBlockY();
        int playerLocationZ = playerLocation.getBlockZ();

        int diffX = Math.abs(deadChestX - playerLocationX);
        int diffY = Math.abs(deadChestY - playerLocationY);
        int diffZ = Math.abs(deadChestZ - playerLocationZ);

        return (diffX <= 5 && diffY <= 2 && diffZ <= 5);
    }

    /**
     * Generates a new DeadChest (double chest) at the player's position and inserts the specified items.
     *
     * <p>If a DeadChest already exists for the player, it is removed before creating the new one.
     * The chest is created at the player's position (one block above the player's current Y-coordinate)
     * and secured by surrounding it with barriers. The coordinates are then stored in the configuration.</p>
     *
     * @param player the player for whom the DeadChest is created
     * @param items  the list of items to be added to the DeadChest inventory
     */
    public void generateDeadChest(Player player, List<ItemStack> items) {

        World world = player.getWorld();

        if (configManager.isDeadChest(player)) {
            int x = configManager.getDeadChestX(player);
            int y = configManager.getDeadChestY(player);
            int z = configManager.getDeadChestZ(player);
            Location oldLocation = new Location(world, x, y, z);


            removeDeadChest(player);
        }

        int playerX = player.getLocation().getBlockX();
        int playerY = player.getLocation().getBlockY();
        int playerZ = player.getLocation().getBlockZ();

        // Create a double chest:
        // chestLoc1 = (x, y+1, z)
        // chestLoc2 = (x+1, y+1, z)
        Location chestLoc1 = new Location(world, playerX, playerY + 1, playerZ);
        Location chestLoc2 = new Location(world, playerX + 1, playerY + 1, playerZ);

        // Clear the blocks by setting them to AIR first
        chestLoc1.getBlock().setType(Material.AIR);
        chestLoc2.getBlock().setType(Material.AIR);

        // Set both blocks to CHEST type
        chestLoc1.getBlock().setType(Material.CHEST);
        chestLoc2.getBlock().setType(Material.CHEST);

        // Retrieve the BlockData to enforce the double chest configuration
        Block chestBlock1 = chestLoc1.getBlock();
        Block chestBlock2 = chestLoc2.getBlock();

        org.bukkit.block.data.type.Chest chestData1 = (org.bukkit.block.data.type.Chest) chestBlock1.getBlockData();
        org.bukkit.block.data.type.Chest chestData2 = (org.bukkit.block.data.type.Chest) chestBlock2.getBlockData();

        // Set the facing direction for both chests (e.g., NORTH)
        chestData1.setFacing(BlockFace.NORTH);
        chestData2.setFacing(BlockFace.NORTH);

        // Force double chest formation: first chest is LEFT, second is RIGHT
        chestData1.setType(org.bukkit.block.data.type.Chest.Type.LEFT);
        chestData2.setType(org.bukkit.block.data.type.Chest.Type.RIGHT);

        // Apply the modified BlockData
        chestBlock1.setBlockData(chestData1);
        chestBlock2.setBlockData(chestData2);

        Chest deadChest = (Chest) chestLoc1.getBlock().getState();
        Inventory chestInventory = deadChest.getInventory();

        for (ItemStack i : items) {
            chestInventory.addItem(i);
        }

        secureDeadChest(player, chestLoc1, chestLoc2);

        // Save DeadChest status and coordinates to the configuration
        configManager.setIsDeadChest(player, true);
        configManager.setDeadChestCoordinates(player, chestLoc1.getBlockX(), chestLoc1.getBlockY(), chestLoc1.getBlockZ());
        player.sendMessage(ChatColor.GOLD + "No worries! Your items are safely stored in your personal DeadChest. Visit the Wildlands Menu to buy them back");
    }

    /**
     * Removes the existing DeadChest for the player, if present.
     *
     * <p>This method first removes the barriers surrounding the DeadChest by unlocking it,
     * then sets both chest blocks to AIR, and finally resets the corresponding configuration entries.</p>
     *
     * @param player the player whose DeadChest is to be removed
     */
    public void removeDeadChest(Player player) {
        World currentWorld = player.getWorld();
        int deadChestX = configManager.getDeadChestX(player);
        int deadChestY = configManager.getDeadChestY(player);
        int deadChestZ = configManager.getDeadChestZ(player);

        Location chestLoc1 = new Location(currentWorld, deadChestX, deadChestY, deadChestZ);
        Location chestLoc2 = new Location(currentWorld, deadChestX + 1, deadChestY, deadChestZ);

        Block block1 = chestLoc1.getBlock();
        Block block2 = chestLoc2.getBlock();

        if (block1.getType() == Material.CHEST) {
            block1.setType(Material.AIR);
        }
        if (block2.getType() == Material.CHEST) {
            block2.setType(Material.AIR);
        }
        unlockDeadChest(player);

        configManager.setIsDeadChest(player, false);
        configManager.setDeadChestCoordinates(player, 123456789, 123456789, 123456789);
    }

    /**
     * Secures the double chest by surrounding it with barriers.
     *
     * <p>The method fills a fixed 4×3×2 area around the two chest blocks with barriers, excluding
     * the chest blocks themselves. This area extends 1 block beyond the chests in all directions.
     * A message is sent to the player once the chest is fully secured.</p>
     *
     * @param player    the player for whom the DeadChest is being secured
     * @param chestLoc1 the location of the first chest block
     * @param chestLoc2 the location of the second chest block
     */
    public void secureDeadChest(Player player, Location chestLoc1, Location chestLoc2) {
        if (chestLoc1 == null || chestLoc2 == null) {
            //player.sendMessage(ChatColor.RED + "Error: One of the chest locations is null!");
            return;
        }
        World world = chestLoc1.getWorld();
        if (world == null) {
           // player.sendMessage(ChatColor.RED + "Error: World is null!");
            return;
        }

        int c1x = chestLoc1.getBlockX();
        int c1y = chestLoc1.getBlockY();
        int c1z = chestLoc1.getBlockZ();

        int c2x = chestLoc2.getBlockX();
        int c2y = chestLoc2.getBlockY();
        int c2z = chestLoc2.getBlockZ();

        int minX = Math.min(c1x, c2x) - 1;
        int maxX = Math.max(c1x, c2x) + 1;
        int minY = c1y - 1;
        int maxY = c1y + 1;
        int minZ = c1z - 1;
        int maxZ = c1z + 1;

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    if ((x == c1x && y == c1y && z == c1z) ||
                            (x == c2x && y == c2y && z == c2z)) {
                        continue;
                    }
                    Block block = world.getBlockAt(x, y, z);
                    block.setType(Material.BARRIER);
                }
            }
        }

        //player.sendMessage(ChatColor.GREEN + "Chest fully secured with barriers!");
    }

    /**
     * Removes the barriers surrounding the DeadChest, unlocking it for the player.
     *
     * <p>This method calculates the same 4×3×2 area used to secure the chest and sets any barrier blocks
     * found within that area to AIR, leaving the chest blocks intact.</p>
     *
     * @param player the player whose DeadChest barriers are to be removed
     */
    public void unlockDeadChest(Player player) {
        World world = player.getWorld();
        int c1x = configManager.getDeadChestX(player);
        int c1y = configManager.getDeadChestY(player);
        int c1z = configManager.getDeadChestZ(player);
        Location chestLoc1 = new Location(world, c1x, c1y, c1z);

        Location chestLoc2 = chestLoc1.clone().add(1, 0, 0);

        int c2x = chestLoc2.getBlockX();
        int c2y = chestLoc2.getBlockY();
        int c2z = chestLoc2.getBlockZ();

        int minX = Math.min(c1x, c2x) - 1;
        int maxX = Math.max(c1x, c2x) + 1;
        int minY = c1y - 1;
        int maxY = c1y + 1;
        int minZ = c1z - 1;
        int maxZ = c1z + 1;

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    boolean isChest1 = (x == c1x && y == c1y && z == c1z);
                    boolean isChest2 = (x == c2x && y == c2y && z == c2z);
                    if (isChest1 || isChest2) {
                        continue;
                    }
                    Block block = world.getBlockAt(x, y, z);
                    if (block.getType() == Material.BARRIER) {
                        block.setType(Material.AIR);
                    }
                }
            }
        }
    }

    /**
     * Processes the purchase to unlock the DeadChest.
     *
     * <p>This method checks that the player is near their DeadChest, verifies that a DeadChest exists,
     * and confirms that the player has sufficient funds. If all checks pass, it deducts the required amount
     * and unlocks the DeadChest by removing the barriers.</p>
     *
     * @param player the player attempting to unlock their DeadChest
     */
    public void buyDeadchestUnlock(Player player) {
        if (!isNearDeadChest(player)) {
            player.sendMessage(ChatColor.RED + "❌ You are not near your DeadChest!");
            return;
        }

        if (!configManager.isDeadChest(player)) {
            player.sendMessage(ChatColor.RED + "❌ No DeadChest exists for you!");
            return;
        }

        long price = configManager.getDeadChestOpenPrice();
        if (!economyManager.canAfford(player, price)) {
            player.sendMessage(ChatColor.RED + "❌ You do not have enough money! (Cost: $" + price);
            return;
        }

        economyManager.takeMoney(player, price);
        player.sendMessage(ChatColor.GREEN + "✅ You have unlocked your DeadChest for $" + price + "!");

        unlockDeadChest(player);
    }
}
