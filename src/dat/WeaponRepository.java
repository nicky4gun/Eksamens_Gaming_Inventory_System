package dat;

import dat.config.DatabaseConfig;
import models.Weapon;

import java.sql.*;

public class WeaponRepository {
    private final DatabaseConfig config;

    public WeaponRepository(DatabaseConfig config) {
        this.config = config;
    }

    public double getTotalWeight() {
        String sql = "SELECT COALESCE(SUM(weight), 0) FROM weapon";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())){
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            return rs.next() ? rs.getDouble(1) : 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error: Unable calculate total weight");
        }
    }

    public int getItemCount() {
        String sql = "SELECT COUNT(*) FROM weapon";

        try(Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            return rs.next() ? rs.getInt(1) : 0;

        } catch(SQLException e){
            throw new RuntimeException("Error: Unable calculate total amount of items");
        }
    }

    // CRUD operations for items
    public void addItem(Weapon weapon) {
        String sql = "INSERT INTO weapon (name, weight, category, damage, attackSpeed, isOneHanded,weaponCategory) VALUES (?, ?, ?, ?, ?, ?,?)";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, weapon.getName());
            stmt.setDouble(2, weapon.getWeight());
            stmt.setString(3, weapon.getCategory().name());
            stmt.setInt(4, weapon.getDamage());
            stmt.setDouble(5, weapon.getAttackSpeed());
            stmt.setBoolean(6, weapon.getIsOneHanded());
            stmt.setString(7,weapon.getweaponCategory().name());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while trying to add item to database.", e);
        }
    }

    public void readItem() {
        String sql = "SELECT id, name, weight, damage, attackSpeed, isOneHanded, category,weaponCategory FROM weapon";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double weight = rs.getDouble("weight");
                int damage = rs.getInt("damage");
                double attackSpeed = rs.getDouble("attackSpeed");
                boolean isOneHanded = rs.getBoolean("isOneHanded");
                String category = rs.getString("category");
                String weaponCategory = rs.getString("weaponCategory");
                System.out.printf("%-3d | %-30s | %-5.2f | %-3d | %-5.2f | %-6b | %-7s | %-10s%n", id, name, weight, damage, attackSpeed, isOneHanded, category,weaponCategory);
            }

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while reading item.", e);
        }
    }

    public void updateItem(String name, double weight, String category, int damage, double attackSpeed, boolean isOneHanded,String weaponCategory) {
        String sql = "UPDATE weapon SET name = ?, weight = ?, category = ?, damage = ?, attackSpeed = ?, isOneHanded = ?,weaponCategory =? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, name);
            stmt.setDouble(2, weight);
            stmt.setString(3, category);
            stmt.setInt(4, damage);
            stmt.setDouble(5, attackSpeed);
            stmt.setBoolean(6,  isOneHanded);
            stmt.setString(7, weaponCategory);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while updating item.", e);
        }
    }

    public void deleteItem(String name) {
        String sql = "DELETE FROM weapon WHERE name = ?";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while deleting item.", e);
        }
    }
}
