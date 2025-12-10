package models;

import models.enums.ItemCategory;

public class Consumable extends Item {
    private int health;
    private int damage;


    public Consumable(int id, String name, double weight, ItemCategory category, int health, int damage) {
        super(id , name, weight, category);
        this.health = health;
        this.damage = damage;
    }

    public Consumable(String name, double weight, ItemCategory category, int health, int damage) {
        super(name, weight, category);
        this.health = health;
        this.damage = damage;
    }

    public int getHealth() {
        return health;
    }
    public int getDamage() {
        return damage;
    }

    @Override
    public ItemCategory getItemType() {return ItemCategory.CONSUMABLE;}

    @Override
    public String toString() {
        return super.toString()  + " Damage: " + damage + ", Health: " + health;
    }
}
