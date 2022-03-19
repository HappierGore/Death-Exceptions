package modules;

/**
 *
 * @author HappierGore
 */
public class DropChance {

    private final float dropChance;

    public DropChance(int dropChance) {
        this.dropChance = dropChance;
    }

    public boolean dropChance() {
        return (Math.random() * 100 > dropChance);
    }
}
