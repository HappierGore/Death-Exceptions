package com.happiergore.deathexceptions.gui.items;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappierGore
 */
public abstract class ItemGUI {

    private Inventory inventory;
    private ItemStack item;
    private int slot;

    public ItemGUI(Inventory inventory, int slot) {
        this.inventory = inventory;
        this.item = this.generateMainItem();
        this.slot = slot;
    }

    public abstract ItemStack generateMainItem();

    //*****************
    //      GETTERS
    //*****************
    public Inventory getInventory() {
        return inventory;
    }

    public ItemStack getItem() {
        return item;
    }

    public int getSlot() {
        return slot;
    }

    //*****************
    //      SETTERS
    //*****************
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

}
