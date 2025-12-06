package logic;

import dat.*;
import models.*;

import java.sql.SQLException;

public class InventoryService {
    private final PlayerRepository playerRepository;
    private final ItemRepository itemRepository;
    private final WeaponRepository weaponRepository;
    private final ArmorRepository armorRepository;
    private final ConsumableRepository consumableRepository;

    private final int MAX_WEIGHT = 50;
    private final int MAX_SLOTS =192;
    private int unlockedSlots = 32;

    public InventoryService(PlayerRepository playerRepository, ItemRepository itemRepository, WeaponRepository weaponRepository, ArmorRepository armorRepository, ConsumableRepository consumableRepository) {
        this.playerRepository = playerRepository;
        this.itemRepository = itemRepository;
        this.weaponRepository = weaponRepository;
        this.armorRepository = armorRepository;
        this.consumableRepository = consumableRepository;
    }

    // Game logic
    private void checkWeight(Item item) throws SQLException{
        double totalWeight = getTotalWeightFromDb();
        if (totalWeight + item.getWeight() > MAX_WEIGHT) {
            throw new IllegalArgumentException("Cannot add item: item exceeds max weight of " + MAX_WEIGHT);
        }
    }

    private void checkSlotsAvailable() throws SQLException {
        int usedSlots = getUsedSlotsFromDb();
        if (usedSlots + 1 > unlockedSlots) {
            throw new IllegalArgumentException("Cannot add item: no free slots available");
        }
    }

    public void unlockSlots(int amount) {
        unlockedSlots = Math.min(unlockedSlots + amount, MAX_SLOTS);
    }

    public double getTotalWeightFromDb() {
        return weaponRepository.getTotalWeight()
                + armorRepository.getTotalWeight()
                + consumableRepository.getTotalWeight();
    }

    public int getUsedSlotsFromDb() {
        return weaponRepository.getItemCount()
                + armorRepository.getItemCount()
                + consumableRepository.getItemCount();
    }

    // Create objects
    public void createPlayer(String playerName, int credits, int level) {
        Player player = new Player(playerName, credits, level);
        playerRepository.addPlayer(player);
    }

    public void addItem(Item item) {
        try {
            checkWeight(item);
            checkSlotsAvailable();

            switch (item.getItemType()) {
                case "weapon":
                    weaponRepository.addItem((Weapon) item);
                    break;
                case "armor":
                    armorRepository.addItem((Armor) item);
                    break;
                case "consumable":
                    consumableRepository.addItem((Consumable) item);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown item type: " + item.getItemType());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error occurred while checking limits");
        }

    }
}

