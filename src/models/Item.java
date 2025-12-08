package models;

import models.enums.ItemCategory;

public abstract class Item {
    private String name;
    private double weight;
    private ItemCategory category;

    public Item(String name, double weight, ItemCategory category) {
        this.name = name;
        this.weight = weight;
        this.category = category;
    }

    public String getName() {
        return name;
    }
    public double getWeight() {return weight;}
    public String getCategory() {return category.toString();}

    public abstract ItemCategory getItemType();

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                ", category='" + category + '\'' +
                '}';
    }
}
