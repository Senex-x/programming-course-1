package Homeworks.SecondSemester.Month06.HomeworkTo03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class Task1 {
    private static final String OUTPUT_PATH = "src/Homeworks/SecondSemester/Month06/HomeworkTo03/data/output.txt";
    private static final Object lock = new Object();
    private static volatile Boolean isReadingAvailable = false;

    public static void main(String[] args) {
        Thread readerThread = new Thread(new ReaderRunnable(10));
        readerThread.setName("Reader");
        readerThread.start();

        Thread writerThread = new Thread(new WriterRunnable(10));
        writerThread.setName("Writer");
        writerThread.start();

        try {
            readerThread.join();
            writerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class WriterRunnable implements Runnable {
        int count;

        private WriterRunnable(int count) {
            this.count = count;
        }

        @Override
        public void run() {
            synchronized (lock) {
                while (count-- != 0) {
                    while (isReadingAvailable) {
                        printMessage("waits");
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    printMessage("writes, " + (count + 1) + " times left");
                    write();
                    isReadingAvailable = true;
                    lock.notify();
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

    private static final class ReaderRunnable implements Runnable {
        int count;

        public ReaderRunnable(int count) {
            this.count = count;
        }

        @Override
        public synchronized void run() {
            synchronized (lock) {
                while (count-- != 0) {
                    while (!isReadingAvailable) {
                        try {
                            printMessage("waits");
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    printMessage("reads, " + (count + 1) + " times left");
                    read();
                    isReadingAvailable = false;
                    lock.notify();
                }
            }
        }

        private void read() {
            try {
                Files.readAllLines(Paths.get(OUTPUT_PATH)).forEach(System.out::println);
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

    private static void printMessage(String message) {
        System.out.println(Thread.currentThread().getName() + " " + message);
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


