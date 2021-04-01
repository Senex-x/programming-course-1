package Classworks.ClassworkOf0401;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Parser {
    private static final String PATH = "src/OtherWorks/Exams/ControlWork1/data/list.txt";
    static HashMap<String, HashMap<String, HashMap<String, Integer>>> data = new HashMap<>();

    public static void main(String[] args) {
        parseText();
        displayData();
    }

    static void displayData() {
        for(String name : data.keySet()) {
            System.out.println(name);
            for(String city : data.get(name).keySet()) {
                System.out.println("\t" + city);
                HashMap<String, Integer> itemMap = data.get(name).get(city);
                for(String itemName : itemMap.keySet()) {
                    System.out.println("\t\t" + itemName + ", count: " + itemMap.get(itemName));
                }
            }
        }
    }

    static void parseText() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(PATH));
            String line;
            while((line = reader.readLine()) != null) {
                String[] words = line.split("\\|");
                addAll(words[0], words[1], words[2],Integer.parseInt(words[3]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addAll(String name, String city, String item, int count) {
        if(!data.containsKey(name)) {
            data.put(name, new HashMap<>());
        }

        HashMap<String, HashMap<String, Integer>> clientData = data.get(name);
        if(!clientData.containsKey(city)) {
            clientData.put(city, new HashMap<>());
        }

        HashMap<String, Integer> cityData = clientData.get(city);
        if(!cityData.containsKey(item)) {
            cityData.put(item, count);
        } else {
            cityData.replace(item, cityData.get(item) + count);
        }
    }
}
