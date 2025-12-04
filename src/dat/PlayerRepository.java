package dat;

import dat.config.DatabaseConfig;
import models.Player;

import java.sql.*;

public class PlayerRepository {
    private final DatabaseConfig config;

    public PlayerRepository(DatabaseConfig config) {
        this.config = config;
    }

    // CRUD operations for players
    public void addPlayer(Player player) {
        String sql = "INSERT INTO info_player (name , credits, level) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, player.getPlayerName());
            stmt.setInt(2, player.getCredits());
            stmt.setInt(3, player.getLevel());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while trying to add player to database.");
        }
    }

    public void readPlayer() {
        String sql = "SELECT * FROM info_player";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int credits = rs.getInt("credits");
                int level = rs.getInt("level");
                System.out.printf("%-3d | %-10s | %-10s | %-3d%n", id, name,  credits, level);
            }

        } catch (SQLException e) {
            throw new RuntimeException("an error occurred while trying to read player from database.");
        }
    }

    public void updatePlayer(String name, int credits, int level) {
        String sql = "UPDATE info_player SET name = ?, credits = ?, level ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, name);
            stmt.setInt(2, credits);
            stmt.setInt(3, level);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while updating player in database.");
        }
    }

    public void deletePlayer(int id) {
        String sql = "DELETE FROM info_player WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred while deleting player.");
        }
    }
}
