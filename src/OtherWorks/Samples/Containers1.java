package OtherWorks.Samples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;

public class Containers1 {
    static ArrayList<? extends Item> items = new ArrayList<>(
            Arrays.asList(
                    new Item(),
                    new ItemOne(),
                    new ItemTwo()
            )
    );
    static ArrayList<? extends Item> itemsOne = new ArrayList<>(
            Arrays.asList(
                    new ItemOne(),
                    new ItemOne(),
                    new ItemOne()
            )
    );
    static ArrayList<? extends Item> itemsTwo = new ArrayList<>(
            Arrays.asList(
                    new ItemTwo(),
                    new ItemTwo(),
                    new ItemTwo()
            )
    );

    public static void main(String[] args) {
        displayAllItems(itemsOne);
        displayAllItems(itemsTwo);

        System.out.println(items);
        ListIterator<? extends Item> it = items.listIterator();
        while(it.hasNext()) {
            System.out.println(it.previousIndex() + " " + it.nextIndex());
            it.next().display();
        }
    }

    static void displayAllItems(ArrayList<? extends Item> array) {
        for(Item item : array) {
            item.display();
        }
    }
}

class Item {
    void display() {
        System.out.println("Item display");
    }
}

class ItemOne extends Item {
    @Override
    void display() {
        System.out.println("ItemOne display");
    }
}

class ItemTwo extends Item {
    @Override
    void display() {
        System.out.println("ItemTwo display");
    }
}
