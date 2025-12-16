package cli;

import dat.*;
import dat.config.DatabaseConfig;
import logic.InventoryService;
import logic.Sorting;
import models.*;
import models.enums.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class InventoryCliSystem {
    public static void main(String[] args) {
        DatabaseConfig config = new DatabaseConfig();

        PlayerRepository playerRepository = new PlayerRepository(config);
        WeaponRepository weaponRepository = new WeaponRepository(config);
        ArmorRepository armorRepository = new ArmorRepository(config);
        ConsumableRepository consumableRepository = new ConsumableRepository(config);
        Sorting sort = new Sorting();
        InventoryRepository inventoryRepository = new InventoryRepository(weaponRepository, armorRepository, consumableRepository,sort);

        InventoryService service = new InventoryService(playerRepository, weaponRepository, armorRepository, consumableRepository, inventoryRepository);

        Scanner scanner = new Scanner(System.in);

        // Try to establish a connection
        try (Connection conn = config.getConnection()) {
            System.out.println("Connection established: " + conn);
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }

        System.out.println("=============================================");
        System.out.println("Welcome to your Legend of CodeCraft inventory");
        System.out.println("=============================================");

        printInventoryStats(service);
        run(service, scanner);
    }

    public static void run(InventoryService service, Scanner scanner) {
        printMenu();
        boolean running = true;

        while (running) {
            System.out.print("Choose action: ");

            int method;
            try {
                method = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine();
                continue;
            }

            switch (method) {
                case 1 -> handlePickUpItem(service);
                case 2 -> handleDeleteItem(service, scanner);
                case 3 -> handleShowInventory(scanner, service);
                case 4 -> handleSearchItem(service, scanner);
                case 5 -> handleUnlockSlots(service, scanner);
                case 0 -> {System.out.println("Exiting Inventory..."); running = false;}
                default -> System.out.println("Invalid choice. Try again!");
            }
        }
    }

    private static void printInventoryStats(InventoryService service) {
        System.out.printf("Inventory (weight): %.2f kg%n", service.getTotalWeightFromDb());
        System.out.println("Slots used: " + service.getUsedSlotsFromDb());
        System.out.println("Available Inventory Slots: " + service.getSlotsAvailable());
        System.out.println("Collected gold: " + service.getGold());
    }

    private static void printMenu() {
        System.out.println("""
               
                ==== Inventory Actions ====
                1. Add new Player
                2. Collect Loot
                3. Sell Items
                4. Show Inventory
                5. Search in Inventory
                0. Exit program
                """);
    }

    private static void printInventoryMenu() {
        System.out.println("""
                
                ==== Sort Inventory By ====
                1. Name
                2. Id
                3. Weight
                4. Type
                5. Category (Weapons)
                6. Defense (Armor)
                0. Exit
                """);
    }

    // Printing methods for inventory
    private static void printInventoryByName(InventoryService service) {
        List<Item> inventory = service.findAllItemsBySortedByName();

        for (Item item : inventory) {
            System.out.println(item);
        }
    }

    private static void printInventoryById(InventoryService service) {
        List<Item> inventory = service.findAllItemsBySortedById();

        for (Item item : inventory) {
            System.out.println(item);
        }
    }

    private static void printInventoryByWeight(InventoryService service) {
        List<Item> inventory = service.findAllItemsBySortedByWeight();

        for (Item item : inventory) {
            System.out.println(item);
        }
    }

    private static void printInventoryByType(InventoryService service) {
        List<Item> inventory = service.findAllItemsBySortedByType();

        for (Item item : inventory) {
            System.out.println(item);
        }
    }

    private static void printInventoryByWeaponCategory(InventoryService service) {
        List<Weapon> inventory = service.findAllItemsBySortedByCategory();
        for (Item item : inventory) {
            System.out.println(item);
        }
    }

    private static void printInventoryByDefense(InventoryService service) {
        List<Armor> inventory = service.findAllItemsBySortedByDefense();

        for (Armor armor : inventory) {
            System.out.println(armor);
        }
    }

    private static void printWeapons(InventoryService service) {
        List<Weapon> inventory = service.findAllWeapons();

        for (Weapon weapon : inventory) {
            System.out.println(weapon);
        }
    }

    private static void printArmor(InventoryService service) {
        List<Armor> inventory = service.findAllArmor();

        for (Armor armor : inventory) {
            System.out.println(armor);
        }
    }

    private static void printConsumable(InventoryService service) {
        List<Consumable> inventory = service.findAllConsumables();

        for (Consumable consumable : inventory) {
            System.out.println(consumable);
        }
    }

    // Handlers for inventory actions
    private static void handleDeleteItem(InventoryService service, Scanner scanner) {
        boolean running = true;

        while (running) {
            System.out.print("Enter item to delete (id) - (0 to exit): ");

            int id;
            try {
                id = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine();
                continue;
            }

            if (id < 0) {
                System.out.println("Not a valid id. Try again!");
            } else if (id == 0) {
                printInventoryStats(service);
                System.out.println("\nExiting delete-mode...");
                running = false;
            } else {
                try {
                    service.deleteItemFromInventory(id);
                    System.out.println("Item deleted successfully!");
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }

    private static void handlePickUpItem(InventoryService service ) {
        System.out.print("Choose number of items to pick up: ");
        Random random = new Random();
        int numberOfItems = random.nextInt(5);
        System.out.println("You found " +  numberOfItems + " items to pick up!");

        int successfullyAdded = 0;

        for (int i = 0; i < numberOfItems; i++) {

            Item randomItem = service.createRandomItem();
            System.out.println(randomItem);

            try {
                service.addItem(randomItem);
                successfullyAdded++;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }
        }

        System.out.println(successfullyAdded + " item(s) added to inventory!");
    }

    public static void handleShowInventory(Scanner scanner, InventoryService service) {
        printInventoryMenu();

        System.out.println(); // Prints empty line

        printInventoryStats(service);

        System.out.println(); // Prints empty line

        printInventoryById(service);
        boolean running = true;

        while (running) {
            System.out.print("\nChoose Inventory sorting method: ");

            int method = 0;
            try {
                method = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine();
            }

            switch (method) {
                case 1 -> printInventoryByName(service);
                case 2 -> printInventoryById(service);
                case 3 -> printInventoryByWeight(service);
                case 4 -> printInventoryByType(service);
                case 5 -> printInventoryByWeaponCategory(service);
                case 6 -> printInventoryByDefense(service);
                case 0 -> {System.out.println("Exiting Inventory..."); running = false;}
                default -> System.out.println("Invalid choice. Try again!");
            }
        }
    }

    public static void handleSearchItem(InventoryService service, Scanner scanner) {
        System.out.print("\nShat item type would you like to search for?: ");
        String search = scanner.nextLine().toLowerCase();

        switch (search) {
            case "weapon" -> printWeapons(service);
            case "armor" -> printArmor(service);
            case "consumable" -> printConsumable(service);
            default -> System.out.println("Invalid choice. Try again!");
        }
    }

    public static void handleUnlockSlots(InventoryService service, Scanner scanner) {
        System.out.print("How many slots would you like to unlock: ");
        int slots = scanner.nextInt();
        scanner.nextLine();

        service.unlockSlots(slots);
        int totalSlots = service.getSlotsAvailable();
        System.out.println("Successfully unlocked: " + slots + " slots!");
        System.out.println("You now have " + totalSlots + " unlocked slots.");
    }
}



