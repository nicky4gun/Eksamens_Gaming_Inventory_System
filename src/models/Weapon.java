package models;

import models.enums.ItemCategory;
import models.enums.WeaponCategory;
import models.enums.WeaponType;

public class Weapon extends Item {
    private int damage;
    private double attackSpeed;
    private WeaponType weaponType;
    private WeaponCategory weaponCategory;

    public Weapon(String name, double weight, int damage, double attackSpeed, WeaponType weaponType , ItemCategory category, WeaponCategory weaponCategory) {
        super(name, weight, category);
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.weaponType = weaponType;
        this.weaponCategory = weaponCategory;
    }

    public int getDamage() {
        return damage;
    }
    public double getAttackSpeed() {
        return attackSpeed;
    }
    public WeaponType getWeaponType() {
        return weaponType;
    }
    public WeaponCategory getweaponCategory(){return weaponCategory;}


    @Override
    public ItemCategory getItemType() {
        return ItemCategory.WEAPON;
    }

    @Override
    public String toString() {
        return super.toString() +
                "Damage: " + damage +
                ", Attack Speed: " + String.format("%.2f", attackSpeed) +
                ", Weapon Type: " + weaponType;
    }
}
