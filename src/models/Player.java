package models;

public class Player {
    private String playerName;
    private int gold;
    private int level;

    public Player(String playerName, int gold, int level) {
        this.playerName = playerName;
        this.gold = gold;
        this.level = level;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getGold() {
        return gold;
    }

    public int getLevel() {
        return level;
    }
}
