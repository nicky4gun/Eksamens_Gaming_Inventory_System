package dat;

import dat.config.DatabaseConfig;
import logic.Sorting;
import models.Armor;
import models.Consumable;
import models.Item;
import models.Weapon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryRepository {
    private final DatabaseConfig config;
    private final WeaponRepository weaponRepository;
    private final ArmorRepository armorRepository;
    private final ConsumableRepository consumableRepository;
    private final Sorting sort;

    public InventoryRepository(DatabaseConfig config, WeaponRepository weaponRepository, ArmorRepository armorRepository, ConsumableRepository consumableRepository, Sorting sort) {
        this.config = config;
        this.weaponRepository = weaponRepository;
        this.armorRepository = armorRepository;
        this.consumableRepository = consumableRepository;
        this.sort = sort;
    }

    // === Searching ===
    public List<Item> findAllItems() {
        List<Item> items = new ArrayList<>();
        items.addAll(weaponRepository.readAllWeapons());
        items.addAll(armorRepository.readAllArmor());
        items.addAll(consumableRepository.readAllConsumables());
        return items;

    }

    public List<Weapon> findAllWeapons() {
        return weaponRepository.readAllWeapons();
    }

    public List<Armor> findAllArmor() {
        return armorRepository.readAllArmor();
    }

    public List<Consumable> findAllConsumables() {
        return consumableRepository.readAllConsumables();
    }

    // === Sorting ===
    public List<Item> findAllItemsSortedById() {
        List<Item> items = findAllItems();
        sort.sortById(items);
        return items;
    }

    public List<Item> findAllItemsSortedByName() {
        List<Item> items = findAllItems();
        sort.sortByName(items);
        return items;
    }

    public List<Item> findAllItemsSortedByWeight() {
        List<Item> items = findAllItems();
        sort.sortByWeight(items);
        return items;
    }

    public List<Item> findAllItemsSortedByType() {
        List<Item> items = findAllItems();
        sort.sortByType(items);
        return items;
    }

    public List<Weapon> findAllWeaponsSortedCategory(){
        List<Weapon> weapons = findAllWeapons();
        sort.sortByCategory(weapons);
        return weapons;
    }

    public List<Armor> findAllArmorSortedCategory(){
        List<Armor> armors = findAllArmor();
        sort.sortByArmor(armors);
        return armors;
    }

    public boolean deleteItemById(int id) {
        String sql = "DELETE FROM item WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while deleting item.", e);
        }
    }
}

