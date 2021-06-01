package Homeworks.SecondSemester.Month05.HomeworkTo27;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Task2 {
    public static void main(String[] args) {
        ArrayList<String> even = new ArrayList<>();
        ArrayList<String> odd = new ArrayList<>();
        ArrayList<String> all = new ArrayList<>();

        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader(
                                     "src/Homeworks/SecondSemester/Month05/HomeworkTo27/data/input.txt"))) {
            String s;
            while ((s = reader.readLine()) != null) {
                all.addAll(Arrays.asList(s.toLowerCase().split(" ")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread evenCountingThread = new Thread(() -> all.forEach((word) -> {
            if (word.chars().asLongStream().distinct().count() % 2 == 0) {
                System.out.println(Thread.currentThread().getName() + " put \"" + word + "\" in even");
                even.add(word);
            }
        }));
        evenCountingThread.start();

        Thread oddCountingThread = new Thread(() -> all.forEach((word) -> {
            if (word.chars().asLongStream().distinct().count() % 2 != 0) {
                System.out.println(Thread.currentThread().getName() + " put \"" + word + "\" in odd");
                odd.add(word);
            }
        }));
        oddCountingThread.start();


        try {
            evenCountingThread.join();
            oddCountingThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(
                "\nList of even: " + even +
                        "\nList of odd: " + odd);
    }
}