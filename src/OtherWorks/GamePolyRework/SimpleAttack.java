package OtherWorks.GamePolyRework;

public class SimpleAttack extends Attack {
    private static final int CHANCE = 0;
    private static final int DAMAGE = 5;

    @Override
    public String getName() {
        return "Do " + DAMAGE + " damage with " + (10 - CHANCE) * 10 + "% chance";
    }

    public SimpleAttack() {
        super.setCHANCE(CHANCE);
        super.setDAMAGE(DAMAGE);
    }


}
