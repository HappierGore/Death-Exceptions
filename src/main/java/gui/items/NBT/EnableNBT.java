package gui.items.NBT;

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
public class EnableNBT extends ItemGUI {

    private final ItemStack configItem;

    public EnableNBT(ItemStack configItem, Inventory inventory, int slot) {
        super(inventory, slot);

        final String displayName = TextUtils.parseColor("&6Ignore NBT Tags.");
        final List<String> lore = new ArrayList<>();
        lore.add(TextUtils.parseColor("&bTo ignore NBT information"));
        lore.add(TextUtils.parseColor("&benable this option."));

        this.setItem(this.generateItem(null, Material.BOOK, displayName, lore, null));

        this.configItem = configItem;
    }

//**********************
//      Generators
//**********************
    @Override
    public void onClick() {
        ItemDB itemDB = new ItemDB();
        itemDB.addFlag(this.configItem, ItemFlags.IgnoreNBT);
        this.changeItem(this.itemsToChange.get(0).getItem());
    }
}
