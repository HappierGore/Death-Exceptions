package gui.items.modules;

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
public class ItemDropChance extends SwitchItem {

    public ItemDropChance(Inventory inv, int slot) {
        super(inv, slot);
    }

    
    
    @Override
    public ItemStack generateSwitchItem() {
        Map<Enchantment, Integer> enchantments = new HashMap<>();
        enchantments.put(Enchantment.LUCK, 1);

        List<String> lore = new ArrayList<>();
        lore.add(parseColor("&bDisable drop chance"));

        List<ItemFlag> flags = new ArrayList<>();
        flags.add(ItemFlag.HIDE_ENCHANTS);

        return ItemUtils.generateItem(enchantments, Material.ENDER_CHEST, parseColor("&9Drop chance"), lore, flags);
    }

    @Override
    public ItemStack generateMainItem() {
        List<String> lore = new ArrayList<>();
        lore.add(parseColor("&bEnable drop chance"));

        return ItemUtils.generateItem(null, Material.ENDER_CHEST, parseColor("&9Drop chance"), lore, null);
    }

    @Override
    public boolean loadCondition() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
