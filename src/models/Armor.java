package models;

public class Armor extends Item {
    private int defense;

    public Armor(int id,String name,double weight,int maxStack ,int defense) {
    super(id,name,weight,maxStack);
    this.defense = defense;}
}
