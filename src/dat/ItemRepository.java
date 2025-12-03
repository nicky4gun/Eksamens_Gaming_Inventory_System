package dat;

import dat.config.DatabaseConfig;
import models.Armor;
import models.Consumeable;
import models.Item;
import models.Weapon;

import java.sql.*;

public class ItemRepository {
    private final DatabaseConfig config;

    public ItemRepository(DatabaseConfig config) {
        this.config = config;
    }

    // CRUD operations for players
    public void addItem(Item item, Weapon weapon, Armor armor, Consumeable consumeable) {
        String sql = "INSERT INTO item_def (name, max_Stack, weight, category, damage, attack_Speed, is_One_Hand, defense, health) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, item.getName());
            stmt.setInt(2, item.getMaxStack());
            stmt.setDouble(3, item.getWeight());
            stmt.setString(4, item.getCategory());
            stmt.setInt(5, weapon.getDamage());
            stmt.setDouble(6, weapon.getAttackSpeed());
            stmt.setBoolean(7, weapon.getIsOneHanded());
            stmt.setInt(8, armor.getDefense());
            stmt.setInt(9, consumeable.getHealth());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while trying to add item to database.");
        }
    }
}
