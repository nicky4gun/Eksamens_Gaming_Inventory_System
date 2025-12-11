package logic;

import dat.*;
import models.*;
import models.enums.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InventoryService {
    private final PlayerRepository playerRepository;
    private final WeaponRepository weaponRepository;
    private final ArmorRepository armorRepository;
    private final ConsumableRepository consumableRepository;

    private final int MAX_WEIGHT = 50;
    private final int MAX_SLOTS =192;
    private int unlockedSlots = 32;

    private final Random rand = new Random();

    public InventoryService(PlayerRepository playerRepository, WeaponRepository weaponRepository, ArmorRepository armorRepository, ConsumableRepository consumableRepository) {
        this.playerRepository = playerRepository;
        this.weaponRepository = weaponRepository;
        this.armorRepository = armorRepository;
        this.consumableRepository = consumableRepository;
    }

    // Game logic
    private void checkWeight(Item item) {
        double totalWeight = getTotalWeightFromDb();
        if (totalWeight + item.getWeight() > MAX_WEIGHT) {
            throw new IllegalStateException("Can't add item: inventory exceeds max weight of " + MAX_WEIGHT);
        };
    }

    private void checkSlotsAvailable() {
        int usedSlots = getUsedSlotsFromDb();
        if (usedSlots + 1 > unlockedSlots) {
            throw new IllegalStateException("Can't add item: no free slots available");
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
    }

    public void deleteItemFromInventory(int id ) {
        weaponRepository.deleteItemById(id);
        armorRepository.deleteItemById(id);
        consumableRepository.deleteItemById(id);
    }

    public List<Item> findAllItems() {
        List<Item> items = new ArrayList<>();

        items.addAll(weaponRepository.readAllWeapons());
        items.addAll(armorRepository.readAllArmor());
        items.addAll(consumableRepository.readAllConsumables());

        if (items.isEmpty()) {
            System.out.println("Your Inventory is Empty!");
        }

        return items;
    }

    public Item createRandomItem() {
        int choice = 1 + rand.nextInt(3);

        ItemCategory category;
        WeaponCategory weaponCategory;
        CoolWeaponNames coolWeaponNames;
        CoolWeaponNames coolWeaponNames2;
        ArmorCategory armorCategory;
        CoolArmorNames coolArmorNames;
        CoolConsumabelName coolConsumabelName;
        ConsumableCategory consumableCategory;
        WeaponType weaponType;

        switch (choice) {
            case 1: // Weapon
                category = ItemCategory.WEAPON;
                double weight = 0.5 + (5 * rand.nextDouble());
                int damage = 5 + rand.nextInt(30);
                double attackSpeed = 0.5 + rand.nextDouble() * 2;

                weaponType = WeaponType.values()[rand.nextInt(WeaponType.values().length)];
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

                return new Weapon(weaponName, weight, damage, attackSpeed, weaponType, category, weaponCategory);
            case 2: //Armor
                category = ItemCategory.ARMOR;
                weight = 0.5 + (5 * rand.nextDouble());
                armorCategory = ArmorCategory.values()[rand.nextInt(ArmorCategory.values().length)];
                coolArmorNames = CoolArmorNames.values()[rand.nextInt(CoolArmorNames.values().length)];
                String armorName = armorCategory.name() + " " + coolArmorNames;

                int defense = 1 + rand.nextInt(40);

                return new Armor(armorName, weight, category, defense);
            case 3: // consumable
                category = ItemCategory.CONSUMABLE;

                coolConsumabelName = CoolConsumabelName.values()[rand.nextInt(CoolConsumabelName.values().length)];
                consumableCategory = ConsumableCategory.values()[rand.nextInt(ConsumableCategory.values().length)];
                String consumableName = coolConsumabelName.name();

                weight = 0.5;
                int health = 0;
                int cdamage = 0;
                boolean  stackable = false;
                int quantity = 0;

                if (consumableCategory == ConsumableCategory.HEALTH_POTION) {
                    health = 50;
                } else if (consumableCategory == ConsumableCategory.DAMAGE_POTION) {
                    cdamage = 20;
                } else if (consumableCategory == consumableCategory.ARROWS || consumableCategory == consumableCategory.BOLTS ) {
                    stackable = true;
                    quantity = 12;
                    consumableName = "arrows";
                }

                return new Consumable(consumableName, weight, category, health, cdamage, consumableCategory, stackable, quantity);
            default:
                System.out.println("Invalid choice. Try again!");
                return null;
        }
    }
}

