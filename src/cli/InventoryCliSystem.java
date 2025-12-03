package cli;

import dat.ItemRepository;
import dat.PlayerRepository;
import dat.config.DatabaseConfig;
import logic.InventoryService;
import models.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class InventoryCliSystem {

    public static void main(String[] args) {
        DatabaseConfig config = new DatabaseConfig();

        PlayerRepository playerRepository = new PlayerRepository(config);
        ItemRepository itemRepository = new ItemRepository(config);

        InventoryService service = new InventoryService(playerRepository, itemRepository);

        Scanner scanner = new Scanner(System.in);

        // Try to establish a connection
        try (Connection conn = config.getConnection()) {
            System.out.println("Connection established: " + conn);
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }

        System.out.print("Choose method: ");
        int method = scanner.nextInt();

        while (method != 0) {
            switch (method) {
                case 1:
                    handleCreatePlayer(service, scanner);
                    break;

                case 2:
                    handleCreateItem(service, scanner);
                    break;
            }
        }

    }

    public static void handleCreatePlayer(InventoryService service, Scanner scanner) {
        System.out.println("Playername: ");
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
            e.getMessage();
        }
    }

    public static void handleCreateItem(InventoryService service, Scanner scanner) {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        scanner.nextLine();
        System.out.print("Weight: ");
        double weight = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Max stack size: ");
        int maxStack = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Category: ");
        String category = scanner.nextLine();


        try {
            service.createItem(name, weight, maxStack, category);
            System.out.println("Item added successfully!");
        } catch (RuntimeException e) {
            e.getMessage();
        }
    }
}


