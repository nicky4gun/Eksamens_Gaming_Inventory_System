package dat;

import dat.config.DatabaseConfig;
import models.Armor;

import java.sql.*;

public class ArmorRepository {
    private final DatabaseConfig config;

    public ArmorRepository(DatabaseConfig config) {
        this.config = config;
    }

    public double getTotalWeight() {
        String sql = "SELECT COALESCE(SUM(weight), 0) FROM armor";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())){
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            return rs.next() ? rs.getDouble(1) : 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error: Unable calculate total weight");
        }
    }

    public int getItemCount() {
        String sql = "SELECT COUNT(*) FROM armor";

        try(Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            return rs.next() ? rs.getInt(1) : 0;

        } catch(SQLException e){
            throw new RuntimeException("Error: Unable calculate total amount of items");
        }
    }

    // CRUD operations for weapons
    public void addItem( Armor armor) {
        String sql = "INSERT INTO armor (name, weight, defense, category) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1,armor.getName());
            stmt.setDouble(2, armor.getWeight());
            stmt.setInt(3, armor.getDefense());
            stmt.setString(4, armor.getCategory().name());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while trying to add item to database.", e);
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

                System.out.printf("%-3d | %-30s | %5.2f | %-6s | %-3d%n", id, name, weight, category, defense);
            }

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while reading item.", e);
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
            throw new RuntimeException("An error occurred while updating item.", e);
        }
    }

    public void deleteItem(String name) {
        String sql = "DELETE FROM armor WHERE name = ?";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while deleting item.", e);
        }
    }
}
