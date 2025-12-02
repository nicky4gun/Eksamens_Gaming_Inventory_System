package models;

public class Consumable extends Item {
    private int health;
    private int damage;

    public Consumable(int id,String name,double weight,int maxStack,int health,int damage) {
        super(id,name,weight,maxStack);
        this.health = health;
        this.damage = damage;

    }
}
