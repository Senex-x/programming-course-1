package Homeworks.SecondSemester.Month06.HomeworkTo03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Task1 {
    static volatile Boolean isReadingAvailable = false;

    public static void main(String[] args) {
        new WriterThread(10).start();
    }

    private static class WriterThread extends Thread {
        private static final String OUTPUT_PATH = "src/Homeworks/SecondSemester/Month06/HomeworkTo03/data/output.txt";
        int count;

        private WriterThread(int count) {
            this.count = count;
        }

        @Override
        public void run() {
            if (count-- != 0) {
                if (isReadingAvailable) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    isReadingAvailable = true;
                    write();
                    notify();
                }
            }
        }

        private void write() {
            try {
                Files.write(Paths.get(OUTPUT_PATH), Person.getRandomPerson().toString().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static final class Person {
        private final String name;
        private final int age;
        private final Countries country;

        private static Person getRandomPerson() {
            return new Person(
                    getRandomName(),
                    new Random().nextInt(101),
                    getRandomCountry());
        }

        private Person(String name, int age, Countries country) {
            this.name = name;
            this.age = age;
            this.country = country;
        }

        private static String getRandomName() {
            StringBuilder name = new StringBuilder();
            Random rand = new Random();
            for (int i = 0; i < rand.nextInt(11) + 10; i++) {
                name.append((char) rand.nextInt(123));
            }
            return name.toString();
        }

        private static Countries getRandomCountry() {
            Random rand = new Random();
            Countries[] countries = Countries.values();
            return countries[rand.nextInt(countries.length)];
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public Countries getCountry() {
            return country;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", country=" + country +
                    '}';
        }
    }

    private enum Countries {
        RUSSIA,
        SWEDEN,
        CHINA,
        ITALY,
        GERMANY,
        BINOCLA,
        ITIS,
        ANDORRA,
        SPAIN,
        DENMARK,
        DC,
        MARVEL,
        APPLE,
        SAMSUNG,
        DELL,
        STEAM,
        ORIGIN,
        JAVA,
        C_PLUS {
            @Override
            public String toString() {
                return "C++";
            }
        },
        C_SHARP {
            @Override
            public String toString() {
                return "C#";
            }
        }
    }
}


