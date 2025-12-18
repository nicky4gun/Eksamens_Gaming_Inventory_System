package dat;

import dat.config.DatabaseConfig;

import java.sql.*;

public class PlayerRepository {
    private final DatabaseConfig config;

    public PlayerRepository(DatabaseConfig config) {
        this.config = config;
    }


    // Takes the gold form the corresponded column for the player at showcase it
    public int getGold() {
        String sql = "SELECT gold FROM player WHERE id = 1";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("gold");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to read gold from database.", e);
        }
    }

    // Give gold based on weapon sold or adventuring and put in the correspondent table column for the player
    public void addGold(int gold) {
        if (gold <= 0) return;
        String sql = "UPDATE player SET gold = gold + ? WHERE id = 1";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, gold);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to add gold.", e);
        }
    }

    // Removes gold when used e.g. slots
    public boolean subtractGold(int gold) {
        if (gold <= 0) return false;
        String sql = "UPDATE player SET gold = gold - ? WHERE id = 1 AND gold >= ?";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, gold);
            stmt.setInt(2, gold);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to subtract gold.", e);
        }
    }

    // Operations for handling slots in Inventory
    public int getSlots() {
        String sql = "SELECT slots FROM player WHERE id = 1";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("slots");
            } else {
                return 32;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to read slots from database.", e);
        }
    }

    // Add more slots to the player when he buys them
    public void addSlots(int slots) {
        if (slots <= 0) return;
        String sql = "UPDATE player SET slots = slots + ? WHERE id = 1";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, slots);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to add slots.", e);
        }
    }
}