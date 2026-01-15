package logic;

import models.Armor;
import models.Consumable;
import models.Item;
import models.Weapon;
import models.enums.*;

import java.util.Random;

public class ItemFactory {
    private final Random rand = new Random();

    public Item createRandomItem() {
        int choice = 1 + rand.nextInt(3);

        return switch (choice) {
            case 1 -> createRandomWeapon();
            case 2 -> createRandomArmor();
            case 3 -> createRandomConsumable();
            default -> throw new IllegalArgumentException("Unexpected random value: " + choice);
        };
    }

    private Weapon createRandomWeapon() {
        ItemCategory category = ItemCategory.WEAPON;
        double weight = 0.5 + (5 * rand.nextDouble());
        int damage = 5 + rand.nextInt(30);
        double attackSpeed = 0.5 + rand.nextDouble() * 2;

        WeaponHandling weaponHandling = WeaponHandling.values()[rand.nextInt(WeaponHandling.values().length)];
        WeaponType weaponType = WeaponType.values()[rand.nextInt(WeaponType.values().length)];
        CoolWeaponNames coolWeaponNames = CoolWeaponNames.values()[rand.nextInt(CoolWeaponNames.values().length)];
        CoolWeaponNames coolWeaponNames2 = CoolWeaponNames.values()[rand.nextInt(CoolWeaponNames.values().length)];

        int nameGen = rand.nextInt(3) + 1;
        String weaponName;

        if (nameGen == 1) {
            weaponName = weaponType + " " + coolWeaponNames;
        } else if (nameGen == 2){
            weaponName = coolWeaponNames + " " + weaponType;
        } else {
            weaponName = coolWeaponNames + " " + weaponType + " " + coolWeaponNames2;
        }

        return new Weapon(weaponName, weight, damage, attackSpeed, weaponHandling, category, weaponType);
    }

    private Armor createRandomArmor() {
        ItemCategory category = ItemCategory.ARMOR;
        double weight = 0.5 + (5 * rand.nextDouble());
        ArmorCategory armorCategory = ArmorCategory.values()[rand.nextInt(ArmorCategory.values().length)];
        CoolArmorNames coolArmorNames = CoolArmorNames.values()[rand.nextInt(CoolArmorNames.values().length)];
        String armorName = armorCategory.name() + " " + coolArmorNames;

        int defense = 1 + rand.nextInt(40);

        return new Armor(armorName, weight, category, defense);
    }

    private Consumable createRandomConsumable() {
        ItemCategory category = ItemCategory.CONSUMABLE;

        CoolConsumabelName coolConsumableName = CoolConsumabelName.values()[rand.nextInt(CoolConsumabelName.values().length)];
        ConsumableCategory consumableCategory = ConsumableCategory.values()[rand.nextInt(ConsumableCategory.values().length)];
        String consumableName = coolConsumableName.name();

        double weight = 0.5;
        int health = 0;
        int damage = 0;
        boolean stackable = false;
        int quantity = 1;

        if (consumableCategory == ConsumableCategory.HEALTH_POTION) {
            health = 50;
        } else if (consumableCategory == ConsumableCategory.DAMAGE_POTION) {
            damage = 20;
        } else if (consumableCategory == ConsumableCategory.ARROWS || consumableCategory == ConsumableCategory.BOLTS ) {
            stackable = true;
            quantity = 12;

            if (consumableCategory == ConsumableCategory.ARROWS) {
                consumableName = "ARROWS";
            } else {
                consumableName = "BOLTS";
            }
        }

        return new Consumable(consumableName, weight, category, health, damage, consumableCategory, stackable, quantity);
    }
}