package OtherWorks.GamePolyRework;

public class InstantAttack extends Attack {
    private static final int CHANCE = 9;

    @Override
    public String getName() {
        return "Kill other player with " + (10 - CHANCE) * 10 + "% chance";
    }

    public InstantAttack() {
        super.setCHANCE(CHANCE);
        super.setDAMAGE(Integer.MAX_VALUE);
    }
}