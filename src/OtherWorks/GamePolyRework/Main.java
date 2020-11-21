package OtherWorks.GamePolyRework;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter hp value:");

        Attack[] availableAttacksList = {
                new SimpleAttack(),
                new HeavyAttack(),
                new InstantAttack()
        };

        Game game = new Game(sc.nextInt(), availableAttacksList);

        while (true) {
            game.makeNewPlayers();
            game.makeGame();

            System.out.println("Do you want to play another time?");
            System.out.println("Enter 0 for yes, any other number for exit");

            int answer = sc.nextInt();
            if (answer != 0)
                break;
        }
    }
}
