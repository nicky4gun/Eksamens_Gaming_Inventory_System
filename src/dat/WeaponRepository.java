package dat;

import dat.config.DatabaseConfig;
import models.Weapon;

import java.sql.*;

public class WeaponRepository {
    private final DatabaseConfig config;

    public WeaponRepository(DatabaseConfig config) {
        this.config = config;
    }

    // CRUD operations for items
    public void addItem(Weapon weapon) {
        String sql = "INSERT INTO weapon (name, weight, category, damage, attackSpeed, isOneHanded) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, weapon.getName());
            stmt.setDouble(2, weapon.getWeight());
            stmt.setString(3, weapon.getCategory());
            stmt.setInt(4, weapon.getDamage());
            stmt.setDouble(5, weapon.getAttackSpeed());
            stmt.setBoolean(6, weapon.getIsOneHanded());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while trying to add item to database.");
        }
    }

    public void readItem() {
        String sql = "SELECT id, name, weight, category, damage, attack_Speed, isOneHanded FROM weapon";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double weight = rs.getDouble("weight");
                String category = rs.getString("category");
                int damage = rs.getInt("damage");
                double attackSpeed = rs.getDouble("attackSpeed");
                boolean isOneHanded = rs.getBoolean("isOneHanded");
                System.out.printf("%-3d | %-10s | %-3f | %-10s | %-3d | %-3f | %-3b%n", id, name, weight, category, damage, attackSpeed, isOneHanded);
            }

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while reading item.");
        }
    }

    public void updateItem(String name, double weight, String category, int damage, double attackSpeed, boolean isOneHanded) {
        String sql = "UPDATE weapon SET name = ?, weight = ?, category = ?, damage = ?, attackSpeed = ?, isOneHanded = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, name);
            stmt.setDouble(2, weight);
            stmt.setString(3, category);
            stmt.setInt(4, damage);
            stmt.setDouble(5, attackSpeed);
            stmt.setBoolean(6,  isOneHanded);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while updating item.");
        }
    }

    public void deleteItem(int id) {
        String sql = "DELETE FROM weapon WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while deleting item.");
        }
    }
}
