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
    private int SLOT_COST = 30;
    private int gold = 0;

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
        if (usedSlots + 1 > getSlotsAvailable()) {
            throw new IllegalStateException("Can't add item: no free slots available");
        }
    }

    public void unlockSlots(int amount) {
        if (amount == 0) return;
        int currentSlots = getSlotsAvailable();

        if (currentSlots >= MAX_SLOTS) {
            throw new IllegalStateException("Can't add item: no free slots available");
        }

        int slotsToAdd = Math.min(amount, MAX_SLOTS - currentSlots);
        int totalCost = SLOT_COST * amount;

        if (!spendGold(totalCost)) {
            throw new IllegalStateException("Not enough gold to unlock slots");
        }

        addSlots(slotsToAdd);
    }

    public void addGold(int amount) {
        if (amount <= 0) return;

        try {
            playerRepository.addGold(amount);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add gold.");
        }
    }
    public void addSlots (int slots ){
        if (slots <= 0) return;
        try{
            playerRepository.addSlots(slots);
        }catch (Exception e){
            throw new RuntimeException("Failed to add slots.");
        }
    }


    public boolean spendGold(int amount) {
        if (amount <= 0) return false;

        int currentGold = getGold();
        if (currentGold < amount) return false;

        try {
            playerRepository.subtractGold(amount);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Failed to subtract gold.");
        }
    }

    // Create objects
    public void addItem(Item item) {
        checkWeight(item);
        checkSlotsAvailable();

        switch (item.getItemType()) {
            case WEAPON -> weaponRepository.addItem((Weapon) item);
            case ARMOR -> armorRepository.addItem((Armor) item);
            case CONSUMABLE -> consumableRepository.addItem((Consumable) item);
            default -> throw new IllegalArgumentException("Unknown item type: " + item.getItemType());
        }
    }

    public void deleteItemFromInventory(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid item id: " + id);
        }

        boolean deleted = weaponRepository.deleteItemById(id);
        gold += rand.nextInt(16)+5;

        if (!deleted) {
            armorRepository.deleteItemById(id);
            gold += rand.nextInt(21)+5;
        }

        if (!deleted) {
            consumableRepository.deleteItemById(id);
            gold += rand.nextInt(11)+5;
        }

        if (!deleted) {
            throw new IllegalArgumentException("Item with id " + id + " not found");
        }

        addGold(gold);
        System.out.println("You gained " + gold + " gold!");
    }

    // Item creation methods
    public Item createRandomItem() {
        int choice = 1 + rand.nextInt(3);

        return switch (choice) {
            case 1 -> createRandomWeapon();
            case 2 -> createRandomArmor();
            case 3 -> createRandomConsumable();
            default -> throw new IllegalArgumentException("Unexpected random value: " + choice);
        };
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
        addGold(rand.nextInt(20) + 1);

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
        addGold(rand.nextInt(20) + 1);

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

        addGold(rand.nextInt(20) + 1);

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
    public List<Weapon> findAllWeapons() {
        List<Weapon> items = inventoryRepository.findAllWeapons();

        if (items.isEmpty()) {
            System.out.println("You don't have any weapons");
        }

        return items;
    }

    public List<Armor> findAllArmor() {
        List<Armor> items = inventoryRepository.findAllArmor();

        if (items.isEmpty()) {
            System.out.println("You don't have any armor");
        }

        return items;
    }

    public List<Consumable> findAllConsumables() {
        List<Consumable> items = inventoryRepository.findAllConsumables();

        if (items.isEmpty()) {
            System.out.println("You don't have any consumables");
        }

        return items;
    }

    // Sorting
    public List<Item> findAllItemsBySortedByName() {
        List<Item> items = inventoryRepository.findAllItemsSortedByName();

        if (items.isEmpty()) {
            System.out.println("Your Inventory is Empty!");
        }

        return items;
    }

    public List<Item> findAllItemsBySortedById() {
        List<Item> items = inventoryRepository.findAllItemsSortedById();

        if (items.isEmpty()) {
            System.out.println("Your Inventory is Empty!");
        }

        return items;
    }

    public List<Item> findAllItemsBySortedByWeight() {
        List<Item> items = inventoryRepository.findAllItemsSortedByWeight();

        if (items.isEmpty()) {
            System.out.println("Your Inventory is Empty!");
        }

        return items;
    }

    public List<Item> findAllItemsBySortedByType() {
        List<Item> items = inventoryRepository.findAllItemsSortedByType();

        if (items.isEmpty()) {
            System.out.println("Your Inventory is Empty!");
        }

        return items;
    }

    public List<Weapon> findAllItemsBySortedByCategory() {
        List<Weapon> items = inventoryRepository.findAllWeaponsSortedCategory();

        if (items.isEmpty()) {
            System.out.println("You don't have any weapons. Unable to sort by category.");
        }

        return items;
    }

    public List<Armor> findAllItemsBySortedByDefense(){
        List<Armor> items = inventoryRepository.findAllArmorSortedCategory();

        if (items.isEmpty()) {
            System.out.println("You don't have any armor. Unable to sort by defense.");
        }

        return items;
    }

    // Getters
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

    public int getGold() {
        try {
            return playerRepository.getGold();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get gold.");
        }
    }

    public int getSlotsAvailable() {
        return playerRepository.getSlots();
    }
}

