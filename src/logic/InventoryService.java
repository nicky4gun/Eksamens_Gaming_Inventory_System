package logic;

import dat.*;
import models.*;

public class InventoryService {
    private final PlayerRepository playerRepository;
    private final ItemRepository itemRepository;
    private final WeaponRepository weaponRepository;
    private final ArmorRepository armorRepository;
    private final ConsumableRepository consumableRepository;

    private final int MAX_WEIGHT = 50;
    private final int MAX_SLOTS =192;
    private int unlockedSlots = 32;

    private double currentWeight = 0;
    private int usedSlots = 0;

    public InventoryService(PlayerRepository playerRepository, ItemRepository itemRepository, WeaponRepository weaponRepository, ArmorRepository armorRepository, ConsumableRepository consumableRepository) {
        this.playerRepository = playerRepository;
        this.itemRepository = itemRepository;
        this.weaponRepository = weaponRepository;
        this.armorRepository = armorRepository;
        this.consumableRepository = consumableRepository;
    }

    // Game logic


    // Create objects
    public void createPlayer(String playerName, int credits, int level) {
        Player player = new Player(playerName, credits, level);
        playerRepository.addPlayer(player);
    }

    public void addItem(Item item) {
        String type = item.getItemType();

        switch (type) {
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
                throw new IllegalArgumentException("Unknown item type: " + type);
        }
    }
}

