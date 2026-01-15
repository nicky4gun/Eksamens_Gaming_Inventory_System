package logic;

import dat.*;
import models.*;
import models.enums.*;
import models.exceptions.InventoryFullException;

import java.util.List;
import java.util.Random;

public class InventoryService {
    private final PlayerRepository playerRepository;
    private final WeaponRepository weaponRepository;
    private final ArmorRepository armorRepository;
    private final ConsumableRepository consumableRepository;
    private final InventoryRepository inventoryRepository;
    private final ItemFactory itemFactory;

    private final double MAX_WEIGHT = 50.0;
    private final int MAX_SLOTS =192;
    private final int SLOT_COST = 30;
    private int gold = 0;

    private final Random rand = new Random();

    public InventoryService(PlayerRepository playerRepository, WeaponRepository weaponRepository, ArmorRepository armorRepository, ConsumableRepository consumableRepository, InventoryRepository inventoryRepository, ItemFactory itemFactory) {
        this.playerRepository = playerRepository;
        this.weaponRepository = weaponRepository;
        this.armorRepository = armorRepository;
        this.consumableRepository = consumableRepository;
        this.inventoryRepository = inventoryRepository;
        this.itemFactory = itemFactory;
    }

    // Game logic
    private void checkWeight(Item item) {
        double totalWeight = getTotalWeightFromDb();
        if (totalWeight + item.getWeight() > MAX_WEIGHT) {
            throw new InventoryFullException("Can't add item: inventory would exceed max weight of " + MAX_WEIGHT + " kg");
        }
    }

    private void checkSlotsAvailable() {
        int usedSlots = getUsedSlotsFromDb();
        int availableSlots = playerRepository.getSlots();

        if (usedSlots >= availableSlots) {
            throw new InventoryFullException("Can't add item: no free slots available");
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
        playerRepository.addGold(amount);
    }

    public void addSlots(int slots ){
        if (slots <= 0) return;
        playerRepository.addSlots(slots);
    }

    public boolean spendGold(int amount) {
        if (amount <= 0) return false;
        return playerRepository.subtractGold(amount);
    }

    // Create objects
    public void addItem(Item item) {
        if (tryStacking(item)) return;
        checkWeight(item);
        checkSlotsAvailable();

        switch (item.getItemType()) {
            case WEAPON -> weaponRepository.addItem((Weapon) item);
            case ARMOR -> armorRepository.addItem((Armor) item);
            case CONSUMABLE -> consumableRepository.addItem((Consumable) item);
            default -> throw new IllegalArgumentException("Unknown item type: " + item.getItemType());
        }
    }

    public boolean tryStacking(Item item) {
        if (!(item instanceof  Consumable consumable)) return false;

        for (Consumable c : findAllConsumables()) {
            if (c.getName().equals(consumable.getName()) && c.getStackable()) {
                c.setQuantity(c.getQuantity() + consumable.getQuantity());

                consumableRepository.stackConsumable(c.getName(), c.getQuantity());
                System.out.println("Stacked " + consumable.getQuantity() + "x " + c.getName());

                return true;
            }
        }

        return false;
    }

    // Example on method-overloading
    public void addItem(Weapon weapon) {
        checkWeight(weapon);
        checkSlotsAvailable();

        weaponRepository.addItem(weapon);
    }

    public void addItem(Armor armor) {
        checkWeight(armor);
        checkSlotsAvailable();

        armorRepository.addItem(armor);
    }

    public void addItem(Consumable consumable) {
        checkWeight(consumable);
        checkSlotsAvailable();

        consumableRepository.addItem(consumable);
    }

    public void deleteItemFromInventory(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid item id: " + id);
        }

        boolean deleted = inventoryRepository.deleteItemById(id);

        if (!deleted) {
            throw new IllegalArgumentException("Item with id " + id + " not found");
        }

        gold += rand.nextInt(16)+5;
        addGold(gold);
        System.out.println("You gained " + gold + " gold!");
    }

    // Item creation through factory
    public Item createRandomItem() {
        Item item = itemFactory.createRandomItem();
        addGold(rand.nextInt(20) + 1);
        return item;
    }

    // Searching
    public List<Weapon> findAllWeapons() {
        List<Weapon> items = inventoryRepository.findAllWeapons();

        if (items == null || items.isEmpty()) {
            System.out.println("You don't have any weapons");
        }

        return items;
    }

    public List<Armor> findAllArmor() {
        List<Armor> items = inventoryRepository.findAllArmor();

        if (items == null || items.isEmpty()) {
            System.out.println("You don't have any armor");
        }

        return items;
    }

    public List<Consumable> findAllConsumables() {
        List<Consumable> items = inventoryRepository.findAllConsumables();

        if (items == null || items.isEmpty()) {
            System.out.println("You don't have any consumables");
        }

        return items;
    }

    // Sorting
    public List<Item> findAllItemsBySortedByName() {
        List<Item> items = inventoryRepository.findAllItemsSortedByName();

        if (items == null || items.isEmpty()) {
            System.out.println("Your Inventory is Empty!");
        }

        return items;
    }

    public List<Item> findAllItemsBySortedById() {
        List<Item> items = inventoryRepository.findAllItemsSortedById();

        if (items == null || items.isEmpty()) {
            System.out.println("Your Inventory is Empty!");
        }

        return items;
    }

    public List<Item> findAllItemsBySortedByWeight() {
        List<Item> items = inventoryRepository.findAllItemsSortedByWeight();

        if (items == null || items.isEmpty()) {
            System.out.println("Your Inventory is Empty!");
        }

        return items;
    }

    public List<Item> findAllItemsBySortedByType() {
        List<Item> items = inventoryRepository.findAllItemsSortedByType();

        if (items == null || items.isEmpty()) {
            System.out.println("Your Inventory is Empty!");
        }

        return items;
    }

    public List<Weapon> findAllItemsBySortedByCategory() {
        List<Weapon> items = inventoryRepository.findAllWeaponsSortedCategory();

        if (items == null || items.isEmpty()) {
            System.out.println("You don't have any weapons. Unable to sort by category.");
        }

        return items;
    }

    public List<Armor> findAllItemsBySortedByDefense(){
        List<Armor> items = inventoryRepository.findAllArmorSortedCategory();

        if (items == null || items.isEmpty()) {
            System.out.println("You don't have any armor. Unable to sort by defense.");
        }

        return items;
    }

    // Getters
    public double getTotalWeightFromDb() {
        List<Item> items = inventoryRepository.findAllItems();
        double totalWeight = 0.0;

        for (Item item : items) {
            totalWeight += item.getWeight();
        }

        return totalWeight;
    }

    public int getUsedSlotsFromDb() {
        List<Item> items = inventoryRepository.findAllItems();
        int counter = 0;

        for (int i = 0; i < items.size(); i++) {
            counter++;
        }

        return counter;
    }

    public int getGold() {
        return playerRepository.getGold();
    }

    public int getSlotsAvailable() {
        return playerRepository.getSlots() - getUsedSlotsFromDb();
    }
}