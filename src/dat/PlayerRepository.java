package dat;

import dat.config.DatabaseConfig;
import models.Player;

import java.sql.*;

public class PlayerRepository {
    private final DatabaseConfig config;

    public PlayerRepository(DatabaseConfig config) {
        this.config = config;
    }

    // Operations for handling gold exchange
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

    public boolean subtractGold(int gold) {
        if (gold <= 0) return false;
        String sql = "UPDATE player SET gold = gold - ? WHERE id = 1";

        int currentGold = getGold();
        if (currentGold < gold) {
            return false;
        }

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, gold);
            stmt.executeUpdate();

            return true;

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
