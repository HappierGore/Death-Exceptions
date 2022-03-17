package gui.items.NBT;

import gui.items.ItemGUI;
import gui.items.ItemFlags;
import helper.TextUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mysqlite.ItemDB;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappierGore
 */
public class DisableNBT extends ItemGUI {

    private final ItemStack configItem;

    public DisableNBT(ItemStack configItem, Inventory inventory, int slot) {
        super(inventory, slot);

        final Map<Enchantment, Integer> enchants = new HashMap<>();
        enchants.put(Enchantment.LURE, 1);

        final List<ItemFlag> flags = new ArrayList<>();
        flags.add(ItemFlag.HIDE_ENCHANTS);

        final String displayName = TextUtils.parseColor("&6Compare NBT Tags.");
        final List<String> lore = new ArrayList<>();
        lore.add(TextUtils.parseColor("&bTo compare NBT information"));
        lore.add(TextUtils.parseColor("&bunable this option."));

        this.setItem(this.generateItem(enchants, Material.BOOK, displayName, lore, flags));

        this.configItem = configItem;
    }

    @Override
    public void onClick() {
        ItemDB.removeFlag(this.configItem, ItemFlags.IgnoreNBT);
        this.changeItem(this.itemsToChange.get(0).getItem());
    }

}
