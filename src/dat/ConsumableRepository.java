package dat;

import dat.config.DatabaseConfig;
import models.Consumable;

import java.sql.*;

public class ConsumableRepository {
    private final DatabaseConfig config;

    public ConsumableRepository(DatabaseConfig config) {
        this.config = config;
    }

    public double getTotalWeight() {
        String sql = "SELECT COALESCE(SUM(weight), 0) FROM consumable";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())){
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            return rs.next() ? rs.getDouble(1) : 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error: Unable calculate total weight", e);
        }
    }

    public int getItemCount() {
        String sql = "SELECT COUNT(*) FROM consumable";

        try(Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            return rs.next() ? rs.getInt(1) : 0;

        } catch(SQLException e){
            throw new RuntimeException("Error: Unable calculate total amount of items", e);
        }
    }

    // CRUD operations for items
    public void addItem( Consumable consumable) {
        String sql = "INSERT INTO consumable (name, weight, category, health, damage) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, consumable.getName());
            stmt.setDouble(2, consumable.getWeight());
            stmt.setString(3, consumable.getCategory().name());
            stmt.setDouble(4, consumable.getDamage());
            stmt.setInt(5, consumable.getHealth());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while trying to add item to database.", e);
        }
    }

    public void readItem() {
        String sql = "SELECT id, name, weight, category, damage, health FROM consumable";

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
            throw new RuntimeException("An error occurred while reading item.", e);
        }
    }

    public void updateItem(String name, double weight, String category, int damage, int health) {
        String sql = "UPDATE consumable  SET name = ?, weight = ?, category = ?, damage = ?, health = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, name);
            stmt.setDouble(2, weight);
            stmt.setString(3, category);
            stmt.setInt(4, damage);
            stmt.setInt(5, health);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while updating item.", e);
        }
    }

    public void deleteItem(int id) {
        String sql = "DELETE FROM consumable WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while deleting item.", e);
        }
    }
}
