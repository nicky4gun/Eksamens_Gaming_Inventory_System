package cli.Test;

import logic.InventoryService;
import models.Armor;
import models.Consumable;
import models.Item;
import models.Weapon;
import models.enums.ConsumableCategory;
import models.enums.ItemCategory;
import models.enums.WeaponHandling;
import models.enums.WeaponType;

public class OverloadingExample {

    public static void main(String[] args) {
        InventoryService service = null;

        Weapon sword = new Weapon("Excalibur", 3.2, 12, 2.6, WeaponHandling.TWO_HANDED, ItemCategory.WEAPON, WeaponType.SWORD);
        service.addItem(sword);

        Armor helmet = new Armor("Helmet", 1.2, ItemCategory.ARMOR, 23);
        service.addItem(helmet);

        Consumable potion = new Consumable("Healh Potion", 2.3, ItemCategory.CONSUMABLE, 15, 0, ConsumableCategory.HEALTH_POTION, true, 1);
        service.addItem(potion);

        Item randomItem = service.createRandomItem();
        service.addItem(randomItem);
    }
}
