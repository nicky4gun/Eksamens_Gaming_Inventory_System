package dat;

import dat.config.DatabaseConfig;
import models.Weapon;
import models.enums.ItemCategory;
import models.enums.WeaponCategory;
import models.enums.WeaponType;

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

    // CRUD operations for items
    public void addItem(Weapon weapon) {
        String sql = "INSERT INTO weapon (name, weight, category, damage, attackSpeed, weaponType ,weaponCategory) VALUES (?, ?, ?, ?, ?, ?,?)";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, weapon.getName());
            stmt.setDouble(2, weapon.getWeight());
            stmt.setString(3, weapon.getCategory().name());
            stmt.setInt(4, weapon.getDamage());
            stmt.setDouble(5, weapon.getAttackSpeed());
            stmt.setString(6, weapon.getWeaponType().name());
            stmt.setString(7,weapon.getWeaponCategory().name());
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                int id = keys.getInt(1);
                weapon.setId(id);
            }

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while trying to add item to database.", e);
        }
    }

    public List<Weapon> readAllWeapons() {
        List<Weapon> weapons = new ArrayList<>();
        String sql = "SELECT id, name, weight, damage, attackSpeed, weaponType, category, weaponCategory FROM weapon";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double weight = rs.getDouble("weight");
                int damage = rs.getInt("damage");
                double attackSpeed = rs.getDouble("attackSpeed");
                WeaponType weaponType = WeaponType.valueOf(rs.getString("weaponType"));
                ItemCategory category =  ItemCategory.valueOf(rs.getString("category"));
                WeaponCategory weaponCategory = WeaponCategory.valueOf(rs.getString("weaponCategory"));

                Weapon weapon = new Weapon(id, name, weight, damage, attackSpeed, weaponType, category, weaponCategory);
                weapons.add(weapon);
            }

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while reading item.", e);
        }

        return weapons;
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

    public void deleteItemById(int id) {
        String sql = "DELETE FROM weapon WHERE id = ?";

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
