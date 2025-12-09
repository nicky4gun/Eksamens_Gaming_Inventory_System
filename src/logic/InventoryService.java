package logic;

import dat.*;
import models.*;
import models.enums.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InventoryService {
    private final PlayerRepository playerRepository;
    // private final ItemRepository itemRepository;
    private final WeaponRepository weaponRepository;
    private final ArmorRepository armorRepository;
    private final ConsumableRepository consumableRepository;

    private final int MAX_WEIGHT = 50;
    private final int MAX_SLOTS =192;
    private int unlockedSlots = 32;

    public InventoryService(PlayerRepository playerRepository, WeaponRepository weaponRepository, ArmorRepository armorRepository, ConsumableRepository consumableRepository) {
        this.playerRepository = playerRepository;
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

    // Ikke i brug endnu
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
                case WEAPON:
                    weaponRepository.addItem((Weapon) item);
                    break;
                case ARMOR:
                    armorRepository.addItem((Armor) item);
                    break;
                case CONSUMABLE:
                    consumableRepository.addItem((Consumable) item);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown item type: " + item.getItemType());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error occurred while checking limits");
        }
    }

    public void deleteItemFromInventory(String name) {
        weaponRepository.deleteItem(name);
        armorRepository.deleteItem(name);
        consumableRepository.deleteItem(name);
    }

    public List<Item> findAllItems() {
        List<Item> items = new ArrayList<>();

        items.addAll(weaponRepository.readAllItems());
        items.addAll(armorRepository.readAllItems());
        items.addAll(consumableRepository.readAllItems());

        return items;
    }

    public Item createRandomItem() {
        Random rand = new Random();

        int choice = 1 + rand.nextInt(3);

        double weight = 0.5 + (10 * rand.nextDouble());

        ItemCategory category;
        WeaponCategory weaponCategory;
        CoolWeaponNames coolWeaponNames;
        CoolWeaponNames coolWeaponNames2;
        ArmorCategory armorCategory;
        CoolArmorNames coolArmorNames;
        CoolConsumabelName coolConsumabelName;

        switch (choice) {
            case 1: // Weapon
                category = ItemCategory.WEAPON;

                int damage = 5 + rand.nextInt(30);
                double attackSpeed = 0.5 + rand.nextDouble() * 2;
                boolean isOneHanded = rand.nextBoolean();

                weaponCategory = WeaponCategory.values()[rand.nextInt(WeaponCategory.values().length)];
                coolWeaponNames = CoolWeaponNames.values()[rand.nextInt(CoolWeaponNames.values().length)];
                coolWeaponNames2 = CoolWeaponNames.values()[rand.nextInt(CoolWeaponNames.values().length)];

                int nameGen = rand.nextInt(3) + 1;
                String weaponName;

                if (nameGen == 1) {
                    weaponName = weaponCategory + " " + coolWeaponNames;
                }
                else if (nameGen == 2){
                    weaponName = coolWeaponNames + " " + weaponCategory;
                }
                else {
                    weaponName = coolWeaponNames + " " + weaponCategory + " " + coolWeaponNames2;
                }

                return new Weapon(weaponName, weight, damage, attackSpeed, isOneHanded, category, weaponCategory);
            case 2: //Armor
                category = ItemCategory.ARMOR;

                armorCategory = ArmorCategory.values()[rand.nextInt(ArmorCategory.values().length)];
                coolArmorNames = CoolArmorNames.values()[rand.nextInt(CoolArmorNames.values().length)];
                String armorName = armorCategory.name() + " " + coolArmorNames;

                int defense = 1 + rand.nextInt(40);

                return new Armor(armorName, weight, category, defense);
            case 3: // consumable
                category = ItemCategory.CONSUMABLE;

                coolConsumabelName = CoolConsumabelName.values()[rand.nextInt(CoolConsumabelName.values().length)];
                String consumableName = coolConsumabelName.name();

                int cdamage = rand.nextInt(20);
                int health = 5 + rand.nextInt(50);

                return new Consumable(consumableName, weight, category, health, cdamage);
            default:
                System.out.println("Invalid choice. Try again!");
                return null;
        }
    }
}

