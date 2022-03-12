package gui.items.Enchantments;

import gui.items.ItemGUI;
import gui.items.ItemFlags;
import helper.TextUtils;
import helper.VersionManager;
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
public class DisableEnchants extends ItemGUI {

    private final ItemStack configItem;

    public DisableEnchants(ItemStack configItem, Inventory inventory, int slot) {
        super(inventory, slot);

        final Map<Enchantment, Integer> enchants = new HashMap<>();
        enchants.put(Enchantment.LURE, 1);

        final List<ItemFlag> flags = new ArrayList<>();
        flags.add(ItemFlag.HIDE_ENCHANTS);

        final String displayName = TextUtils.parseColor("&6Compare enchantments");
        final List<String> lore = new ArrayList<>();
        lore.add(TextUtils.parseColor("&bTo compare enchantments"));
        lore.add(TextUtils.parseColor("&benable this option."));

        Material material = Material.getMaterial(VersionManager.parseMaterial("ENCHANTING_TABLE"));

        this.setItem(this.generateItem(enchants, material, displayName, lore, flags));

        this.configItem = configItem;
    }

    @Override
    public void onClick() {
        ItemDB itemDB = new ItemDB();
        itemDB.removeFlag(this.configItem, ItemFlags.IgnoreEnchantments);
        this.changeItem(this.itemsToChange.get(0).getItem());
    }

}
