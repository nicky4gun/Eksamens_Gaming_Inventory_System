package models;

import models.enums.ItemCategory;
import models.enums.WeaponCategory;

public class Weapon extends Item {
    private int damage;
    private double attackSpeed;
    private boolean isOneHanded;
    private WeaponCategory weaponCategory;

    public Weapon(String name, double weight, int damage, double attackSpeed, boolean isOneHanded, ItemCategory category) {
        super(name, weight, category);
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.isOneHanded = isOneHanded;
        this.weaponCategory = weaponCategory;
    }

    public int getDamage() {
        return damage;
    }
    public double getAttackSpeed() {
        return attackSpeed;
    }
    public boolean getIsOneHanded() {
        return isOneHanded;
    }
    public WeaponCategory weaponCategory(){return weaponCategory;}


    @Override
    public String getItemType() {
        return "weapon";
    }

    @Override
    public String toString() {
        return "Weapon{" +
                super.toString() +
                "damage=" + damage +
                ", attackSpeed=" + attackSpeed +
                ", isOneHand=" + isOneHanded +
                ", weaponCategory= " + weaponCategory +
                '}';
    }
}
