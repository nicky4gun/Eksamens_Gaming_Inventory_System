/*
package dat;

import dat.config.DatabaseConfig;
import models.Armor;
import models.Consumable;
import models.Item;
import models.Weapon;

import java.sql.*;

public class ItemRepository {
    private final DatabaseConfig config;

    public ItemRepository(DatabaseConfig config) {
        this.config = config;
    }

    // CRUD operations for items
    public void addItem(Item item, Weapon weapon, Armor armor, Consumable consumable) {
        String sql = "INSERT INTO item_def (name, max_Stack, weight, category, damage, attack_Speed, is_One_Hand, defense, health) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, item.getName());
            stmt.setDouble(2, item.getWeight());
            stmt.setString(3, item.getCategory());
            stmt.setInt(4, weapon.getDamage());
            stmt.setDouble(5, weapon.getAttackSpeed());
            stmt.setBoolean(6, weapon.getIsOneHanded());
            stmt.setInt(7, armor.getDefense());
            stmt.setInt(8, consumable.getHealth());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while trying to add item to database.");
        }
    }

    public void readItem() {
        String sql = "SELECT id,name, max_Stack, weight, category, damage, attack_Speed, is_One_Hand, defense, health FROM item_def";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int maxStack = rs.getInt("max_Stack");
                double weight = rs.getDouble("weight");
                String category = rs.getString("category");
                int damage = rs.getInt("damage");
                double attackSpeed = rs.getDouble("attack_Speed");
                boolean isOneHanded = rs.getBoolean("is_One_Hand");
                int defense = rs.getInt("defense");
                int health = rs.getInt("health");
                System.out.printf("%-3d | %-10s | %-3d | %-3f | %-10s | %-3d | %-3f | %-3b | %-3d | %-3d%n", id, name, maxStack, weight, category, damage, attackSpeed, isOneHanded, defense, health);
            }

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while reading item.");
        }
    }

    public void updateItem(String name, int max_Stack, double weight, String category, int damage, double attack_Speed, boolean is_One_Hand, int defense, int health) {
        String sql = "UPDATE item_def  SET name = ?, max_Stack = ?, weight = ?, category = ?, damage = ?, attack_Speed = ?, is_One_Hand = ?, defense = ?, health = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, name);
            stmt.setDouble(2, weight);
            stmt.setString(3, category);
            stmt.setInt(4, damage);
            stmt.setDouble(5, attack_Speed);
            stmt.setInt(6, defense);
            stmt.setInt(7, health);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while updating item.");
        }
    }

    public void deleteItem(int id) {
        String sql = "DELETE FROM item_def WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while deleting item.");
        }
    }
}
 */
