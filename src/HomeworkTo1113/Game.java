package HomeworkTo1113;

import Methods.Methods;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;


class GameTest {
    public static void main(String[] args) {
        Game newGame = new Game();
        newGame.start();
    }
}

public class Game {
    private Player firstPlayer;
    private Player secondPlayer;

    private final double MIN_CHANCE = 0.4f;
    private final double MAX_CHANCE = 0.95f;

    private final int MAX_STRENGTH = 8;

    public void start() {
        displayRules();
        setPlayers();

        int turnCounter = 0;
        boolean isRetry = false;
        while (!isSomeoneLost()) {
            if (!isRetry) {
                Methods.line();
                System.out.println(
                        "Remaining health: \n" +
                                firstPlayer.getName() + ": " + firstPlayer.getHp() + "\n" +
                                secondPlayer.getName() + ": " + secondPlayer.getHp() + "\n");

                if (turnCounter % 2 == 0) {
                    System.out.println(
                            firstPlayer.getName() + "'s turn!");

                } else {
                    System.out.println(
                            secondPlayer.getName() + "'s turn!");
                }
            }
            System.out.println("Choose the strength of a punch from 1 to " + MAX_STRENGTH + ": ");
            int strength = Methods.getInt();
            if (strength < 1 || strength > MAX_STRENGTH) {
                System.out.println("Please, choose punch power according to the rules!");
                isRetry = true;
                continue;
            }
            isRetry = false;
            hit(strength, turnCounter++ % 2 == 0);
        }
    }

    private void displayRules() {
        Methods.line();
        System.out.println("In this game your goal is to beat your opponent up \n" +
                "each turn you have to punch him with strength measured from 1 to " + MAX_STRENGTH + ". \n" +
                "If you don't miss the enemy, then he will lose health equal to your punch strength, \n" +
                "but aware that stronger the punch then less the chance to hit. \n" +
                "Here is the list of probabilities to hit your opponent depending on the punch power: ");
        for (int i = 1; i <= MAX_STRENGTH; i++) {
            System.out.println("Strength: " + i + " --> probability: " + Math.round(getChance(i) * 100) + "%");
        }
        Methods.line();
    }

    private boolean isSomeoneLost() {
        if (firstPlayer.getHp() <= 0) {
            System.out.println(
                    firstPlayer.getName() + " looses the game \n" +
                            "Better luck next time!");
            return true;
        } else if (secondPlayer.getHp() <= 0) {
            System.out.println(
                    secondPlayer.getName() + " looses the game \n" +
                            "Better luck next time!");
            return true;
        } else
            return false;
    }

    private void hit(int power, boolean isFirstPlayerTurn) {
        String s;
        if (Methods.getRandDouble() < getChance(power)) {
            if (power == 1) s = "";
            else s = "s";
            if (isFirstPlayerTurn) {
                System.out.println(
                        "You got him! \n" +
                                secondPlayer.getName() + " looses " + power + " health point" + s + "!");
                secondPlayer.setHp((byte) (secondPlayer.getHp() - power));
            } else {
                System.out.println(
                        "You got him! \n" +
                                firstPlayer.getName() + " looses " + power + " health point" + s + "!");
                firstPlayer.setHp((byte) (firstPlayer.getHp() - power));
            }
        }
    }

    private double getChance(int power) {
        double section = (MAX_CHANCE - MIN_CHANCE) / (MAX_STRENGTH - 1);
        double chance = MIN_CHANCE + section * (MAX_STRENGTH - power);

        return chance;
    }

    private void setPlayers() {
        firstPlayer = new Player();
        secondPlayer = new Player();
        System.out.println("Enter first player's name: ");
        firstPlayer.setName(Methods.getString());
        System.out.println("Enter second player's name: ");
        secondPlayer.setName(Methods.getString());
        System.out.println("Enter health amount from 1 to 100 for each player: ");
        byte health;
        String input;
        do {
            input = Methods.getString();
        } while (!isInputProper(input));
        health = (byte) Integer.parseInt(input);
        firstPlayer.setHp(health);
        secondPlayer.setHp(health);
    }

    private boolean isInputProper(String input) {
        if (input.length() <= 3 && // not too long
                NumberUtils.isCreatable(input) && // is a number
                Integer.parseInt(input) > 0 && // positive
                Integer.parseInt(input) < 101) { // not too big
            return true;
        } else {
            System.out.println("Please, enter a valid number from 1 to 100: ");
            return false;
        }
    }


    private class Player {
        private String name;
        private byte hp;

        private String getName() {
            return name;
        }

        private void setName(String name) {
            this.name = name.replaceFirst(
                    Character.toString(name.charAt(0)),
                    Character.toString(Character.toUpperCase(name.charAt(0))));
        }

        private byte getHp() {
            return hp;
        }

        private void setHp(byte hp) {
            this.hp = hp;
        }

        @Override
        public String toString() {
            return "Player {" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
