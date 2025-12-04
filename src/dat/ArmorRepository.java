package dat;

import dat.config.DatabaseConfig;
import models.Armor;
import models.Consumeable;
import models.Item;
import models.Weapon;

import java.sql.*;

public class ArmorRepository {
    private final DatabaseConfig config;

    public ArmorRepository(DatabaseConfig config) {
        this.config = config;
    }

    // CRUD operations for weapons
    public void addItem( Armor armor) {
        String sql = "INSERT INTO armor (name, weight, category, , defense, ) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1,armor.getName());
            stmt.setDouble(2, armor.getWeight());
            stmt.setString(3, armor.getCategory());
            stmt.setInt(4, armor.getDefense());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while trying to add item to database.");
        }
    }

    public void readItem() {
        String sql = "SELECT id, name, weight, category, defense  FROM armor";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double weight = rs.getDouble("weight");
                String category = rs.getString("category");
                int defense = rs.getInt("defense");

                System.out.printf("%-3d | %-10s | %-3f | %-10s | %-3d%n", id, name, weight, category, defense);
            }

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while reading item.");
        }
    }

    public void updateItem(String name,  double weight, String category,  int defense) {
        String sql = "UPDATE armor  SET name = ?, weight = ?, category = ?, , defense = ?, WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, name);
            stmt.setDouble(2, weight);
            stmt.setString(3, category);
            stmt.setInt(4, defense);


            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while updating item.");
        }
    }

    public void deleteItem(int id) {
        String sql = "DELETE FROM armor WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while deleting item.");
        }
    }
}
