package models;

public class Armor extends Item {
    private int defense;

    public Armor(String name, double weight, int maxStack, String category, int defense) {
    super(name, weight, maxStack,category );
    this.defense = defense;
    }
    public int getDefense() {
        return defense;
    }

    @Override
    public String toString() {
        return "Armor{" +
                super.toString() +
                "defense=" + defense +
                '}';
    }
}
