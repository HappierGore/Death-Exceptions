package gui.modules;

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
        float probability = (float) Math.random() * 100;
        System.out.println("Chance : " + probability + "\nDropChance: " + this.dropChance);
        return (probability > dropChance);
    }
}
