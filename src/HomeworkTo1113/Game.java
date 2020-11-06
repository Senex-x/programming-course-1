package HomeworkTo1113;

import Methods.Methods;

class GameTest {
    public static void main(String[] args) {
        Game newGame = new Game();
        newGame.start();
    }
}

public class Game {
    private Player firstPlayer;
    private Player secondPlayer;

    public void start() {
        setPlayers();

        int turnCounter = 0;
        boolean isRetry = false;
        while (!isSomeoneLost()) {
            if (!isRetry) {
                Methods.line();
                System.out.println(
                        "Remaining health: \n" +
                                firstPlayer.getName() + ": " + firstPlayer.getHp() + "\n" +
                                secondPlayer.getName() + ": " + secondPlayer.getHp());
                Methods.line();

                if (turnCounter % 2 == 0) {
                    System.out.println(
                            firstPlayer.getName() + "'s turn!");

                } else {
                    System.out.println(
                            secondPlayer.getName() + "'s turn!");
                }
            }
            System.out.println("Choose the strength of a punch from 1 to 8: ");
            int strength = Methods.getInt();
            if (strength < 1 || strength > 8) {
                System.out.println("Please, choose punch power according to the rules!");
                isRetry = true;
                continue;
            }
            isRetry = false;
            hit(strength, turnCounter++ % 2 == 0);
        }
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
        System.out.println("Hit chance is: " + Math.round(getChance(power) * 100) + "%");
        if (Methods.getRandDouble() < getChance(power)) {
            if (power == 1) s = "";
            else s = "s";
            if (isFirstPlayerTurn) {
                System.out.println(
                        "You got him! \n" +
                                secondPlayer.getName() + " looses " + power + " health point" + s + "!");
                secondPlayer.setHp(secondPlayer.getHp() - power);
            } else {
                System.out.println(
                        "You got him! \n" +
                                firstPlayer.getName() + " looses " + power + " health point" + s + "!");
                firstPlayer.setHp(firstPlayer.getHp() - power);
            }
        }
    }

    private double getChance(int power) {
        final double MIN_CHANCE = 0.4f;
        final double MAX_CHANCE = 0.95f;

        double section = (MAX_CHANCE - MIN_CHANCE) / 7;
        double chance = MIN_CHANCE + section * (8 - power);

        return chance;
    }

    private void setPlayers() {
        firstPlayer = new Player();
        secondPlayer = new Player();
        System.out.println("Enter first player's name: ");
        firstPlayer.setName(Methods.getString());
        System.out.println("Enter second player's name: ");
        secondPlayer.setName(Methods.getString());
        System.out.println("Enter health amount of each player: ");
        int health = Methods.getInt();
        firstPlayer.setHp(health);
        secondPlayer.setHp(health);
    }


    private class Player {
        private String name;
        private int hp;

        private String getName() {
            return name;
        }

        private void setName(String name) {
            this.name = name.replaceFirst(
                    Character.toString(name.charAt(0)),
                    Character.toString(Character.toUpperCase(name.charAt(0))));
        }

        private int getHp() {
            return hp;
        }

        private void setHp(int hp) {
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
