package HomeworkTo1113;

import Methods.Methods;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

class GameTest {
    public static void main(String[] args) {
        Game newGame = new Game();
        newGame.start();
    }
}

public class Game {
    private Player firstPlayer;
    private Player secondPlayer;

    private static final double MIN_CHANCE_TO_HIT = 0.3f;
    private static final double MAX_CHANCE_TO_HIT = 0.95f;

    private static final int MAX_STRENGTH = 9;
    private static final int MAX_HP = 100;

    public void start() {
        displayRules();

        setPlayers();

        int turnCounter = 0;
        while (!isSomeoneLostGame()) {
            if (turnCounter++ % 2 == 0) {
                makeMove(firstPlayer);
            } else {
                makeMove(secondPlayer);
            }
        }
    }

    private void makeMove(Player playerMakingMove) {
        Methods.line("-");
        System.out.println(
                "Remaining health: \n" +
                        firstPlayer.getName() + ": " + firstPlayer.getHp() +
                        "\n" + secondPlayer.getName() + ": " + secondPlayer.getHp() +
                        "\n\n" + playerMakingMove.getName() + "'s turn!");
        System.out.println("Choose the strength of a punch from 1 to " + MAX_STRENGTH + ": ");
        String strength;
        while (!isInputProper(strength = Methods.getLine(), MAX_STRENGTH)) {
        }
        hit(Integer.parseInt(strength), playerMakingMove == firstPlayer ? secondPlayer : firstPlayer);
    }

    private void hit(int power, Player playerToGetHit) {
        String s;
        if (power == 1) s = "";
        else s = "s";
        if (Methods.getRandDouble() < getChance(power)) {
            System.out.println(
                    "You " + Colors.CYAN.code() + "got him " + Colors.RESET.code() + " with a " + getFormattedChance(power) + " chance to hit! \n" +
                            playerToGetHit.getName() + " looses " + power + " health point" + s + "!");
            playerToGetHit.setHp((byte) (playerToGetHit.getHp() - power));
        } else {
            System.out.println("Unfortunately, " + Colors.RED.code() + "you missed" + Colors.RESET.code() + " with a " + getFormattedChance(power) + " chance to hit!");
        }
    }

    private boolean isSomeoneLostGame() {
        if (firstPlayer.getHp() <= 0) {
            endgameMessage(firstPlayer);
            return true;
        } else if (secondPlayer.getHp() <= 0) {
            endgameMessage(secondPlayer);
            return true;
        } else
            return false;
    }

    private void endgameMessage(Player deadPlayer) {
        Methods.line("=");
        System.out.println(
                deadPlayer.getName() + Colors.RED.code() + " looses the game... \n" + Colors.RESET.code() +
                        "Better luck to you next time!" +
                        "\n\n" +  (deadPlayer == firstPlayer ? secondPlayer.getName() : firstPlayer.getName()) + Colors.CYAN.code() + " wins! \n" + Colors.RESET.code() +
                        "Congratulations!");
    }

    private void setPlayers() {
        firstPlayer = new Player();
        secondPlayer = new Player();

        // first player's name setting section
        System.out.println("Enter first player's name: ");
        String name;
        while ((name = Methods.getLine()).isEmpty() || name.replaceAll(" ", "").isEmpty()) { // skipping empty strings
            System.out.println("Please, enter a valid name: ");
        }
        firstPlayer.setName(name, Colors.BLUE);

        // second player's name setting section
        System.out.println("Enter second player's name: ");
        while ((name = Methods.getLine()).isEmpty() ||
                name.replaceAll(" ", "").isEmpty() ||
                name.equals(firstPlayer.getName())) {
            System.out.println(name.equals(firstPlayer.getName()) ?
                    "Players must not have same names! " : "Please, enter a valid name: ");
        }
        secondPlayer.setName(name, Colors.PURPLE);

        // HP setting section
        System.out.println("Enter health amount from 1 to " + MAX_HP + " for each player: ");
        byte health;
        String input;
        do {
            input = Methods.getLine();
        } while (!isInputProper(input, MAX_HP));
        health = (byte) Integer.parseInt(input);
        firstPlayer.setHp(health);
        secondPlayer.setHp(health);
    }

    private double getChance(int power) {
        double section = (MAX_CHANCE_TO_HIT - MIN_CHANCE_TO_HIT) / (MAX_STRENGTH - 1);
        return MIN_CHANCE_TO_HIT + section * (MAX_STRENGTH - power);
    }

    private String getFormattedChance(int power) {
        BigDecimal bigDecimal = BigDecimal.valueOf(getChance(power) * 100);
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
        return Colors.GREEN.code() + bigDecimal.doubleValue() + "%" + Colors.RESET.code();
    }

    private boolean isInputProper(String input, int upperBound) {
        if (input.length() <= Integer.toString(upperBound).length() && // not too long
                NumberUtils.isCreatable(input) && // is a number
                Integer.parseInt(input) > 0 && // positive
                Integer.parseInt(input) <= upperBound) { // not too big
            return true;
        } else {
            System.out.println("Please, enter a valid number from 1 to " + upperBound + ": ");
            return false;
        }
    }

    private void displayRules() {
        Methods.line("-");
        System.out.println("In this game your goal is to beat your opponent up. \n" +
                "Each turn you have to punch him with strength measured from 1 to " + MAX_STRENGTH + ". \n" +
                "If you don't miss the enemy, then he will lose health equal to your punch strength, \n" +
                "but aware that stronger the punch then less the chance to hit. \n" +
                "Here is the list of probabilities to hit your opponent depending on the punch power: ");
        for (int i = 1; i <= MAX_STRENGTH; i++) {
            System.out.println("Strength: " + i + " -> probability: " + getFormattedChance(i));
        }
        Methods.line("-");
    }

    private class Player {
        private String name;
        private byte hp;

        private String getName() {
            return name;
        }

        private void setName(String name, Colors color) {
            this.name = color.code() + name.replaceFirst(
                    Character.toString(name.charAt(0)),
                    Character.toString(Character.toUpperCase(name.charAt(0)))
                            .replaceAll(" ", "")) + Colors.RESET.code();
        }

        private byte getHp() {
            return hp;
        }

        private void setHp(byte hp) {
            this.hp = hp;
        }
    }

    private enum Colors {
        RESET("\u001B[0m"),
        RED("\u001B[31m"),
        BLUE("\u001B[34m"),
        PURPLE("\033[0;95m"),
        CYAN("\033[0;96m"),
        GREEN("\033[0;92m");

        private String code;

        Colors(String code) {
            this.code = code;
        }

        private String code() {
            return code;
        }
    }
}
