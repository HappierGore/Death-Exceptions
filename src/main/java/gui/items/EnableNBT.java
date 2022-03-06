package gui.items;

import gui.ItemGUI;
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

        final List<String> lore = new ArrayList<>();
        lore.add(TextUtils.parseColor("&bPara ignorar toda la información NBT"));
        lore.add(TextUtils.parseColor("&bactiva esta opción"));

        this.setItem(this.generateItem(null, Material.BOOK, "&bIgnorar NBT Tags", lore, null));

        this.configItem = configItem;
    }

//**********************
//      Generators
//**********************
    @Override
    public void onClick() {
        ItemDB itemDB = new ItemDB();
        List<String> flags = itemDB.getFlags(this.configItem);

        if (!flags.contains("IgnoreNBT")) {
            flags.add("IgnoreNBT");
        }
        this.changeItem(this.itemsToChange.get(0).getItem());
        itemDB.updateFlags(this.configItem, flags);
    }
}
