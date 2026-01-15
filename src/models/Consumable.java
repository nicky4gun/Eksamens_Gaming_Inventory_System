package models;

import models.enums.ConsumableCategory;
import models.enums.ItemCategory;

public class Consumable extends Item {
    private int health;
    private int damage;
    private ConsumableCategory consumableCategory;
    private boolean stackable;
    private int quantity;


    public Consumable(int id, String name, double weight, ItemCategory category, int health, int damage,ConsumableCategory consumableCategory, boolean stackable, int quantity ) {
        super(id , name, weight, category);
        this.health = health;
        this.damage = damage;
        this.consumableCategory = consumableCategory;
        this.stackable = stackable;
        this.quantity = quantity;
    }

    public Consumable(String name, double weight, ItemCategory category, int health, int damage, ConsumableCategory consumableCategory, boolean stackable, int quantity ) {
        super(name, weight, category);
        this.health = health;
        this.damage = damage;
        this.consumableCategory = consumableCategory;
        this.stackable = stackable;
        this.quantity = quantity;
    }

    public int getHealth() {
        return health;
    }
    public int getDamage() {
        return damage;
    }
    public ConsumableCategory getType() {
        return consumableCategory;
    }
    public  boolean getStackable() {
        return stackable;
    }
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public ItemCategory getItemType() {return ItemCategory.CONSUMABLE;}

    @Override
    public String toString() {
        return super.toString()  + " Damage: " + damage + ", Health: " + health + ", Category: " + consumableCategory + ", stack: " + stackable + ", quantity: " + quantity ;
    }
}
