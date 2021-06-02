package Homeworks.SecondSemester.Month06.HomeworkTo03;

import Methods.Methods;

import java.security.InvalidParameterException;
import java.util.Random;
import java.util.concurrent.*;

public class Task2 {
    private static final int DELAY = 3000;

    public static void main(String[] args) {
        int n = 0;
        try {
            n = checkInput(Methods.getInt());
        } catch (InvalidParameterException e) {
            e.printStackTrace();
            return;
        }

        ScheduledExecutorService service = Executors.newScheduledThreadPool(4);
        StringBuilder string = new StringBuilder();

        for (int i = 0; i < n; i++) {
            Future<String> schedule = service.schedule(new Provider(), DELAY, TimeUnit.MILLISECONDS);
            try {
                Thread.sleep(DELAY);
                String word = schedule.get(DELAY, TimeUnit.MILLISECONDS);
                string.append(word);
                System.out.println("Adding string: " + word + "\nCurrent string: " + string);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
            }
        }
    }

    private static class Provider implements Callable<String> {
        @Override
        public String call() {
            StringBuilder builder = new StringBuilder();
            int length = new Random().nextInt(6) + 1;
            builder.append(getRandomWord(length));
            return builder.toString();
        }

        private static String getRandomWord(int length) {
            return new Random().nextBoolean() ?
                    getRandomWordFromLettersBetween('а', 'я', length) :
                    getRandomWordFromLettersBetween('a', 'z', length);
        }

        private static String getRandomWordFromLettersBetween(char from, char to, int length) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                builder.append(getRandomLetterBetween(from, to));
            }
            return builder.toString();
        }

        private static char getRandomLetterBetween(char from, char to) {
            Random rand = new Random();
            return rand.nextBoolean() ?
                    (char) (rand.nextInt(to - from + 1) + from) :
                    (char) (rand.nextInt(Character.toUpperCase(to) -
                            Character.toUpperCase(from) + 1) + Character.toUpperCase(from));
        }
    }

    private static int checkInput(int input) throws InvalidParameterException {
        if (input > 0) return input;
        throw new InvalidParameterException();
    }
}
