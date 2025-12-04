package models;

import models.enums.ItemCategory;

public class Weapon extends Item {
    private int damage;
    private double attackSpeed;
    private boolean isOneHanded;

    public Weapon(String name, double weight, int damage, double attackSpeed, boolean isOneHanded, ItemCategory category) {
        super(name, weight, category);
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.isOneHanded = isOneHanded;
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
                '}';
    }
}
