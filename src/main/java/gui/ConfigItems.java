package gui;

import gui.items.Back;
import gui.items.DisplayName.DisableDisplay;
import gui.items.DisplayName.EnableDisplay;
import gui.items.ItemGUI;
import gui.items.Enchantments.DisableEnchants;
import gui.items.NBT.DisableNBT;
import gui.items.Enchantments.EnableEnchants;
import gui.items.NBT.EnableNBT;
import gui.items.ItemFlags;
import gui.items.Lore.DisableLore;
import gui.items.Lore.EnableLore;
import java.util.ArrayList;
import java.util.List;
import mysqlite.ItemDB;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappierGore
 */
public final class ConfigItems {

    //All Instances
    List<ItemGUI> allItems = new ArrayList<>();

    //NBT Option
    private EnableNBT enableNBT;
    private DisableNBT disableNBT;

    //Enchantments Option
    private EnableEnchants enableEnch;
    private DisableEnchants disableEnch;

    //Lore option
    private EnableLore enableLore;
    private DisableLore disableLore;

    //Displayname option
    private EnableDisplay enableDisplay;
    private DisableDisplay disableDisplay;

    //Back option
    private Back back;

    private final ItemStack configureItem;

    public ConfigItems(ItemStack configureItem, Inventory inventory, Player player) {
        this.configureItem = configureItem;
        loadNBTItems(configureItem, inventory);
        loadEnchantItems(configureItem, inventory);
        loadLoreItems(configureItem, inventory);
        loadDisplayItems(configureItem, inventory);
        loadBack(player, inventory);
    }

    public void evaluate(ItemStack item) {
        for (ItemGUI entry : this.allItems) {
            if (entry.getItem().equals(item)) {
                entry.onClick();
                return;
            }
        }

    }

    //***********************
    //   LOADING FUNCTIONS
    //***********************
    private void loadNBTItems(ItemStack configureItem, Inventory inventory) {
        int slot = 10;
        enableNBT = new EnableNBT(configureItem, inventory, slot);
        disableNBT = new DisableNBT(configureItem, inventory, slot);

        switchItems(enableNBT, disableNBT, ItemFlags.IgnoreNBT);
    }

    private void loadEnchantItems(ItemStack configureItem, Inventory inventory) {
        int slot = 12;
        this.enableEnch = new EnableEnchants(configureItem, inventory, slot);
        this.disableEnch = new DisableEnchants(configureItem, inventory, slot);
        switchItems(this.enableEnch, this.disableEnch, ItemFlags.IgnoreEnchantments);
    }

    private void loadLoreItems(ItemStack configureItem, Inventory inventory) {
        int slot = 14;
        this.enableLore = new EnableLore(configureItem, inventory, slot);
        this.disableLore = new DisableLore(configureItem, inventory, slot);
        switchItems(this.enableLore, this.disableLore, ItemFlags.IgnoreLore);
    }

    private void loadDisplayItems(ItemStack configureItem, Inventory inventory) {
        int slot = 16;
        this.enableDisplay = new EnableDisplay(configureItem, inventory, slot);
        this.disableDisplay = new DisableDisplay(configureItem, inventory, slot);
        switchItems(this.enableDisplay, this.disableDisplay, ItemFlags.IgnoreDisplayName);
    }

    private void loadBack(Player player, Inventory inventory) {
        int slot = 22;
        this.back = new Back(inventory, slot, player);
        this.allItems.add(back);
        back.loadItem();
    }

    //***********************
    //      BEHAVIOUR
    //***********************
    private void switchItems(ItemGUI enabledItem, ItemGUI disabledItem, ItemFlags flag) {

        enabledItem.itemsToChange.add(disabledItem);
        disabledItem.itemsToChange.add(enabledItem);

        if (ItemDB.getFlags(this.configureItem).contains(flag)) {
            disabledItem.loadItem();
//            enabledItem.getInventory().setItem(disabledItem.getSlot(), disabledItem.getItem());
        } else {
            enabledItem.loadItem();
            //          enabledItem.getInventory().setItem(enabledItem.getSlot(), enabledItem.getItem());
        }
        this.allItems.add(enabledItem);
        this.allItems.add(disabledItem);
    }

}
