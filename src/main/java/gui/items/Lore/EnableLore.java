package gui.items.Lore;

import gui.items.ItemFlags;
import gui.items.ItemGUI;
import helper.TextUtils;
import helper.VersionManager;
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
public class EnableLore extends ItemGUI {

    final ItemStack configItem;

    public EnableLore(ItemStack configItem, Inventory inventory, int slot) {
        super(inventory, slot);
        final String displayName = TextUtils.parseColor("&6Ignore Lore.");
        final List<String> lore = new ArrayList<>();
        lore.add(TextUtils.parseColor("&bTo ignore lore"));
        lore.add(TextUtils.parseColor("&benable this option."));

        Material material = Material.getMaterial(VersionManager.parseMaterial("OAK_SIGN"));
        this.setItem(this.generateItem(null, material, displayName, lore, null));

        this.configItem = configItem;
    }

    @Override
    public void onClick() {
        ItemDB itemDB = new ItemDB();
        itemDB.addFlag(this.configItem, ItemFlags.IgnoreLore);
        this.changeItem(this.itemsToChange.get(0).getItem());
    }

}
