package logic;

import dat.ItemRepository;
import dat.PlayerRepository;
import models.*;

public class InventoryService {
    private final PlayerRepository playerRepository;
    private final ItemRepository itemRepository;

    public InventoryService(PlayerRepository playerRepository, ItemRepository itemRepository) {
        this.playerRepository = playerRepository;
        this.itemRepository = itemRepository;
    }

    // Create objects
    public void createPlayer(String playerName, int credits, int level) {
        Player player = new Player(playerName, credits, level);
        playerRepository.addPlayer(player);
    }

    /*
    public void createItem(String name, double weight, int maxStack, String category, int damage, double attack_Speed, boolean isOneHanded, int defence, int health) {
        Item item = new Item(name, weight, maxStack, category);
        Weapon weapon = new Weapon(name, weight, maxStack, category, damage, attack_Speed, isOneHanded);
        Armor armor = new Armor(name, weight, maxStack, category, defence);
        Consumeable consumeable = new Consumeable(name, weight, maxStack, category, damage, health);
        itemRepository.addItem(item);
    }

     */

}
