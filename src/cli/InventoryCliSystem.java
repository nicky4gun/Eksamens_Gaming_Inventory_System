package cli;

import dat.*;
import dat.config.DatabaseConfig;
import logic.InventoryService;
import models.*;
import models.enums.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

public class InventoryCliSystem {

    public static void main(String[] args) {
        DatabaseConfig config = new DatabaseConfig();
        InventoryService service = getService(config);

        Scanner scanner = new Scanner(System.in);

        // Try to establish a connection
        try (Connection conn = config.getConnection()) {
            System.out.println("Connection established: " + conn);
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }

        // Shows player how much space is currently used in their inventory
        System.out.println("Inventory (weight): " + service.getTotalWeightFromDb() + " kg");
        System.out.println("Slots used: " + service.getUsedSlotsFromDb());

        System.out.print("Choose method: ");
        int method = scanner.nextInt();
        scanner.nextLine();

        while (true) {
            switch (method) {
                case 1:
                    handleCreatePlayer(service, scanner);
                    return;
                case 2:
                    handlePickUpItem(service, scanner);
                    return;
                case 0:
                    System.out.println("Exiting Inventory...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again!");
            }
        }
    }

    private static InventoryService getService(DatabaseConfig config) {
        PlayerRepository playerRepository = new PlayerRepository(config);
        // ItemRepository itemRepository = new ItemRepository(config);
        WeaponRepository weaponRepository = new WeaponRepository(config);
        ArmorRepository armorRepository = new ArmorRepository(config);
        ConsumableRepository consumableRepository = new ConsumableRepository(config);

        return new InventoryService(playerRepository, weaponRepository, armorRepository, consumableRepository);
    }

    public static void handleCreatePlayer(InventoryService service, Scanner scanner) {
        System.out.println("Name: ");
        String playerName = scanner.nextLine();

        System.out.println("Credits: ");
        int credits = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Level: ");
        int level = scanner.nextInt();
        scanner.nextLine();

        try {
            service.createPlayer(playerName, credits, level);
            System.out.println("Player added successfully!");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void handlePickUpItem(InventoryService service, Scanner scanner) {
        System.out.println("Choose number of items to pick up: ");
        int numberOfItems = scanner.nextInt();

        for (int i = 0; i < numberOfItems; i++) {
            Item randomItem = createRandomItem();
            System.out.println(randomItem);

            if (randomItem != null) {
                service.addItem(randomItem);
            }
        }

        System.out.println("Item added to inventory!");
    }

    public static Item createRandomItem() {
        Random rand = new Random();

        int choice = 1 + rand.nextInt(3);

        // String name = "item_" + rand.nextInt(1000);
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

                return new Consumable(consumableName, weight, category, cdamage, health);
            default:
                System.out.println("Invalid choice. Try again!");
                return null;
        }
    }
}



