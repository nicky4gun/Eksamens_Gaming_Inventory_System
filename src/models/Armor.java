package models;

import models.enums.ItemCategory;

public class Armor extends Item {
    private int defense;

    public Armor(int id, String name, double weight, ItemCategory category, int defense) {
    super(id , name, weight, category);
    this.defense = defense;
    }

    public Armor(String name, double weight,  ItemCategory category, int defense) {
        super(name, weight, category);
        this.defense = defense;
    }

    public int getDefense() {
        return defense;
    }

    @Override
    public ItemCategory getItemType() {
        return ItemCategory.ARMOR;
    }

    @Override
    public String toString() {
        return super.toString() + "Defense: " + defense;
    }
}
