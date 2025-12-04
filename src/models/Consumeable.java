package models;

public class Consumeable extends Item {
    private int health;
    private int damage;

    public Consumeable(String name, double weight,   String category, int health, int damage) {
        super(name,weight,category);
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
    public String getItemType() {
        return "consumeable";
    }

    @Override
    public String toString() {
        return "Consumeable{" + super.toString() +
                "health=" + health +
                ", damage=" + damage +
                '}';
    }
}
