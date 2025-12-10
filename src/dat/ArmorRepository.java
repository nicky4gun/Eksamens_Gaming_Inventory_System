package dat;

import dat.config.DatabaseConfig;
import models.Armor;
import models.enums.ItemCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1,armor.getName());
            stmt.setDouble(2, armor.getWeight());
            stmt.setInt(3, armor.getDefense());
            stmt.setString(4, armor.getCategory().name());
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                int id = keys.getInt(1);
                armor.setId(id);
            }

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while trying to add item to database.", e);
        }
    }

    public List<Armor> readAllArmor() {
        List<Armor> armors = new ArrayList<>();
        String sql = "SELECT id, name, weight, category, defense  FROM armor";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double weight  = rs.getDouble("weight");
                ItemCategory category = ItemCategory.valueOf(rs.getString("category"));
                int defense = rs.getInt("defense");

                Armor armor = new Armor(id, name, weight, category, defense);
                armors.add(armor);
            }

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while reading item.", e);
        }

        return armors;
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

    public void deleteItemById(int id) {
        String sql = "DELETE FROM armor WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();

            if (rows == 0) {
                System.out.println("No weapon with id " + id + " found");
            }

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while deleting item.", e);
        }
    }
}
