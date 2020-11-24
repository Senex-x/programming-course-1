package OtherWorks.GamePolyRework;

public class Player {
    private String name;
    private int hp;

    public Player(String name, int hp) {
        this.name = name;
        this.hp = hp;
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

}