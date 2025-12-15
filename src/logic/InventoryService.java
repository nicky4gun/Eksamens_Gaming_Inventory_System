package logic;

import dat.*;
import models.*;
import models.enums.*;

import java.util.List;
import java.util.Random;

public class InventoryService {
    private final PlayerRepository playerRepository;
    private final WeaponRepository weaponRepository;
    private final ArmorRepository armorRepository;
    private final ConsumableRepository consumableRepository;
    private final InventoryRepository inventoryRepository;

    private final int MAX_WEIGHT = 50;
    private final int MAX_SLOTS =192;
    private int unlockedSlots = 32;

    private final Random rand = new Random();

    public InventoryService(PlayerRepository playerRepository, WeaponRepository weaponRepository, ArmorRepository armorRepository, ConsumableRepository consumableRepository, InventoryRepository inventoryRepository) {
        this.playerRepository = playerRepository;
        this.weaponRepository = weaponRepository;
        this.armorRepository = armorRepository;
        this.consumableRepository = consumableRepository;
        this.inventoryRepository = inventoryRepository;
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
        if (playerName == null || playerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be null or empty");
        }

        if (credits < 0) {
            throw new IllegalArgumentException("Credits cannot be negative");
        }

        if (level < 1 || level > 100) {
            throw new IllegalArgumentException("Level must be between 1 and 100");
        }

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
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid item id: " + id);
        }

        boolean deleted = weaponRepository.deleteItemById(id);

        if (!deleted) {
            armorRepository.deleteItemById(id);
        }

        if (!deleted) {
            consumableRepository.deleteItemById(id);
        }

        if (!deleted) {
            throw new IllegalArgumentException("Item with id " + id + " not found");
        }
    }

    // Item creation methods
    public Item createRandomItem() {
        int choice = 1 + rand.nextInt(3);

        switch (choice) {
            case 1:
                return createRandomWeapon();
            case 2:
                return createRandomArmor();
            case 3:
                return createRandomConsumable();
            default:
                throw new IllegalArgumentException("Unexpected random value: " + choice);
        }
    }

    private Weapon createRandomWeapon() {
        ItemCategory category = ItemCategory.WEAPON;
        double weight = 0.5 + (5 * rand.nextDouble());
        int damage = 5 + rand.nextInt(30);
        double attackSpeed = 0.5 + rand.nextDouble() * 2;

        WeaponHandling weaponHandling = WeaponHandling.values()[rand.nextInt(WeaponHandling.values().length)];
        WeaponType weaponType = WeaponType.values()[rand.nextInt(WeaponType.values().length)];
        CoolWeaponNames coolWeaponNames = CoolWeaponNames.values()[rand.nextInt(CoolWeaponNames.values().length)];
        CoolWeaponNames coolWeaponNames2 = CoolWeaponNames.values()[rand.nextInt(CoolWeaponNames.values().length)];

        int nameGen = rand.nextInt(3) + 1;
        String weaponName;

        if (nameGen == 1) {
            weaponName = weaponType + " " + coolWeaponNames;
        }
        else if (nameGen == 2){
            weaponName = coolWeaponNames + " " + weaponType;
        }
        else {
            weaponName = coolWeaponNames + " " + weaponType + " " + coolWeaponNames2;
        }

        return new Weapon(weaponName, weight, damage, attackSpeed, weaponHandling, category, weaponType);
    }

    private Armor createRandomArmor() {
        ItemCategory category = ItemCategory.ARMOR;
        double weight = 0.5 + (5 * rand.nextDouble());
        ArmorCategory armorCategory = ArmorCategory.values()[rand.nextInt(ArmorCategory.values().length)];
        CoolArmorNames coolArmorNames = CoolArmorNames.values()[rand.nextInt(CoolArmorNames.values().length)];
        String armorName = armorCategory.name() + " " + coolArmorNames;

        int defense = 1 + rand.nextInt(40);

        return new Armor(armorName, weight, category, defense);
    }

    private Consumable createRandomConsumable() {
        ItemCategory category = ItemCategory.CONSUMABLE;

        CoolConsumabelName coolConsumableName = CoolConsumabelName.values()[rand.nextInt(CoolConsumabelName.values().length)];
        ConsumableCategory consumableCategory = ConsumableCategory.values()[rand.nextInt(ConsumableCategory.values().length)];
        String consumableName = coolConsumableName.name();

        double weight = 0.5;
        int health = 0;
        int cdamage = 0;
        boolean  stackable = false;
        int quantity = 0;

        if (consumableCategory == ConsumableCategory.HEALTH_POTION) {
            health = 50;
        } else if (consumableCategory == ConsumableCategory.DAMAGE_POTION) {
            cdamage = 20;
        } else if (consumableCategory == ConsumableCategory.ARROWS || consumableCategory == ConsumableCategory.BOLTS ) {
            stackable = true;
            quantity = 12;
        }

        return new Consumable(consumableName, weight, category, health, cdamage, consumableCategory, stackable, quantity);
    }

    // Searching
    public List<Item> findAllItems() {
        List<Item> items = inventoryRepository.findAllItems();

        if (items.isEmpty()) {
            System.out.println("Your Inventory is Empty!");
        }

        return items;
    }

    public List<Weapon> findAllWeapons() {
        return inventoryRepository.findAllWeapons();
    }

    public List<Armor> findAllArmor() {
        return inventoryRepository.findAllArmor();
    }

    public List<Consumable> findAllConsumables() {
        return inventoryRepository.findAllConsumables();
    }

    // Sorting
    public List<Item> findAllItemsBySortedByName() {
        return inventoryRepository.findAllItemsSortedByName();
    }

    public List<Item> findAllItemsBySortedById() {
        return inventoryRepository.findAllItemsSortedById();
    }

    public List<Item> findAllItemsBySortedByWeight() {
        return inventoryRepository.findAllItemsSortedByWeight();
    }

    public List<Item> findAllItemsBySortedByType() {
        return inventoryRepository.findAllItemsSortedByType();
    }

    public List<Weapon> findAllItemsBySortedByCategory() {
        return inventoryRepository.findAllWeaponsSortedCategory();
    }

    public List<Armor> findAllItemsBySortedByDefense(){
        return inventoryRepository.findAllArmorSortedCategory();
    }

}

