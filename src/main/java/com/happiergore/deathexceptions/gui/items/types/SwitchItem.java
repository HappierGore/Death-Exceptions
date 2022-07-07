package com.happiergore.deathexceptions.gui.items.types;

import com.happiergore.deathexceptions.helper.ItemUtils;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappierGore
 */
public abstract class SwitchItem extends Behaviour {

    private final ItemStack initialItem;
    private ItemStack switchItem;

    public SwitchItem(Inventory inv, int slot) {
        super(inv, slot);
        this.initialItem = generateMainItem();
        this.switchItem = generateSwitchItem();
    }

    private void updateItem() {
        this.setItem(this.getInventory().getItem(this.getSlot()));
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        e.setCancelled(true);
        ItemStack toSwitch = ItemUtils.compareItems(this.getItem(), initialItem) ? switchItem : initialItem;
        this.getInventory().setItem(getSlot(), toSwitch);
        updateItem();
    }

    @Override
    public void onLoad() {
        if (this.loadCondition()) {
            getInventory().setItem(getSlot(), switchItem);
        } else {
            getInventory().setItem(getSlot(), initialItem);
        }
        updateItem();
    }

    public ItemStack getSwitchItem() {
        return switchItem;
    }

    public abstract boolean loadCondition();

    public abstract ItemStack generateSwitchItem();

    public void setSwitchItem(ItemStack switchItem) {
        this.switchItem = switchItem;
    }

}
