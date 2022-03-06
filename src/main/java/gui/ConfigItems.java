package gui;

import gui.items.DisableNBT;
import gui.items.EnableNBT;
import mysqlite.ItemDB;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappierGore
 */
public final class ConfigItems {

    //NBT Option
    private EnableNBT enableNBT;
    private DisableNBT disableNBT;

    public ConfigItems(ItemStack configureItem, Inventory inventory) {
        loadNBTItems(configureItem, inventory);
    }

    public void evaluate(ItemStack item) {
        if (item.equals(this.enableNBT.getItem())) {
            this.enableNBT.onClick();
            return;
        }
        if (item.equals(this.disableNBT.getItem())) {
            this.disableNBT.onClick();
        }
    }

    //***********************
    //   LOADING FUNCTIONS
    //***********************
    private void loadNBTItems(ItemStack configureItem, Inventory inventory) {
        int slot = 11;
        enableNBT = new EnableNBT(configureItem, inventory, slot);
        disableNBT = new DisableNBT(configureItem, inventory, slot);

        this.disableNBT.itemsToChange.add(this.enableNBT);
        this.enableNBT.itemsToChange.add(this.disableNBT);

        if (new ItemDB().getFlags(configureItem).contains("IgnoreNBT")) {
            inventory.setItem(this.disableNBT.getSlot(), this.disableNBT.getItem());
        } else {
            inventory.setItem(this.enableNBT.getSlot(), this.enableNBT.getItem());
        }
    }

}
