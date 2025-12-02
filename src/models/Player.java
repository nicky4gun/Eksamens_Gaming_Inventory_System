package models;

public class Player {
    private String playerId;
    private String playerName;
    private int credits;

    public Player(String playerId, String playerName, int credits) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.credits = credits;
    }
}
