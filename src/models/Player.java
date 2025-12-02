package models;

public class Player {
    private String playerName;
    private int credits;
    private int level;

    public Player(String playerName, int credits, int level) {
        this.playerName = playerName;
        this.credits = credits;
        this.level = level;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getCredits() {
        return credits;
    }

    public int getLevel() {
        return level;
    }
}
