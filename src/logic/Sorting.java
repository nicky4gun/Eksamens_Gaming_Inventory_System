package logic;

import models.Item;

import java.util.List;

public class Sorting {
    static int MAX = 100;

    public static void sortByName(List<Item> arr) {
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
        public static void


    }

}
