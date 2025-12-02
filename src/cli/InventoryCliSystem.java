package cli;


import dat.ItemRepository;
import dat.PlayerRepository;
import dat.config.DatabaseConfig;
import logic.InventoryService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class InventoryCliSystem {

    public static void main(String[] args) {
        DatabaseConfig config = new DatabaseConfig();

        PlayerRepository playerRepository = new PlayerRepository(config);
        ItemRepository itemRepository = new ItemRepository();

        InventoryService service = new InventoryService(playerRepository, itemRepository);

        Scanner scanner = new Scanner(System.in);

        // Try to establish a connection
        try (Connection conn = config.getConnection()) {
            System.out.println("Connection established: " + conn);
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }

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
            System.out.println("Error");
        }
    }
}
