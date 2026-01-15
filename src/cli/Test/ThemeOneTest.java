package cli.Test;

import models.Armor;
import models.Consumable;
import models.Item;
import models.Weapon;

import static models.enums.ConsumableCategory.HEALTH_POTION;
import static models.enums.ItemCategory.*;
import static models.enums.WeaponHandling.TWO_HANDED;
import static models.enums.WeaponType.SWORD;

public class ThemeOneTest {
    public static void main(String[] args) {
        Item[] items = new Item[3];

        Armor chestPlate = new Armor("Chest Plate", 5.2, ARMOR, 20);
        Consumable potion = new Consumable("Health Potion", 2.3, CONSUMABLE, 15, 0, HEALTH_POTION, false, 1);
        Weapon sword = new Weapon("Excalibur", 3.5, 13, 4.2, TWO_HANDED, WEAPON, SWORD);

        items[0] = chestPlate;
        items[1] = potion;
        items[2] = sword;

        // Prints all Items
        for (Item item : items) {
            System.out.println(item);
        }

        // Total Weight
        double expectedTotalWeight = 11.0;
        double actualTotalWeight = calcTotalWeight(items);

        System.out.println(); // Empty line

        System.out.println("=== TOTAL WEIGHT ===");
        System.out.println("Expected Total Wieght: " + expectedTotalWeight);
        System.out.println("Actual Total Weight: " + actualTotalWeight);

        // Damage per Second
        int swordDamage = 13;
        double swordSpeed = 4.2;

        double expectedDPS = 54.6;
        double actualDPS = calculateDPS(swordDamage, swordSpeed);

        System.out.println(); // Empty line

        System.out.println("=== DAMAGE PER SECOND ===");
        System.out.println("Expected DPS: " + expectedDPS);
        System.out.println("Actual DPS: " + actualDPS);
    }

    private static double calcTotalWeight(Item[] items) {
        double totalWeight = 0.0;

        for (Item item : items) {
            totalWeight += item.getWeight();
        }

        return totalWeight;
    }

    private static double calculateDPS(int damage, double attackSpeed) {
        return damage * attackSpeed;
    }
}
