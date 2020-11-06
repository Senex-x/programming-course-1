package HomeworkTo1113;

import Methods.Methods;

class GameTest {
    public static void main(String[] args) {
        new Game().start();
    }
}

public class Game {
    Player firstPlayer;
    Player secondPlayer;
    public void start() {
        setPlayers();

        while(true) {

        }
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

        private boolean getHit(int power) {
            int damage = Methods.getRandInt(1,10);
        }

        private String getName() {
            return name;
        }

        private void setName(String name) {
            this.name = name;
        }

        private int getHp() {
            return hp;
        }

        private void setHp(int hp) {
            this.hp = hp;
        }
    }
}
