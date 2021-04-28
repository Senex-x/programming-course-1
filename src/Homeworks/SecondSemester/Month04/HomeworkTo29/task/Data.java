package Homeworks.SecondSemester.Month04.HomeworkTo29.task;

import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Data {
    private static final String PATH_LIST = "src/Homeworks/SecondSemester/Month04/HomeworkTo29/task/data/list.txt";

    public static <T> void update(ArrayList<T> array) {
        GsonBuilder builder = new GsonBuilder();
        String data = builder.setPrettyPrinting().create().toJson(array);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_LIST))) {
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
