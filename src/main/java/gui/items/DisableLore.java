package gui.items;

import gui.items.types.SwitchItem;
import helper.ItemUtils;
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
public class DisableLore extends SwitchItem {

    private final ItemStack initialItem;
    private final ItemStack configItem;
    private final ItemStack switchItem;
    private boolean condition;
    private final ItemFlags flag = ItemFlags.IgnoreLore;

    public DisableLore(Inventory inv, int slot, ItemStack configItem) {
        this.initialItem = generateInitialItem();
        this.switchItem = generateSwitchItem();
        this.configItem = configItem;
        updateCondition();
        Initialize(inv, slot, this.initialItem, this.switchItem, condition);
    }

    @Override
    public void onClick() {
        super.onClick();
        if (!this.condition) {
            ItemDB.addFlag(this.configItem, flag);
        } else {
            ItemDB.removeFlag(this.configItem, flag);
        }
        this.updateCondition();
    }

    //**********************
    //      Helpers
    //**********************
    private void updateCondition() {
        this.condition = ItemDB.getFlags(configItem).contains(flag);
    }

    private ItemStack generateSwitchItem() {

        final Map<Enchantment, Integer> enchants = new HashMap<>();
        enchants.put(Enchantment.LURE, 1);

        final List<ItemFlag> flags = new ArrayList<>();
        flags.add(ItemFlag.HIDE_ENCHANTS);

        final String displayName = TextUtils.parseColor("&6Compare lore.");

        final List<String> lore = new ArrayList<>();
        lore.add(TextUtils.parseColor("&bTo compare lore"));
        lore.add(TextUtils.parseColor("&bdisable this option."));

        Material material = Material.getMaterial(VersionManager.parseMaterial("OAK_SIGN"));

        return ItemUtils.generateItem(enchants, material, displayName, lore, flags);
    }

    private ItemStack generateInitialItem() {

        final String displayName = TextUtils.parseColor("&6Ignore lore.");

        final List<String> lore = new ArrayList<>();
        lore.add(TextUtils.parseColor("&bTo ignore lore"));
        lore.add(TextUtils.parseColor("&benable this option."));

        Material material = Material.getMaterial(VersionManager.parseMaterial("OAK_SIGN"));

        return ItemUtils.generateItem(null, material, displayName, lore, null);
    }

}
