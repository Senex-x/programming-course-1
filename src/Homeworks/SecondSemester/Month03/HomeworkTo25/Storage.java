package Homeworks.SecondSemester.Month03.HomeworkTo25;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

class Test {
    public static void main(String[] args) {
        String[] names = {"Bread", "Cookie", "Butter"};
        Storage storage = new Storage();
        storage.createType("Milk");
        storage.addItem("Milk");
        storage.deleteItem(storage.getItem("milk"));
        storage.getItem("milk");
        for (int j = 0; j < names.length; j++)
            for (int i = 0; i < 10; i++) {
                storage.addItem(names[j]);
            }
        storage.display();
    }

    public static class Storage {
        private final ArrayList<Type> types = new ArrayList<>();

        void addItem(String name) {
            for (Type t : types) {
                // Such type already exists
                if (t.getName().equalsIgnoreCase(name)) {
                    t.addItem();
                    updateDB();
                    return;
                }
            }

            createType(name);
            addItem(name);
        }

        Type.Item getItem(String name) {
            for (Type t : types) {
                if (t.getName().equalsIgnoreCase(name)) {
                    try {
                        return t.getItem();
                    } catch (IllegalStateException e) {
                        System.err.println("No such item as " + name + " in storage exception");
                        return null;
                    }

                }
            }
            System.err.println("No such item as " + name + " in storage exception");
            return null;
        }

        void deleteItem(Type.Item item) {
            deleteItem(item.getId());
        }

        void deleteItem(int id) {
            for (Type t : types) {
                if (t.isItemFound(id)) {
                    t.deleteItem(id);
                }
            }
            updateDB();
        }

        private void createType(String name) {
            types.add(new Type(name));
        }

        public ArrayList<Type> getTypes() {
            return types;
        }

        void display() {
            System.out.println(toString());
        }

        @Override
        public String toString() {
            return "Storage: " + types;
        }

        private static final String PATH_LIST = "src/Homeworks/SecondSemester/Month03/HomeworkTo25/data/list.txt";

        void updateDB() {
            GsonBuilder builder = new GsonBuilder();
            String data = builder.setPrettyPrinting().create().toJson(types);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_LIST))) {
                writer.write(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class Type {
        private static final IdHandler idHandler = new IdHandler();
        private final String name;
        private int count;
        LinkedList<Item> items = new LinkedList<>();

        public Type(String name) {
            this.name = name;
            count = 0;
        }

        void addItem() {
            items.add(new Item(idHandler.getId(), name));
            count++;
        }

        Item getItem() throws IllegalStateException {
            if (!items.isEmpty()) {
                return items.get(0);
            } else {
                throw new IllegalStateException();
            }
        }

        boolean isItemFound(int id) {
            for (Item i : items) {
                if (i.getId() == id) {
                    return true;
                }
            }
            return false;
        }

        void deleteItem(int id) {
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getId() == id) {
                    items.remove(i);
                    return;
                }
            }
            count--;
        }

        LinkedList<Item> getItems() {
            return items;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Type {'" + name + '\'' +
                    ", items counter: " + count +
                    ", items: " + items + "}";
        }

        private static class Item {
            private final int id;
            private final String name;

            private Item(int id, String name) {
                this.id = id;
                this.name = name;
            }

            private int getId() {
                return id;
            }

            private String getName() {
                return name;
            }

            @Override
            public String toString() {
                return "Item " +
                        "(ID:" + IdHandler.formatId(id) +
                        ") '" + name + '\'';
            }
        }

        static class IdHandler {
            private static final String PATH_ID = "src/Homeworks/SecondSemester/Month03/HomeworkTo25/data/id.txt";

            int getId() {
                return createId();
            }

            int createId() {
                int id = -1;
                try (BufferedReader reader = new BufferedReader(new FileReader(PATH_ID))) {
                    id = Integer.parseInt(reader.readLine()) + 1;
                    updateId(id);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return id;
            }

            void updateId(int id) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_ID))) {
                    writer.write(Integer.toString(id));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            private static final int ID_LENGTH = 3;

            static String formatId(int id) {
                StringBuilder formattedId = new StringBuilder();
                for (int i = 0; i < ID_LENGTH - Integer.toString(id).length(); i++) {
                    formattedId.append(0);
                }
                formattedId.append(id);
                return formattedId.toString();
            }
        }
    }


}
