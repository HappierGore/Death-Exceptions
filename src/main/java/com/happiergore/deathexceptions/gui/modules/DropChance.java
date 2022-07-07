package com.happiergore.deathexceptions.gui.modules;

/**
 *
 * @author HappierGore
 */
public class DropChance {

    public static boolean dropChance(int dropChance) {
        float probability = (float) Math.random() * 100;
        //System.out.println("Chance : " + probability + "| DropChance: " + dropChance);
        return (probability > dropChance);
    }
}
