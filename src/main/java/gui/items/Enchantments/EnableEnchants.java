package gui.items.Enchantments;

import gui.items.ItemGUI;
import gui.items.ItemFlags;
import helper.TextUtils;
import java.util.ArrayList;
import java.util.List;
import mysqlite.ItemDB;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappierGore
 */
public class EnableEnchants extends ItemGUI {

    private final ItemStack configItem;

    public EnableEnchants(ItemStack configItem, Inventory inventory, int slot) {
        super(inventory, slot);
        final String displayName = TextUtils.parseColor("&6Ignore enchantments.");
        final List<String> lore = new ArrayList<>();
        lore.add(TextUtils.parseColor("&bTo ignore enchantments"));
        lore.add(TextUtils.parseColor("&benable this option."));

        this.setItem(this.generateItem(null, Material.ENCHANTING_TABLE, displayName, lore, null));

        this.configItem = configItem;
    }

    @Override
    public void onClick() {
        ItemDB itemDB = new ItemDB();
        itemDB.addFlag(this.configItem, ItemFlags.IgnoreEnchantments);
        this.changeItem(this.itemsToChange.get(0).getItem());
    }

}
