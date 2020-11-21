package OtherWorks.GamePolyRework;

import java.util.Random;

public abstract class Attack {
    public abstract String getName();

    private int CHANCE;

    private int DAMAGE;

    public void setCHANCE(int CHANCE) {
        this.CHANCE = CHANCE;
    }

    public void setDAMAGE(int DAMAGE) {
        this.DAMAGE = DAMAGE;
    }

    public void doAttack(Player attackingPlayer, Player otherPlayer) {
        Random random = new Random();
        if (random.nextInt(10) >= CHANCE) {
            otherPlayer.damage(DAMAGE);
            System.out.println(attackingPlayer.getName() + " made " + DAMAGE + " damage to " + otherPlayer.getName());
        } else {
            System.out.println(attackingPlayer.getName() + " missed");
        }
    }

    @Override
    public String toString() {
        return getName();
    }
}