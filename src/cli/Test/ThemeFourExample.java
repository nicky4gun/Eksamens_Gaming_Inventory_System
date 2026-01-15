package cli.Test;

import models.Armor;
import models.Consumable;
import models.Item;
import models.Weapon;

import static models.enums.ConsumableCategory.ARROWS;
import static models.enums.ConsumableCategory.HEALTH_POTION;
import static models.enums.ItemCategory.*;
import static models.enums.WeaponHandling.TWO_HANDED;
import static models.enums.WeaponType.SWORD;

public class ThemeFourExample {
    public static void main(String[] args) {
        Item[] items = new Item[3];

        Armor chestPlate = new Armor("Chest Plate", 5.2, ARMOR, 20);
        Consumable potion = new Consumable("Health Potion", 2.3, CONSUMABLE, 15, 0, HEALTH_POTION, false, 1);
        Weapon sword = new Weapon("Excalibur", 3.5, 13, 4.2, TWO_HANDED, WEAPON, SWORD);

        items[0] = chestPlate;
        items[1] = potion;
        items[2] = sword;

        // Søgning i Arrays
        for (int i = 0; i < items.length; i++) {
            if (items[i].getName().equals("Health Potion")) {
                System.out.println("Item #" + i + ": " + items[i]);
                break;
            }
        }

        // Kopiering af Arrays
        Item[] newItems = new Item[items.length];

        for (int i = 0; i < items.length; i++) {
            newItems[i] = items[i];
        }

        newItems[1] = new Consumable("ARROWS", 0.1, CONSUMABLE, 0, 7, ARROWS, true, 12);

        // Bevis for kopiering
        System.out.println("\n=== Original ===");
        for (int i = 0; i < items.length; i++) {
            System.out.println(items[i]);
        }

        System.out.println("\n=== Nyt Array ===");
        for (int i = 0; i < newItems.length; i++) {
            System.out.println(newItems[i]);
        }
    }
}
