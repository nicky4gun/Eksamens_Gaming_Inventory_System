package dat;

import models.Item;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InventoryRepository {
    private final WeaponRepository weaponRepository;
    private final ArmorRepository armorRepository;
    private final ConsumableRepository consumableRepository;

    public InventoryRepository(WeaponRepository weaponRepository, ArmorRepository armorRepository, ConsumableRepository consumableRepository) {
        this.weaponRepository = weaponRepository;
        this.armorRepository = armorRepository;
        this.consumableRepository = consumableRepository;
    }

    // === Searching ===
    public List<Item> findAllItems() {
        List<Item> items = new ArrayList<>();
        items.addAll(weaponRepository.readAllWeapons());
        items.addAll(armorRepository.readAllArmor());
        items.addAll(consumableRepository.readAllConsumables());
        return items;
    }

    // === Sorting ===
    public List<Item> findAllItemsSortedById() {
        List<Item> items = findAllItems();
        items.sort(Comparator.comparing(Item::getId));
        return items;
    }

    public List<Item> findAllItemsSortedByName() {
        List<Item> items = findAllItems();
        items.sort(Comparator.comparing(Item::getName));
        return items;
    }

    public List<Item> findAllItemsSortedByWeight() {
        List<Item> items = findAllItems();
        items.sort(Comparator.comparing(Item::getWeight));
        return items;
    }

    public List<Item> findAllItemsSortedByType() {
        List<Item> items = findAllItems();
        items.sort(Comparator.comparing(Item::getItemType));
        return items;
    }
}
