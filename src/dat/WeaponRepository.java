package dat;

import dat.config.DatabaseConfig;
import models.Weapon;
import models.enums.ItemCategory;
import models.enums.WeaponType;
import models.enums.WeaponHandling;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    // CRUD operations for weapons
    public void addItem(Weapon weapon) {
        String sql = "INSERT INTO weapon (name, weight, category, damage, attackSpeed, weaponHandling ,weaponType) VALUES (?, ?, ?, ?, ?, ?,?)";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, weapon.getName());
            stmt.setDouble(2, weapon.getWeight());
            stmt.setString(3, weapon.getCategory().name());
            stmt.setInt(4, weapon.getDamage());
            stmt.setDouble(5, weapon.getAttackSpeed());
            stmt.setString(6, weapon.getWeaponHandling().name());
            stmt.setString(7, weapon.getWeaponType().name());
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                int weaponId = keys.getInt(1);
                weapon.setId(weaponId);
            }

            String sqlItem = "INSERT INTO item (weapon_id) VALUES (?)";

            try {
                PreparedStatement itemStmt = conn.prepareStatement(sqlItem, Statement.RETURN_GENERATED_KEYS);
                itemStmt.setInt(1, weapon.getId());
                itemStmt.executeUpdate();

                ResultSet itemKeys = itemStmt.getGeneratedKeys();
                if (itemKeys.next()) {
                    int itemId = itemKeys.getInt(1);
                    weapon.setId(itemId);
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error: Unable to insert item");
            }

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while trying to add item to database.", e);
        }
    }

    public List<Weapon> readAllWeapons() {
        List<Weapon> weapons = new ArrayList<>();
        String sql = """
                SELECT i.id AS item_id, w.weapon_id, w.name, w.weight, w.damage, w.attackSpeed, w.weaponHandling, w.category, w.weaponType
                FROM item i
                JOIN weapon w ON i.weapon_id = w.weapon_id
                """;

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int itemId = rs.getInt("item_id");
                String name = rs.getString("name");
                double weight = rs.getDouble("weight");
                int damage = rs.getInt("damage");
                double attackSpeed = rs.getDouble("attackSpeed");
                WeaponHandling weaponHandling = WeaponHandling.valueOf(rs.getString("weaponHandling"));
                ItemCategory category =  ItemCategory.valueOf(rs.getString("category"));
                WeaponType weaponType = WeaponType.valueOf(rs.getString("weaponType"));

                Weapon weapon = new Weapon(itemId, name, weight, damage, attackSpeed, weaponHandling, category, weaponType);
                weapons.add(weapon);
            }

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while reading item.", e);
        }

        return weapons;
    }

    public void updateItem(String name, double weight, String category, int damage, double attackSpeed, boolean isOneHanded,String weaponType) {
        String sql = "UPDATE weapon SET name = ?, weight = ?, category = ?, damage = ?, attackSpeed = ?, isOneHanded = ?,weaponType =? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, name);
            stmt.setDouble(2, weight);
            stmt.setString(3, category);
            stmt.setInt(4, damage);
            stmt.setDouble(5, attackSpeed);
            stmt.setBoolean(6,  isOneHanded);
            stmt.setString(7, weaponType);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while updating item.", e);
        }
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
