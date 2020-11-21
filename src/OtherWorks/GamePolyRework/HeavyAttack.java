package OtherWorks.GamePolyRework;

public class HeavyAttack extends Attack {
    private static final int CHANCE = 5;
    private static final int DAMAGE = 10;

    @Override
    public String getName() {
        return "Do " + DAMAGE + " damage with " + (10 - CHANCE) * 10 + "% chance";
    }

    public HeavyAttack() {
        super.setCHANCE(CHANCE);
        super.setDAMAGE(DAMAGE);
    }
}