package Homeworks.SecondSemester.Month03.HomeworkTo15;

import java.io.File;
import java.util.Arrays;

public class Files {
    public static void main(String[] args) {
        File file = new File(".");
        fileTreeDisplay(file);
    }

    static void fileTreeDisplay(File file) {
        if(file.isDirectory()) {
            for(File f : file.listFiles()) {
                System.out.println("Directory: " + f.getName());
                fileTreeDisplay(f);
            }
        } else if(file.isFile()) {
            System.out.println("File: " + file.getName());
        }
    }
}
