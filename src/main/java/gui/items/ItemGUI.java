package gui.items;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappierGore
 */
public class ItemGUI {

    private Inventory inventory;
    private ItemStack item;
    private int slot;

    /**
     * Initialize the ItemGUI Class, it's like the constructor, but for
     * problems, constructor won't help.
     *
     * @param slot slot
     * @param item Item
     * @param inv Inventory
     */
    public void Initialize(Inventory inv, ItemStack item, int slot) {
        this.inventory = inv;
        this.item = item;
        this.slot = slot;
    }

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
