package cli;

import models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        List<Item> items = new ArrayList<>();

        InventoryCliSystem inventory = new InventoryCliSystem();

        boolean running = true;

        while (running) {
            System.out.println("Choose option: ");
            int choice = input.nextInt();
            input.nextLine();

            switch(choice) {
                case 1:
                    addItem(items,input);
                    break;
                case 0:
                    System.out.println("Bye!");
                    running = false;
            }
        }

        /*
        Player player = new Player("Player", 100, 20);
        Item bow = new Weapon("warbow",3,1,12,1.24,false,"weapon");
        Item twoHandedSword = new Weapon("twinblade",4,1,8,2.00,false,"weapon");
        Item healingPotion = new Consumeable("Healindrink", 2.1, 12, "consumeable", 12, 0);
        Item bomb = new Consumeable("bomb", 7, 5, "consumeable", 0, 10);
        Item helmet = new Armor("vikinghat",3,1,"Armor",32);
        Item chestplate = new Armor("hevy chestplate",6,1,"Armor",32);

        items.add(bow);
        items.add(healingPotion);
        items.add(helmet);
        items.add(chestplate);
        items.add(twoHandedSword);
        items.add(bomb);
        */

        for (Item item : items) {
            System.out.println(item);
        }
    }

    public static void addItem(List<Item> items, Scanner input) {
        System.out.println("Choose category: ");
        String category = input.nextLine();

        switch (category) {
            case "weapon":
                System.out.println("Name: ");
                String name = input.nextLine();

                System.out.println("Weight: ");
                double weight = input.nextDouble();
                input.nextLine();

                System.out.println("Max stack: ");
                int maxStack = input.nextInt();
                input.nextLine();

                System.out.println("Damage: ");
                int damage = input.nextInt();
                input.nextLine();

                System.out.println("Attack speed: ");
                double attackSpeed = input.nextDouble();
                input.nextLine();

                System.out.println("isOneHand (true/false): ");
                boolean isOneHand = input.nextBoolean();
                input.nextLine();

                items.add(new Weapon(name, weight, maxStack, damage, attackSpeed, isOneHand, category));
                break;

           case "armor":
               System.out.println("Name: ");
               String name1 = input.nextLine();
               input.nextLine();
               System.out.println("Weight: ");
               double weight1 = input.nextDouble();
               input.nextLine();

               System.out.println("Max stack: ");
               int maxStack1 = input.nextInt();
               input.nextLine();

               System.out.println("Defense: ");
               int defence  = input.nextInt();
               input.nextLine();

               items.add(new Armor(name1, weight1, maxStack1, category, defence));
               break;

           case "consumeable":
               System.out.println("Name: ");
               String name2 = input.nextLine();

               System.out.println("Weight: ");
               double weight2 = input.nextDouble();
               input.nextLine();

               System.out.println("Max stack: ");
               int maxStack2 = input.nextInt();
               input.nextLine();

               System.out.println("Damage: ");
               int damage2 = input.nextInt();
               input.nextLine();

               System.out.println("health");
               int health = input.nextInt();
               input.nextLine();

               items.add(new Consumeable(name2, weight2, maxStack2,  category,  damage2 , health));
               break;
        }

    }

}
