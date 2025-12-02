package models;

public class Item {
    private int id;
    private String name;
    private double weight;
    private int maxStack;

    public Item(int id, String name, double weight, int maxStack) {
    this.id = id;
    this.name = name;
    this.weight = weight;
    this.maxStack = maxStack;
    }
}
