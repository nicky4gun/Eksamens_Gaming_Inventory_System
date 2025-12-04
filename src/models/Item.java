package models;

public abstract class Item {
    private String name;
    private double weight;
    private String category;

    public Item(String name, double weight, String category) {
        this.name = name;
        this.weight = weight;
        this.category = category;
    }

    public String getName() {
        return name;
    }
    public double getWeight() {return weight;}
    public String getCategory() {return category;}

    public abstract String getItemType();

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                ", category='" + category + '\'' +
                '}';
    }
}
