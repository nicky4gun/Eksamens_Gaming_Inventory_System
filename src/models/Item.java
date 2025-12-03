package models;

public abstract class Item {
    private String name;
    private double weight;
    private int maxStack;
    private String category;

    public Item(String name, double weight, int maxStack, String category) {
        this.name = name;
        this.weight = weight;
        this.maxStack = maxStack;
        this.category = category;
    }

    public String getName() {
        return name;
    }
    public double getWeight() {return weight;}
    public int getMaxStack() {return maxStack;}
    public String getCategory() {return category;}

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                ", maxStack=" + maxStack +
                ", category='" + category + '\'' +
                '}';
    }
}
