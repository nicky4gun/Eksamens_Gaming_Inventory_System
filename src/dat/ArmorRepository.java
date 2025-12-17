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

    // CRUD operations for armor
    public void addItem( Armor armor) {
        String sql = "INSERT INTO armor (name, weight, defense, category) VALUES (?, ?, ?, ?)";
        String sqlItem = "INSERT INTO item (armor_id) VALUES (?)";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement itemStmt = conn.prepareStatement(sqlItem, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1,armor.getName());
            stmt.setDouble(2, armor.getWeight());
            stmt.setInt(3, armor.getDefense());
            stmt.setString(4, armor.getCategory().name());
            stmt.executeUpdate();

            ResultSet armorKeys = stmt.getGeneratedKeys();
            if (armorKeys.next()) {
                int armorId = armorKeys.getInt(1);
                armor.setId(armorId);
            }

            itemStmt.setInt(1, armor.getId());
            itemStmt.executeUpdate();

            ResultSet itemKeys = itemStmt.getGeneratedKeys();
            if (itemKeys.next()) {
                int itemId = itemKeys.getInt(1);
                armor.setId(itemId);
            }

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while trying to add item to database.", e);
        }
    }

    public List<Armor> readAllArmor() {
        List<Armor> armors = new ArrayList<>();
        String sql = """
                SELECT i.id AS item_id, a.armor_id, a.name, a.weight, a.category, a.defense
                FROM item i
                JOIN armor a ON i.armor_id = a.armor_id
                """;

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int itemId = rs.getInt("item_id");
                String name = rs.getString("name");
                double weight  = rs.getDouble("weight");
                ItemCategory category = ItemCategory.valueOf(rs.getString("category"));
                int defense = rs.getInt("defense");

                Armor armor = new Armor(itemId, name, weight, category, defense);
                armors.add(armor);
            }

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while reading item.", e);
        }

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