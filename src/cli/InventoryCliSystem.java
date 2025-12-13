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

        InventoryService service = new InventoryService(playerRepository, weaponRepository, armorRepository, consumableRepository);

        Scanner scanner = new Scanner(System.in);

        // Try to establish a connection
        try (Connection conn = config.getConnection()) {
            System.out.println("Connection established: " + conn);
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }

        printInventoryStats(service);

        System.out.println(); // Print empty line

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
                case 1:
                    handleCreatePlayer(service, scanner);
                    break;
                case 2:
                    handlePickUpItem(service, scanner);
                    break;
                case 3:
                    printInventory(service);
                    handleDeleteItem(service, scanner);
                    break;
                case 4:
                    printInventory(service);
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


    private static void printMenu(){
        System.out.println("\n==== Inventory Actions ====");
        System.out.println("1: Add new Player");
        System.out.println("2: Pick up Item");
        System.out.println("3: Remove item from Inventory");
        System.out.println("4: Show Inventory");
        System.out.println("0: Exit");
    }

    private static void printInventory(InventoryService service) {
        List<Item> inventory = service.findAllItems();

        printInventoryStats(service);

        for (Item item : inventory) {
            System.out.println(item);
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
}



