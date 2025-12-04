package logic;

import dat.*;
import models.*;

import java.util.ArrayList;
import java.util.List;

public class InventoryService {
    private final PlayerRepository playerRepository;
    private final ItemRepository itemRepository;
    private final WeaponRepository weaponRepository;
    private final ArmorRepository armorRepository;
    private final ConsumeableRepository consumableRepository;

    private final int MAX_WEIGHT = 50;
    private final int MAX_SLOTS =192;
    private int unlockedSlots = 32;

    public InventoryService(PlayerRepository playerRepository, ItemRepository itemRepository, WeaponRepository weaponRepository, ArmorRepository armorRepository,ConsumeableRepository consumeableRepository) {
        this.playerRepository = playerRepository;
        this.itemRepository = itemRepository;
        this.weaponRepository = weaponRepository;
        this.armorRepository = armorRepository;
        this.consumableRepository = consumeableRepository;
    }

    // Game logic


    // Create objects
    public void createPlayer(String playerName, int credits, int level) {
        Player player = new Player(playerName, credits, level);
        playerRepository.addPlayer(player);
    }

    public void createWeapon(String name, double weight, String category, int damage, double attackSpeed, boolean isOneHanded) {
        Weapon weapon = new Weapon(name, weight, damage, attackSpeed, isOneHanded, category);
        weaponRepository.addItem(weapon);

    }

    public void createArmor(String name, double weight, String category, int defence ) {
        Armor armor = new Armor(name, weight,category,defence);
        armorRepository.addItem(armor);

    }

    public void createConsumable(String name, double weight, String category, int damage,int health) {
        Consumeable consumeable = new Consumeable(name, weight, category, damage, health);
        consumableRepository.addItem(consumeable);
    }
    public void readinventory(String name, double weight, String category, int damage, double attackSpeed, boolean isOneHanded) {
        weaponRepository.readItem();
    }

    public void addItem(Item item) {
        String type = item.getItemType();
        switch (type) {
            case "weapon":
                weaponRepository.addItem((Weapon) item);
                break;

            case "armor":
                armorRepository.addItem((Armor) item);
                break;

            case "consumable":
                consumableRepository.addItem((Consumeable) item);
                break;

            default:
                throw new IllegalArgumentException("Unknown item type: " + type);
        }



        }

    }

