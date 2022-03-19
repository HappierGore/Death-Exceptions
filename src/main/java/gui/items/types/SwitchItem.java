package gui.items.types;

import helper.ItemUtils;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappierGore
 */
public class SwitchItem extends Behaviour {

    private ItemStack initialItem;
    private ItemStack switchItem;
    private boolean loadCondition;

    /**
     * Initialize the SwitchItem Class, it's like the constructor, but for
     * problems, constructor won't help.
     *
     * @param inv Inventory
     * @param slot slot
     * @param switchItem switchItem
     * @param initialItem initialItem
     * @param loadCondition Condition to test to make thw switch
     */
    public void Initialize(Inventory inv, int slot, ItemStack initialItem, ItemStack switchItem, boolean loadCondition) {
        super.Initialize(inv, initialItem, slot);
        this.initialItem = initialItem;
        this.switchItem = switchItem;
        this.loadCondition = loadCondition;
    }

    private void updateItem() {
        this.setItem(this.getInventory().getItem(this.getSlot()));
    }

    @Override
    public void onClick() {
        ItemStack toSwitch = ItemUtils.compareItems(this.getItem(), initialItem) ? switchItem : initialItem;
        this.getInventory().setItem(getSlot(), toSwitch);
        updateItem();
    }

    @Override
    public void onLoad() {
        if (this.loadCondition) {
            getInventory().setItem(getSlot(), switchItem);
        } else {
            getInventory().setItem(getSlot(), initialItem);
        }
        updateItem();
    }
}
