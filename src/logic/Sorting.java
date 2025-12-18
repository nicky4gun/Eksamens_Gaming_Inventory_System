package logic;

import models.Armor;
import models.Item;
import models.Weapon;

import java.util.List;
 /**Bubble works by picking the first number/word and looking at the number/word next to the first number/word
* if the  number/word it has is  higher than the other they swap places this keeps on until it finds a higher number or there are no other number higher that it has
* if it finds a higher number it takes the higher one  and keep going form this new number  when the number hits the end its locks the number in place, and it now only need to go to the end -1 this keeps on going until its sorted.
**/
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


