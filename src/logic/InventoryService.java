package logic;

import dat.ItemRepository;
import dat.PlayerRepository;
import dat.WeaponRepository;
import models.*;

public class InventoryService {
    private final PlayerRepository playerRepository;
    private final ItemRepository itemRepository;
    private final WeaponRepository weaponRepository;

    private final int MAX_WEIGHT = 50;
    private final int MAX_SLOTS =192;
    private int unlockedSlots = 32;

    public InventoryService(PlayerRepository playerRepository, ItemRepository itemRepository, WeaponRepository weaponRepository) {
        this.playerRepository = playerRepository;
        this.itemRepository = itemRepository;
        this.weaponRepository = weaponRepository;
    }

    // Game logic



    // Create objects
    public void createPlayer(String playerName, int credits, int level) {
        Player player = new Player(playerName, credits, level);
        playerRepository.addPlayer(player);
    }

    public void createWeapon(String name, double weight, String category, int damage, double attackSpeed, boolean isOneHanded) {
        Weapon weapon = new Weapon(name, weight, damage, attackSpeed, isOneHanded, category);
        weaponRepository.addWeapon(weapon);
    }

    public void createArmor() {}

    public void createConsumable() {}

}
