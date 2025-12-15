package cli;

import dat.*;
import dat.config.DatabaseConfig;
import logic.InventoryService;
import models.*;
import models.enums.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class InventoryCliSystem {
    public static void main(String[] args) {
        DatabaseConfig config = new DatabaseConfig();

        PlayerRepository playerRepository = new PlayerRepository(config);
        WeaponRepository weaponRepository = new WeaponRepository(config);
        ArmorRepository armorRepository = new ArmorRepository(config);
        ConsumableRepository consumableRepository = new ConsumableRepository(config);
        InventoryRepository inventoryRepository = new InventoryRepository(weaponRepository, armorRepository, consumableRepository);

        InventoryService service = new InventoryService(playerRepository, weaponRepository, armorRepository, consumableRepository, inventoryRepository);

        Scanner scanner = new Scanner(System.in);

        // Try to establish a connection
        try (Connection conn = config.getConnection()) {
            System.out.println("Connection established: " + conn);
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }

        printInventoryStats(service);


        System.out.println(); // Print empty line

        run(service, scanner, inventoryRepository);
    }

    public static void run(InventoryService service, Scanner scanner, InventoryRepository inventoryRepository) {
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
                case 1:
                    handleCreatePlayer(service, scanner);
                    break;
                case 2:
                    handlePickUpItem(service, scanner);
                    break;
                case 3:
                    printInventoryById(service);
                    handleDeleteItem(service, scanner);
                    break;
                case 4:
                    printInventoryById(service);
                    break;
                case 5:
                    handleShowInventory(scanner, service);
                    break;
                case 6:
                    handleSearchItem(service, scanner);
                    break;
                case 0:
                    System.out.println("Exiting Inventory...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again!");
            }
        }
    }

    private static void printInventoryStats(InventoryService service) {
        // Shows player how much space is currently used in their inventory
        System.out.printf("Inventory (weight): %.2f kg%n", service.getTotalWeightFromDb());
        System.out.println("Slots used: " + service.getUsedSlotsFromDb());
    }

    private static void printMenu() {
        System.out.println("""
                \n==== Inventory Actions ====
                1. Add new Player
                2. Pick up Item
                3. Remove item from Inventory
                4. Show Inventory
                5. Show Inventory By name (Bubble)
                6. search in inventory
                0. Exit program
                """);
    }

    private static void printInventoryMenu() {
        System.out.println("""
                \n==== Show Inventory By ====
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
        List<Weapon> intventory = service.findAllWeapons();

        for (Weapon wepon : intventory) {
            System.out.println(wepon);
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
    private static void handleCreatePlayer(InventoryService service, Scanner scanner) {
        try {
            System.out.print("Name: ");
            String playerName = scanner.nextLine();

            if (playerName == null || playerName.isBlank()) {
                System.out.println("Name cannot be empty, Try again!");
                return;
            }

            System.out.print("Credits: ");
            int credits = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Level: ");
            int level = scanner.nextInt();
            scanner.nextLine();

            if (credits < 0 || level < 1 || level > 100) {
                System.out.println("Invalid values. Unable to create Player.");
                return;
            }

            service.createPlayer(playerName, credits, level);
            System.out.println("Player added successfully!");

        } catch (InputMismatchException e) {
            System.out.println("Invalid Input! Please enter a number.");
            scanner.nextLine();
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

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

    private static void handlePickUpItem(InventoryService service, Scanner scanner) {
        System.out.print("Choose number of items to pick up: ");

        int numberOfItems;
        try {
            numberOfItems = scanner.nextInt();
            scanner.nextLine();

            if (numberOfItems <= 0) {
                System.out.println("Please enter a positive number.");
                return;
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a number.");
            scanner.nextLine();
            return;
        }

        int succesfullyAdded = 0;

        for (int i = 0; i < numberOfItems; i++) {

            Item randomItem = service.createRandomItem();
            System.out.println(randomItem);

            try {
                service.addItem(randomItem);
                succesfullyAdded++;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }
        }

        System.out.println(succesfullyAdded + " item(s) added to inventory!");
    }

    public static void handleShowInventory(Scanner scanner, InventoryService inventoryService) {
        printInventoryMenu();
        boolean running = true;

        while (running) {
            System.out.print("Choose Inventory sorting method: ");

            int method = 0;
            try {
                method = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine();
            }

            switch (method) {
                case 1:
                    printInventoryByName(inventoryService);
                    break;
                case 2:
                    printInventoryById(inventoryService);
                    break;
                case 3:
                    printInventoryByWeight(inventoryService);
                    break;
                case 4:
                    printInventoryByType(inventoryService);
                    break;
                case 5:
                    printInventoryByWeaponCategory(inventoryService);
                    break;
                case 6:
                    printInventoryByDefense(inventoryService);
                    break;
                case 0:
                    System.out.println("Exiting Inventory...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again!");
            }
        }
    }

    public static void handleSearchItem(InventoryService service, Scanner scanner) {
        System.out.print("Shat item type would you like to search for?: ");
        String search = scanner.nextLine().toLowerCase();

        switch (search) {
            case "weapon":
                printWeapons(service);

                break;
            case "armor":
                printArmor(service);
                break;
            case "consumable":
                printConsumable(service);
                break;
            default:
                System.out.println("Invalid choice. Try again!");
        }
    }
}



