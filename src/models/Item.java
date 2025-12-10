package models;

import models.enums.ItemCategory;

public abstract class Item {
    private int id;
    private String name;
    private double weight;
    private ItemCategory category;

    public Item(int id, String name, double weight, ItemCategory category) {
        this.name = name;
        this.weight = weight;
        this.category = category;
        this.id = id;
    }

    public Item(String name, double weight, ItemCategory category) {
        this.name = name;
        this.weight = weight;
        this.category = category;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int  getId() {return id;}
    public String getName() {
        return name;
    }
    public double getWeight() {return weight;}
    public ItemCategory getCategory() {return category;}


    public abstract ItemCategory getItemType();

    @Override
    public String toString() {
        return id + " | " + category.toString() + ", " + name + ", " + String.format("%.2f kg", weight) + ", ";
    }
}
