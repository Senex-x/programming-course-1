package OtherWorks.GamePolyRework;

import java.util.Scanner;

public class Game {
    private int defaultHpValue;

    private Player currentPlayer;
    private Player anotherPlayer;

    private Attack[] availableAttacks;

    public Game(int defaultHpValue, Attack[] availableAttacks) {
        this.defaultHpValue = defaultHpValue;
        this.availableAttacks = availableAttacks;
    }


    public void makeNewPlayers() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter first player name:");
        currentPlayer = new Player(sc.nextLine(), defaultHpValue);
        System.out.println("Enter first player name:");
        anotherPlayer = new Player(sc.nextLine(), defaultHpValue);
    }

    public void makeGame() {
        while (checkAlive())
            makeTurn();
        printWinner();
    }


    private void makeTurn() {
        Scanner sc = new Scanner(System.in);

        printTurnInfo();
        printAttackInfo();

        int attackId = sc.nextInt();
        attackId = handleAttackInput(attackId);
        proceedPlayerAttack(attackId);

        switchPlayers();
    }

    private boolean checkAlive() {
        return currentPlayer.isAlive() && anotherPlayer.isAlive();
    }

    private void printLine() {
        System.out.println();
        System.out.println("-----------------------------");
        System.out.println();
    }

    private void printTurnInfo() {
        printLine();
        printPlayersInfo();

        System.out.println("Now it's " + currentPlayer.getName() + " turn");
    }

    private void printPlayersInfo() {
        System.out.println(currentPlayer.getName() + ": " + currentPlayer.getHp());
        System.out.println(anotherPlayer.getName() + ": " + anotherPlayer.getHp());
    }

    private void printAttackInfo() {
        System.out.println();
        System.out.println("Choose your attack:");

        for (int i = 1; i <= availableAttacks.length; i++) {
            System.out.println(i + ": " + availableAttacks[i - 1]);
        }
    }

    private int handleAttackInput(int attackId) {
        attackId--;
        if (attackId < 0)
            attackId = 0;
        if (attackId >= availableAttacks.length)
            attackId = availableAttacks.length - 1;
        return attackId;
    }

    private void proceedPlayerAttack(int attackId) {
        Attack atk = availableAttacks[attackId];
        atk.doAttack(currentPlayer, anotherPlayer);
    }

    private void switchPlayers() {
        Player temp = currentPlayer;
        currentPlayer = anotherPlayer;
        anotherPlayer = temp;
    }

    private void printWinner() {
        printLine();
        printPlayersInfo();

        Player winner = currentPlayer.isAlive() ? currentPlayer : anotherPlayer;
        System.out.println("Winner: " + winner.getName());
    }
}