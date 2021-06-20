package OtherWorks.Samples;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.cert.CollectionCertStoreParameters;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Cw2r {
    public static String pathProducts = "src/OtherWorks/Samples/data/data1.txt"; // products.txt : “товар|магазин|цена”
    public static String pathPurchases = "src/OtherWorks/Samples/data/data2.txt"; // purchases.txt : “покупатель|магазин|товар”
    public static ArrayList<Purchase> listOfPurchases;
    public static ArrayList<Product> listOfProducts;

    public static void main(String[] args) {
//1. Вывести для каждого покупателя магазин, где он потратил больше всего денег.
//2. Вывести для каждого магазина самого прибыльного покупателя (который потратил в нём больше всего денег).
//3. Вывести для каждой пары покупатель-товар из файла покупок самую низкую цену, за которую этот покупатель
// купил этот товар, и самую высокую.
//4. Всё в потоках

        listOfProducts = parseTextToListOfProducts(pathProducts);
        listOfPurchases = parseTextToListOfPurchases(pathPurchases);

        task2();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                task1();
            }
        });
/*
        try {
            Stream<String> stream = Files.lines(Paths.get("file.txt"));
            var t = Files.lines(Paths.get(pathProducts));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

    public static void task1() {
    }

    private static void task2() {
        listOfPurchases.stream().collect(Collectors.toMap(
                Purchase::getShop,
                product -> {
                    ArrayList<HashMap<String, String>> l = new ArrayList<>();
                    HashMap<String, String> m = new HashMap<>();
                    m.put(product.getName(), product.getProduct());
                    l.add(m);
                    return l;
                },
                (h1, h2) -> {
                    h1.addAll(h2);
                    return h1;
                }
        )).entrySet().forEach( // each shop
                shop -> {
                    HashMap<String, Integer> buyersSpend = new HashMap<>();
                    //System.out.println(shop);
                    shop.getValue().stream().collect(Collectors.toMap(
                            map -> map.keySet().stream().findFirst().get(),
                            map -> {
                                String product = map.entrySet().stream().findFirst().get().getValue();
                                ArrayList<String> p = new ArrayList<>();
                                p.add(product);
                                return p;
                            },
                            (e1, e2) -> {
                                e1.addAll(e2);
                                return e1;
                            })).entrySet().forEach( // each buyer
                            buyer -> {
                                //System.out.println(buyer);
                                ArrayList<Integer> prices = new ArrayList<>();
                                buyer.getValue().stream().forEach(
                                        item -> {
                                            //System.out.println(item);
                                            int price = listOfProducts.stream()
                                                    .filter(product ->
                                                            product.getShop().equals(shop.getKey()) &&
                                                                    product.getProduct().equals(item))
                                                    .findFirst().get().getPrice();
                                            prices.add(price);
                                        });
                                int total = prices.stream().mapToInt(value -> value).sum();
                                buyersSpend.put(buyer.getKey(), total);
                            });
                    System.out.println("Best buyer of shop " + shop.getKey() + " is: " +
                            buyersSpend.entrySet().stream().min(Comparator.comparingInt(Map.Entry::getValue)).get().getKey());

                }
        );
    }

    public static ArrayList<Product> parseTextToListOfProducts(String path) {
        ArrayList<Product> array = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new FileReader(path));
            while (sc.hasNext()) {
                String[] line = sc.nextLine().split("\\|");
                array.add(new Product(
                        line[0],
                        line[1],
                        Integer.parseInt(line[2])
                ));
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return array;
    }

    public static ArrayList<Purchase> parseTextToListOfPurchases(String path) {
        ArrayList<Purchase> array = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new FileReader(path));
            while (sc.hasNext()) {
                String[] line = sc.nextLine().split("\\|");
                array.add(new Purchase(
                        line[0],
                        line[1],
                        line[2]
                ));
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return array;
    }
}


class Product {
    String product;
    String shop;
    int price;

    public Product(String product, String shop, int price) {
        this.product = product;
        this.shop = shop;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Products{" +
                "product='" + product + '\'' +
                ", shop='" + shop + '\'' +
                ", price=" + price +
                '}';
    }

    public String getProduct() {
        return product;
    }

    public String getShop() {
        return shop;
    }

    public int getPrice() {
        return price;
    }
}

class Purchase {
    String name;
    String shop;
    String product;

    public Purchase(String name, String shop, String product) {
        this.name = name;
        this.shop = shop;
        this.product = product;
    }

    @Override
    public String toString() {
        return "Purchases{" +
                "name='" + name + '\'' +
                ", shop='" + shop + '\'' +
                ", product='" + product + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getShop() {
        return shop;
    }

    public String getProduct() {
        return product;
    }
}