package OtherWorks.Lectures.Presentation2;

import java.util.ArrayList;

public class L2Part1 {
    public static void main(String[] args) {
        Factory factory = new Factory();

        try {

        }

        finally {
            System.out.println("Спасибо за внимание!");
        }
    }
}

class Factory {
    Storage storage  = new Storage();

    {
        storage.add("Crate");
        storage.add("Barrel");
        storage.add("Box");
        storage.displayInfo();
    }

    private class Storage {
        private int amount;
        private ArrayList<String> list = new ArrayList<>();

        private void add(String name) {
            list.add(name);
            amount++;
        }

        private void displayInfo() {
            System.out.println("Number of items: " + amount +
                    "\nStored items: " + list);
        }
    }
}


class Notifier {
    Notifier(String context) {
        System.out.println(context);
    }
}
