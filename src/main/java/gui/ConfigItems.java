package gui;

import gui.items.Back;
import gui.items.DisableDisplay;
import gui.items.DisableEnchants;
import gui.items.DisableLore;
import gui.items.DisableNBT;
import gui.items.types.Behaviour;
import helper.ItemUtils;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappierGore
 */
public final class ConfigItems {

    //All Instances
    List<Behaviour> allItems = new ArrayList<>();

    private final ItemStack configureItem;

    public ConfigItems(ItemStack configureItem, Inventory inventory, Player player) {
        this.configureItem = configureItem;
        this.allItems.add(new DisableDisplay(inventory, 16, this.configureItem));
        this.allItems.add(new DisableLore(inventory, 14, this.configureItem));
        this.allItems.add(new DisableEnchants(inventory, 12, this.configureItem));
        this.allItems.add(new DisableNBT(inventory, 10, this.configureItem));
        this.allItems.add(new Back(inventory, 22, player));
    }

    public void evaluate(ItemStack item) {
        for (Behaviour entry : this.allItems) {
            if (ItemUtils.compareItems(entry.getItem(), item)) {
                entry.onClick();
                return;
            }
        }
    }

    public void loadAllItems() {
        allItems.forEach(item -> {
            ((Behaviour) item).onLoad();
        });
    }

}
