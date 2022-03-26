package gui.items.configMenu;

import gui.items.ItemFlags;
import gui.items.types.SwitchItem;
import helper.ItemUtils;
import helper.TextUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import sqlite.ItemDB;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappierGore
 */
public class DisableDisplay extends SwitchItem {

    private final ItemStack configItem;
    private final ItemFlags flag = ItemFlags.IgnoreDisplayName;

    public DisableDisplay(Inventory inv, int slot, ItemStack configItem) {
        super(inv, slot);
        this.configItem = configItem;
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        super.onClick(e);
        if (!this.loadCondition()) {
            ItemDB.addFlag(this.configItem, flag);
        } else {
            ItemDB.removeFlag(this.configItem, flag);
        }
    }

    @Override
    public ItemStack generateSwitchItem() {

        final Map<Enchantment, Integer> enchants = new HashMap<>();
        enchants.put(Enchantment.LURE, 1);

        final List<ItemFlag> flags = new ArrayList<>();
        flags.add(ItemFlag.HIDE_ENCHANTS);

        final String displayName = TextUtils.parseColor("&6Compare displayname");

        final List<String> lore = new ArrayList<>();
        lore.add(TextUtils.parseColor("&bTo compare displayname"));
        lore.add(TextUtils.parseColor("&bdisable this option."));

        return ItemUtils.generateItem(enchants, Material.NAME_TAG, displayName, lore, flags);

    }

    @Override
    public ItemStack generateMainItem() {

        final String displayName = TextUtils.parseColor("&6Ignore displayname");

        final List<String> lore = new ArrayList<>();
        lore.add(TextUtils.parseColor("&bTo ignore displayname"));
        lore.add(TextUtils.parseColor("&benable this option."));

        return ItemUtils.generateItem(null, Material.NAME_TAG, displayName, lore, null);
    }

    @Override
    public boolean loadCondition() {
        return ItemDB.getFlags(this.configItem).contains(flag);
    }

}
