package gui.items.NBT.Lore;

import gui.items.ItemFlags;
import gui.items.ItemGUI;
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
public class DisableLore extends ItemGUI {

    final ItemStack configItem;

    public DisableLore(ItemStack configItem, Inventory inventory, int slot) {
        super(inventory, slot);
        final Map<Enchantment, Integer> enchants = new HashMap<>();
        enchants.put(Enchantment.LURE, 1);

        final List<ItemFlag> flags = new ArrayList<>();
        flags.add(ItemFlag.HIDE_ENCHANTS);

        final String displayName = TextUtils.parseColor("&6Compare lore.");

        final List<String> lore = new ArrayList<>();
        lore.add(TextUtils.parseColor("&bTo compare lore"));
        lore.add(TextUtils.parseColor("&bunable this option."));

        this.setItem(this.generateItem(enchants, Material.OAK_SIGN, displayName, lore, flags));

        this.configItem = configItem;
    }

    @Override
    public void onClick() {
        ItemDB itemDB = new ItemDB();
        itemDB.removeFlag(this.configItem, ItemFlags.IgnoreLore);
        this.changeItem(this.itemsToChange.get(0).getItem());
    }

}
