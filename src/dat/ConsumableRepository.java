package dat;

import dat.config.DatabaseConfig;
import models.Consumable;
import models.enums.ConsumableCategory;
import models.enums.ItemCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsumableRepository {
    private final DatabaseConfig config;

    public ConsumableRepository(DatabaseConfig config) {
        this.config = config;
    }

    // CRUD operations for consumables
    public void addItem(Consumable consumable) {
        String sql = "INSERT INTO consumable (name, weight, category, health, damage, consumableType, stack, quantity) VALUES (?, ?, ?, ?, ?, ?,? , ?)";
        String sqlItem = "INSERT INTO item (consumable_id) VALUES (?)";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement itemStmt = conn.prepareStatement(sqlItem, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, consumable.getName());
            stmt.setDouble(2, consumable.getWeight());
            stmt.setString(3, consumable.getCategory().name());
            stmt.setDouble(4, consumable.getDamage());
            stmt.setInt(5, consumable.getHealth());
            stmt.setString(6, consumable.getType().name());
            stmt.setBoolean(7, consumable.getStackable());
            stmt.setInt(8, consumable.getQuantity());

            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                int consumableId = keys.getInt(1);
                consumable.setId(consumableId);
            }

            itemStmt.setInt(1, consumable.getId());
            itemStmt.executeUpdate();

            ResultSet itemKeys = itemStmt.getGeneratedKeys();
            if (itemKeys.next()) {
                int itemId = itemKeys.getInt(1);
                consumable.setId(itemId);
            }

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while trying to add item to database.", e);
        }
    }

    public List<Consumable> readAllConsumables() {
        List<Consumable> consumables = new ArrayList<>();
        String sql = """
                SELECT i.id AS item_id, c.consumable_id, c.name, c.weight, c.category, c.damage, c.health, c.consumableType, c.stack, c.quantity
                FROM item i
                JOIN consumable c ON i.consumable_id = c.consumable_id
                """;

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int itemId = rs.getInt("item_id");
                String name = rs.getString("name");
                double weight = rs.getDouble("weight");
                ItemCategory category = ItemCategory.valueOf(rs.getString("category"));
                int damage = rs.getInt("damage");
                int health = rs.getInt("health");
                ConsumableCategory consumableType = ConsumableCategory.valueOf(rs.getString("consumableType"));
                boolean stackable = rs.getBoolean("stack");
                int quantity = rs.getInt("quantity");

                Consumable consumable = new Consumable(itemId, name, weight, category, damage, health, consumableType, stackable, quantity);
                consumables.add(consumable);
            }

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while reading item.", e);
        }

        return consumables;
    }

    public boolean deleteItemById(int id) {
        String sql = "DELETE FROM item WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id );
            int rows = stmt.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while deleting item.", e);
        }
    }
}