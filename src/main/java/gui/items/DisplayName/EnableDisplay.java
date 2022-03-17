package gui.items.DisplayName;

import gui.items.ItemFlags;
import gui.items.ItemGUI;
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
public class EnableDisplay extends ItemGUI {

    final ItemStack configItem;

    public EnableDisplay(ItemStack configItem, Inventory inventory, int slot) {
        super(inventory, slot);

        final String displayName = TextUtils.parseColor("&6Ignore displayname");

        final List<String> lore = new ArrayList<>();
        lore.add(TextUtils.parseColor("&bTo ignore displayname"));
        lore.add(TextUtils.parseColor("&benablet this option."));

        this.setItem(this.generateItem(null, Material.NAME_TAG, displayName, lore, null));

        this.configItem = configItem;
    }

    @Override
    public void onClick() {
        ItemDB.addFlag(this.configItem, ItemFlags.IgnoreDisplayName);
        this.changeItem(this.itemsToChange.get(0).getItem());
    }

}
