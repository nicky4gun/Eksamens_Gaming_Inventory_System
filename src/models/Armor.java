package models;

public class Armor extends Item {
    private int defense;

    public Armor(String name, double weight, String category, int defense) {
    super(name, weight ,category );
    this.defense = defense;
    }
    public int getDefense() {
        return defense;
    }

    @Override
    public String getItemType() {
        return "armor";
    }

    @Override
    public String toString() {
        return "Armor{" +
                super.toString() +
                "defense=" + defense +
                '}';
    }
}
