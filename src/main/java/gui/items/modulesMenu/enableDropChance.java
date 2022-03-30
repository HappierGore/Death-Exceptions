package gui.items.modulesMenu;

import gui.items.types.SwitchItem;
import gui.items.types.TextRequest;
import gui.menus.ModulesGUI;
import gui.modules.Modules;
import helper.DEXItem;
import helper.ItemUtils;
import static helper.TextUtils.parseColor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import sqlite.ItemDAO;

/**
 *
 * @author HappierGore
 */
public class enableDropChance extends SwitchItem {

    private final DEXItem configItem;
    private final Modules module = Modules.DropChance;

    private final TextRequest txtRequest;

    public enableDropChance(Inventory inv, int slot, ItemStack itemConfig) {
        super(inv, slot);

        this.configItem = ItemDAO.getItemFromDB(itemConfig);

        this.txtRequest = new TextRequest(inv, slot, "&bPlease, enter a number between 0 and 100") {
            @Override
            public void onLoad() {
            }

            @Override
            public ItemStack generateMainItem() {
                return null;
            }

            @Override
            public void onTextEntry(PlayerChatEvent e) {
                int chance;
                try {
                    chance = Integer.parseInt(e.getMessage());
                    super.onTextEntry(e);
                    this.setText(String.valueOf(chance));
                    configItem.getModules().put(module, String.valueOf(chance));
                    ItemDAO.updateItemInfo(configItem);
                    new ModulesGUI(configItem.getItem(), e.getPlayer()).open();
                } catch (NumberFormatException ex) {
                    e.getPlayer().sendMessage(parseColor("&cYou need to specify a valid number between 0 and 100. Please, try again."));
                    e.setCancelled(true);
                }
            }
        };
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        super.onClick(e);
        if (!this.loadCondition()) {
            this.txtRequest.onClick(e);
        } else {
            this.configItem.getModules().remove(module);
        }
        ItemDAO.updateItemInfo(this.configItem);
    }

    @Override
    public boolean loadCondition() {
        return configItem.getModules().containsKey(module);
    }

    @Override
    public ItemStack generateSwitchItem() {
        return generateMainItem();
    }

    @Override
    public void onLoad() {
        if (loadCondition()) {
            setSwitchItem(newSwitchItem());
        }
        super.onLoad();
    }

    private ItemStack newSwitchItem() {
        Map<Enchantment, Integer> ench = new HashMap<>();
        ench.put(Enchantment.LURE, 1);

        List<String> lore = new ArrayList<>();
        lore.add(parseColor("&bDisable this option to remove the chance"));
        lore.add(parseColor("&bto drop the item when player dies"));
        lore.add(parseColor("&7----------------"));
        lore.add(parseColor("&9Actual value: &n" + this.configItem.getModules().get(module)));

        List<ItemFlag> flags = new ArrayList<>();
        flags.add(ItemFlag.HIDE_ENCHANTS);
        return ItemUtils.generateItem(ench, Material.ENDER_PEARL, parseColor("&6Drop chance"), lore, flags);
    }

    @Override
    public ItemStack generateMainItem() {

        List<String> lore = new ArrayList<>();
        lore.add(parseColor("&bEnable this option to add a chance"));
        lore.add(parseColor("&bto drop the item when player dies"));

        return ItemUtils.generateItem(null, Material.ENDER_PEARL, parseColor("&6Drop chance"), lore, null);
    }

}
