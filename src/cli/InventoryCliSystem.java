package cli;

import dat.*;
import dat.config.DatabaseConfig;
import logic.InventoryService;
import models.*;
import models.enums.*;

import java.sql.Connection;
import java.sql.SQLException;
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

        // Shows player how much space is currently used in their inventory
        System.out.printf("Inventory (weight): %.2f kg%n", service.getTotalWeightFromDb());
        System.out.println("Slots used: " + service.getUsedSlotsFromDb());

        run(service, scanner);
    }

    public static void run(InventoryService service, Scanner scanner) {
        printMenu();

        boolean running = true;

        while (running) {
            System.out.print("Choose action: ");
            int method = scanner.nextInt();
            scanner.nextLine();

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
                default:
                    System.out.println("Invalid choice. Try again!");
            }
        }
    }

    private static void printMenu(){
        System.out.println("\n==== Inventory Actions ====");
        System.out.println("1: createPlayer");
        System.out.println("2: pickupItem");
        System.out.println("3: removeItem");
        System.out.println("4: showIventory");
        System.out.println("0: exit");
    }

    private static void printInventory(InventoryService service) {
        List<Item> inventory = service.findAllItems();

        for (Item item : inventory) {
            System.out.println(item);
        }
    }

    // Handlers for inventory actions
    private static void handleCreatePlayer(InventoryService service, Scanner scanner) {
        System.out.print("Name: ");
        String playerName = scanner.nextLine();

        System.out.print("Credits: ");
        int credits = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Level: ");
        int level = scanner.nextInt();
        scanner.nextLine();

        if (playerName == null || playerName.isBlank()) {System.out.println("Name cannot be empty, Try again!");}

        try {
            service.createPlayer(playerName, credits, level);
            System.out.println("Player added successfully!");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void handleDeleteItem(InventoryService service, Scanner scanner) {
        boolean running = true;

        while (running) {
            System.out.print("Enter item to delete (id) - (0 to exit): ");
            int id = scanner.nextInt();

            if (id < 0) {
                System.out.println("Not a valid id. Try again!");
            } else if (id == 0) {
                System.out.println("Exiting delete-mode...");
                running = false;
            } else {
                service.deleteItemFromInventory(id);
                System.out.println("Item deleted successfully!");
            }
        }
    }

    private static void handlePickUpItem(InventoryService service, Scanner scanner) {
        boolean running = true;

        while (running) {
            System.out.print("Choose number of items to pick up: ");
            int numberOfItems = scanner.nextInt();
            scanner.nextLine();

            for (int i = 0; i < numberOfItems; i++) {

                Item randomItem = service.createRandomItem();

                System.out.println(randomItem);

                if (randomItem != null) {
                    service.addItem(randomItem);
                }
            }

            if (numberOfItems < 0) {
                System.out.println("Invalid number, must be positive or 0.");
            } else {
                System.out.println(numberOfItems + " item(s) added to inventory!");
                running = false;
            }
        }
    }
}



