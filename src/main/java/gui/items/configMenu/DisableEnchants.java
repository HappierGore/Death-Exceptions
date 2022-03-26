package gui.items.configMenu;

import gui.items.ItemFlags;
import gui.items.types.SwitchItem;
import helper.ItemUtils;
import helper.TextUtils;
import helper.VersionManager;
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
public class DisableEnchants extends SwitchItem {

    private final ItemStack configItem;
    private final ItemFlags flag = ItemFlags.IgnoreEnchantments;

    public DisableEnchants(Inventory inv, int slot, ItemStack configItem) {
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

        final String displayName = TextUtils.parseColor("&6Compare enchantments");
        final List<String> lore = new ArrayList<>();
        lore.add(TextUtils.parseColor("&bTo compare enchantments"));
        lore.add(TextUtils.parseColor("&bdisable this option."));

        Material material = Material.getMaterial(VersionManager.parseMaterial("ENCHANTING_TABLE"));

        return ItemUtils.generateItem(enchants, material, displayName, lore, flags);
    }

    @Override
    public ItemStack generateMainItem() {

        final String displayName = TextUtils.parseColor("&6Ignore enchantments");

        final List<String> lore = new ArrayList<>();
        lore.add(TextUtils.parseColor("&bTo ignore enchantments"));
        lore.add(TextUtils.parseColor("&benable this option."));

        Material material = Material.getMaterial(VersionManager.parseMaterial("ENCHANTING_TABLE"));

        return ItemUtils.generateItem(null, material, displayName, lore, null);
    }

    @Override
    public boolean loadCondition() {
        return ItemDB.getFlags(configItem).contains(flag);
    }

}
