package com.happiergore.deathexceptions.gui.items.configMenu;

import com.happiergore.deathexceptions.gui.items.ItemFlags;
import com.happiergore.deathexceptions.gui.items.types.SwitchItem;
import com.happiergore.deathexceptions.helper.DEXItem;
import com.happiergore.deathexceptions.helper.ItemUtils;
import com.happiergore.deathexceptions.helper.TextUtils;
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
public class DisableNBT extends SwitchItem {

    private final DEXItem configItem;
    private final ItemFlags flag = ItemFlags.IgnoreNBT;

    public DisableNBT(Inventory inv, int slot, ItemStack configItem) {
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

        final String displayName = TextUtils.parseColor("&6Compare NBT Tags.");
        final List<String> lore = new ArrayList<>();
        lore.add(TextUtils.parseColor("&bTo compare NBT information"));
        lore.add(TextUtils.parseColor("&bdisable this option."));

        return ItemUtils.generateItem(enchants, Material.BOOK, displayName, lore, flags);
    }

    @Override
    public ItemStack generateMainItem() {

        final String displayName = TextUtils.parseColor("&6Ignore NBT Tags.");
        final List<String> lore = new ArrayList<>();
        lore.add(TextUtils.parseColor("&bTo ignore NBT information"));
        lore.add(TextUtils.parseColor("&benable this option."));

        return ItemUtils.generateItem(null, Material.BOOK, displayName, lore, null);
    }

    @Override
    public boolean loadCondition() {
        return configItem.getFlags().contains(flag);
    }

}
