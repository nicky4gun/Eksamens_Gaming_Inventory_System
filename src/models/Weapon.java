package models;

public class Weapon extends Item  {
    private int damage;
    private double attackSpeed;
    private boolean isOneHand;

    public Weapon(int id, String name,double weight,int maxStack, int damage,double attackSpeed,boolean isOneHand) {
        super(id,name,weight,maxStack);
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.isOneHand = isOneHand;
    }
}
