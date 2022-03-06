package gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author HappierGore
 */
public abstract class ItemGUI {

    private Inventory inventory;
    private ItemStack item;
    private int slot;
    public final List<ItemGUI> itemsToChange = new ArrayList<>();

    public ItemGUI(Inventory inventory, int slot) {
        this.slot = slot;
        this.inventory = inventory;
    }

    public void changeItem(ItemStack newItem) {
        inventory.setItem(slot, newItem);
    }

    public void returnOriginalItem() {
        inventory.setItem(slot, item);
    }

    public final ItemStack generateItem(Map<Enchantment, Integer> enchantments, Material material, String displayName, List<String> lore, List<ItemFlag> flags) {
        ItemMeta itemMeta = Bukkit.getItemFactory().getItemMeta(material);

        if (enchantments != null) {
            enchantments.forEach((enchant, level) -> {
                itemMeta.addEnchant(enchant, level, true);
            });
        }

        if (displayName != null) {
            itemMeta.setDisplayName(displayName);
        }

        if (lore != null) {
            itemMeta.setLore(lore);
        }

        if (flags != null) {
            flags.forEach(flag -> {
                itemMeta.addItemFlags(flag);
            });
        }

        ItemStack newItem = new ItemStack(material);
        newItem.setItemMeta(itemMeta);
        return newItem;
    }

    public abstract void onClick();

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
