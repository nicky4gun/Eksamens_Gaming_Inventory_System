package cli;

import dat.*;
import dat.config.DatabaseConfig;
import logic.InventoryService;
import models.Armor;
import models.Consumeable;
import models.Item;
import models.Weapon;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class InventoryCliSystem {

    public static void main(String[] args) {
        DatabaseConfig config = new DatabaseConfig();

        PlayerRepository playerRepository = new PlayerRepository(config);
        ItemRepository itemRepository = new ItemRepository(config);
        WeaponRepository weaponRepository = new WeaponRepository(config);
        ArmorRepository armorRepository = new ArmorRepository(config);
        ConsumeableRepository consumableRepository = new ConsumeableRepository(config);

        InventoryService service = new InventoryService(playerRepository, itemRepository, weaponRepository, armorRepository, consumableRepository);

        Scanner scanner = new Scanner(System.in);

        // Try to establish a connection
        try (Connection conn = config.getConnection()) {
            System.out.println("Connection established: " + conn);
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }

        System.out.print("Choose method: ");
        int method = scanner.nextInt();


        while (true) {
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
        while (true) {
            System.out.println("chose a category: ");
            System.out.println("1: weapon");
            System.out.println("2: armor");
            System.out.println("3: consumable");
            System.out.println("4: exit");
            System.out.println("Choose item: ");


            int choice = scanner.nextInt();
            scanner.nextLine();
            if(choice == 4) {return;}
            Item item = createItem(choice, scanner);
            service.addItem(item);

            System.out.println("Item added successfully!");
        }
    }


    private static Item createItem(int choice, Scanner scanner) {
        System.out.println("Choose name: ");
        String name = scanner.nextLine();

        System.out.println("Choose weight (Double): ");
        double weight = scanner.nextDouble();
        scanner.nextLine();

        switch (choice) {
            case 1: // eapon
                String category = "weapon";
                System.out.println("damage (int): ");
                int damage = scanner.nextInt();
                scanner.nextLine();

                System.out.println("attackSpeed (Double): ");
                double attackSpeed = scanner.nextDouble();
                scanner.nextLine();

                System.out.println("isOneHanded (TRUE or FALSE): ");
                boolean isOneHanded = scanner.nextBoolean();
                scanner.nextLine();

                return new Weapon(name, weight, damage, attackSpeed, isOneHanded, category);

            case 2: //Armor
                 category = "armor";
                System.out.println("defense (int): ");
                int defense = scanner.nextInt();
                scanner.nextLine();

                return new Armor(name, weight, category, defense);

            case 3:
                category = "consumable";
                System.out.println("damage (int); ");
                damage = scanner.nextInt();
                scanner.nextLine();

                System.out.println("health: ");
                int health = scanner.nextInt();
                scanner.nextLine();

                return new Consumeable(name, weight, category, health, damage);

            default:
                System.out.println("Invalid choice.");
                return null;
        }
    }
}



