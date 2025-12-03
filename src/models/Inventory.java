package models;

public class Inventory {
    private final int MAX_WEIGHT;
    private final int MAX_ITEM_SLOTS;
    private int numberOfStartSlots;

    public Inventory() {
        this.MAX_WEIGHT = 50;
        this.MAX_ITEM_SLOTS = 192;
        this.numberOfStartSlots = 32;
    }
}
