package models;

public class Weapon extends Item {
    private int damage;
    private double attackSpeed;
    private boolean isOneHand;

    public Weapon(String name, double weight, int damage, double attackSpeed, boolean isOneHand,String category) {
        super(name, weight, category);
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.isOneHand = isOneHand;
    }

    public int getDamage() {
        return damage;

    }
    public double getAttackSpeed() {
        return attackSpeed;
    }
    public boolean getIsOneHanded() {
        return isOneHand;
    }

    @Override
    public String toString() {
        return "Weapon{" +
                super.toString() +
                "damage=" + damage +
                ", attackSpeed=" + attackSpeed +
                ", isOneHand=" + isOneHand +
                '}';
    }
}
