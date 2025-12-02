package dat;

import dat.config.DatabaseConfig;
import models.Player;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlayerRepository {
    private final DatabaseConfig config;

    public PlayerRepository(DatabaseConfig config) {
        this.config = config;
    }

    public void addPlayer(Player player) {
        String sql = "INSERT INTO info_player (name,credits,level) VALUES (?, ?, ?)";

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
}
