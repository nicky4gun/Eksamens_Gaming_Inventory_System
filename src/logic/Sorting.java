package logic;

import models.Armor;
import models.Item;
import models.Weapon;

import java.util.List;

public class Sorting {
    public void sortByName(List<Item> arr) {
        Item temp;

        for (int pass = 0; pass < arr.size() - 1; pass++) {
            for (int i = 0; i < arr.size() - 1 - pass; i++) {
                if (arr.get(i).getName().compareToIgnoreCase(arr.get(i + 1).getName()) > 0) {
                    temp = arr.get(i);
                    arr.set(i, arr.get(i + 1));
                    arr.set(i + 1, temp);
                }
            }
        }
    }

    public void sortById(List<Item> arr) {
        Item temp;

        for (int pass = 0; pass < arr.size() - 1; pass++) {
            for (int i = 0; i < arr.size() - 1 - pass; i++) {
                if (arr.get(i).getId() > arr.get(i + 1).getId()) {
                    temp = arr.get(i);
                    arr.set(i, arr.get(i + 1));
                    arr.set(i + 1, temp);
                }
            }
        }
    }

    public void sortByType(List<Item> arr) {
        Item temp;
        for (int pass = 0; pass < arr.size() - 1; pass++) {
            for (int i = 0; i < arr.size() - 1 - pass; i++) {

                if (arr.get(i).getItemType().compareTo(arr.get(i + 1).getItemType()) > 0) {
                    temp = arr.get(i);
                    arr.set(i, arr.get(i + 1));
                    arr.set(i + 1, temp);
                }
            }
        }

    }

    public void sortByWeight(List<Item> arr) {
        Item temp;
        for (int pass = 0; pass < arr.size() - 1; pass++) {
            for (int i = 0; i < arr.size() - 1 - pass; i++) {
                if (arr.get(i).getWeight() > arr.get(i + 1).getWeight()) {
                    temp = arr.get(i);
                    arr.set(i, arr.get(i + 1));
                    arr.set(i + 1, temp);
                }
            }
        }
    }

    public void sortByCategory(List<Weapon> arr) {
        Weapon temp;
        for (int pass = 0; pass < arr.size() - 1; pass++) {
            for (int i = 0; i < arr.size() - 1 - pass; i++) {
                if (arr.get(i).getCategory().compareTo(arr.get(i + 1).getCategory()) > 0) {
                    temp = arr.get(i);
                    arr.set(i, arr.get(i + 1));
                    arr.set(i + 1, temp);
                }
            }
        }
    }

    public void sortByArmor(List<Armor> arr) {
        Armor temp;
        for (int pass = 0; pass < arr.size() - 1; pass++) {
            for (int i = 0; i < arr.size() - 1 - pass; i++) {
                if(arr.get(i).getDefense() > arr.get(i + 1).getDefense()) {
                    temp = arr.get(i);
                    arr.set(i, arr.get(i + 1));
                    arr.set(i + 1, temp);
                }
            }
        }
    }
}


