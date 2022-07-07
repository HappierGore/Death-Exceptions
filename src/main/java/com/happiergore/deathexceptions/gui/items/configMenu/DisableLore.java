package com.happiergore.deathexceptions.gui.items.configMenu;

import com.happiergore.deathexceptions.gui.items.ItemFlags;
import com.happiergore.deathexceptions.gui.items.types.SwitchItem;
import com.happiergore.deathexceptions.helper.DEXItem;
import com.happiergore.deathexceptions.helper.ItemUtils;
import com.happiergore.deathexceptions.helper.TextUtils;
import com.happiergore.deathexceptions.helper.VersionManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.happiergore.deathexceptions.sqlite.ItemDAO;
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
public class DisableLore extends SwitchItem {

    private final DEXItem configItem;
    private final ItemFlags flag = ItemFlags.IgnoreLore;

    public DisableLore(Inventory inv, int slot, ItemStack configItem) {
        super(inv, slot);
        this.configItem = ItemDAO.getItemFromDB(configItem);
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        super.onClick(e);
        if (!this.loadCondition()) {
            configItem.getFlags().add(flag);
        } else {
            configItem.getFlags().remove(flag);
        }
        ItemDAO.updateItemInfo(this.configItem);
    }

    @Override
    public ItemStack generateSwitchItem() {

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

    @Override
    public ItemStack generateMainItem() {

        final String displayName = TextUtils.parseColor("&6Ignore lore.");

        final List<String> lore = new ArrayList<>();
        lore.add(TextUtils.parseColor("&bTo ignore lore"));
        lore.add(TextUtils.parseColor("&benable this option."));

        Material material = Material.getMaterial(VersionManager.parseMaterial("OAK_SIGN"));

        return ItemUtils.generateItem(null, material, displayName, lore, null);
    }

    @Override
    public boolean loadCondition() {
        return configItem.getFlags().contains(flag);
    }

}
