package dat;

import dat.config.DatabaseConfig;
import models.Armor;
import models.Consumeable;
import models.Item;
import models.Weapon;

import java.sql.*;

public class ConsumeableRepository {
    private final DatabaseConfig config;

    public ConsumeableRepository(DatabaseConfig config) {
        this.config = config;
    }

    // CRUD operations for items
    public void addItem( Consumeable consumeable) {
        String sql = "INSERT INTO consumeable (name, weight, category, damage, health) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, consumeable.getName());
            stmt.setDouble(2, consumeable.getWeight());
            stmt.setString(3, consumeable.getCategory());
            stmt.setDouble(4, consumeable.getDamage());
            stmt.setInt(5, consumeable.getHealth());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while trying to add item to database.");
        }
    }

    public void readItem() {
        String sql = "SELECT id, name, weight, category, damage, health FROM consumeable";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double weight = rs.getDouble("weight");
                String category = rs.getString("category");
                int damage = rs.getInt("damage");
                int health = rs.getInt("health");
                System.out.printf("%-3d | %-10s | %-3f | %-10s | %-3d | %-3d%n", id, name, weight, category, damage, health);
            }

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while reading item.");
        }
    }

    public void updateItem(String name, double weight, String category, int damage, int health) {
        String sql = "UPDATE consumeable  SET name = ?, weight = ?, category = ?, damage = ?, health = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, name);
            stmt.setDouble(2, weight);
            stmt.setString(3, category);
            stmt.setInt(4, damage);
            stmt.setInt(5, health);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while updating item.");
        }
    }

    public void deleteItem(int id) {
        String sql = "DELETE FROM consumeable WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while deleting item.");
        }
    }
}
