package logic;

import dat.ItemRepository;
import dat.PlayerRepository;
import models.Player;

public class InventoryService {
    private final PlayerRepository playerRepository;
    private final ItemRepository itemRepository;

    public InventoryService(PlayerRepository playerRepository, ItemRepository itemRepository) {
        this.playerRepository = playerRepository;
        this.itemRepository = itemRepository;
    }

    public void createPlayer(String playerName, int credits, int level) {
        Player player = new Player(playerName, credits, level);
        playerRepository.addPlayer(player);
    }
}
