package OtherWorks.GamePolyRework;

public class Player {
    private String name;
    private int hp;
    private static Attack[] attacks;

    public Player(String name, int hp, Attack[] attacks) {
        this.name = name;
        this.hp = hp;
        Player.attacks = attacks;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public void damage(int value) {
        hp -= value;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public Attack[] getAttacks() {
        return attacks;
    }

}