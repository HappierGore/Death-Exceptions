package gui.items.modulesMenu;

import gui.items.types.SwitchItem;
import helper.ItemUtils;
import static helper.TextUtils.parseColor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappierGore
 */
public class enableDropChance extends SwitchItem {

    private final ItemStack itemConfig;

    public enableDropChance(Inventory inv, int slot, ItemStack itemConfig) {
        super(inv, slot);
        this.itemConfig = itemConfig;
    }

    @Override
    public boolean loadCondition() {
        //Agregar en DB AddModule, remove Module y update Module
        return false;
    }

    @Override
    public ItemStack generateSwitchItem() {
        Map<Enchantment, Integer> ench = new HashMap<>();
        ench.put(Enchantment.LURE, 1);

        List<String> lore = new ArrayList<>();
        lore.add(parseColor("&bDisable this option to remove the chance"));
        lore.add(parseColor("&bto drop the item when player dies"));

        List<ItemFlag> flags = new ArrayList<>();
        flags.add(ItemFlag.HIDE_ENCHANTS);
        return ItemUtils.generateItem(ench, Material.NETHER_STAR, parseColor("&6Drop chance"), lore, flags);
    }

    @Override
    public ItemStack generateMainItem() {

        List<String> lore = new ArrayList<>();
        lore.add(parseColor("&bEnable this option to add a chance"));
        lore.add(parseColor("&bto drop the item when player dies"));

        return ItemUtils.generateItem(null, Material.NETHER_STAR, parseColor("&6Drop chance"), lore, null);
    }

}
