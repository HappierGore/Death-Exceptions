package gui.items;

import gui.ItemGUI;
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

        final List<String> lore = new ArrayList<>();
        lore.add(TextUtils.parseColor("&bPara comparar toda la información NBT"));
        lore.add(TextUtils.parseColor("&bdesactiva esta opción"));

        this.setItem(this.generateItem(enchants, Material.BOOK, "&bComparar NBT Tags", lore, flags));

        this.configItem = configItem;
    }

    @Override
    public void onClick() {
        ItemDB itemDB = new ItemDB();
        List<String> flags = itemDB.getFlags(this.configItem);
        if (flags.contains("IgnoreNBT")) {
            flags.remove("IgnoreNBT");
        }
        this.changeItem(this.itemsToChange.get(0).getItem());
        itemDB.updateFlags(this.configItem, flags);
    }

}
