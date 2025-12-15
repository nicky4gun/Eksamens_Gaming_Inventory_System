package models;

import models.enums.ItemCategory;
import models.enums.WeaponType;
import models.enums.WeaponHandling;

public class Weapon extends Item {
    private int damage;
    private double attackSpeed;
    private WeaponHandling weaponHandling;
    private WeaponType weaponType;

    public Weapon(int id, String name, double weight, int damage, double attackSpeed, WeaponHandling weaponHandling, ItemCategory category, WeaponType weaponType) {
        super(id, name, weight, category);
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.weaponHandling = weaponHandling;
        this.weaponType = weaponType;
    }

    public Weapon(String name, double weight, int damage, double attackSpeed,
                  WeaponHandling type, ItemCategory category, WeaponType weaponType) {
        super(name, weight, category);
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.weaponHandling = type;
        this.weaponType = weaponType;
    }

    public int getDamage() {
        return damage;
    }
    public double getAttackSpeed() {
        return attackSpeed;
    }
    public WeaponHandling getWeaponType() {
        return weaponHandling;
    }
    public WeaponType getWeaponCategory(){return weaponType;}


    @Override
    public ItemCategory getItemType() {
        return ItemCategory.WEAPON;
    }

    @Override
    public String toString() {
        return super.toString() +
                "Damage: " + damage +
                ", Attack Speed: " + String.format("%.2f", attackSpeed) +
                ", Weapon Type: " + weaponHandling;
    }
}
