package dat;

import logic.Sorting;
import models.Armor;
import models.Consumable;
import models.Item;
import models.Weapon;

import java.util.ArrayList;
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

    public List<Weapon> findAllWeapons() {
        return weaponRepository.readAllWeapons();
    }

    public List<Armor> findAllArmor() {
        return armorRepository.readAllArmor();
    }

    public List<Consumable> findAllConsumables() {
        return consumableRepository.readAllConsumables();
    }

    // === Sorting ===
    public List<Item> findAllItemsSortedById() {
        List<Item> items = findAllItems();
        Sorting.sortById(items);
        return items;
    }

    public List<Item> findAllItemsSortedByName() {
        List<Item> items = findAllItems();
        Sorting.sortByName(items);
        return items;
    }

    public List<Item> findAllItemsSortedByWeight() {
        List<Item> items = findAllItems();
        Sorting.sortByWeight(items);
        return items;
    }

    public List<Item> findAllItemsSortedByType() {
        List<Item> items = findAllItems();
        Sorting.sortByType(items);
        return items;
    }

    public List<Weapon> findAllWeaponsSortedCategory(){
        List<Weapon> weapons = findAllWeapons();
        Sorting.sortByCategory(weapons);
        return weapons;
    }

    public List<Armor> findAllArmorSortedCategory(){
        List<Armor> armors = findAllArmor();
        Sorting.sortByArmor(armors);
      return armors;
    }

}

