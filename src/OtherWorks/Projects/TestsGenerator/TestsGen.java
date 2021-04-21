package OtherWorks.Projects.TestsGenerator;

import Methods.Methods;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.ArrayList;

class Tester {
    public static void main(String[] args) {
        for (int i = 0; i <= 20000000; i += 2000000) {
            if(i==0) continue;
            TestsGen.createTestFile(i);
        }
    }
}


public class TestsGen {
    public static void createTestFile(int amount) {
        writeTextTo(getSequence(amount), createFile(getFileName(amount)).getPath());
    }

    private static String getSequence(int length) {
        StringBuilder sequence = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sequence.append(Methods.getRandInt(1001)).append(' ');
        }
        return sequence.toString();
    }

    private static void writeTextTo(String text, String pathToTxt) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToTxt))) {
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getFileName(int length) {
        return "seq-length-" + length + ".txt";
    }


    static String pathToDirectory = "src/OtherWorks/Projects/TestsGenerator/tests";

    private static File createFile(String fileName) {
        File newFile = new File(pathToDirectory + "/" + fileName);
        try {
            newFile.createNewFile();
        } catch (IOException e) {
        }
        return newFile;
    }
}


