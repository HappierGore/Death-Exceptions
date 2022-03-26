package helper;

import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTItem;
import gui.items.ItemFlags;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import sqlite.ItemDB;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author HappierGore
 */
public class ItemUtils {

    public static boolean checkModules(ItemStack item) {
        return false;
    }

    /**
     * Compares an item with all items from DB
     *
     * @param item Item to compare
     * @return boolean
     */
    public static boolean compareItemsDB(ItemStack item) {

        boolean result = false;

        ItemStack itemDropped = item.clone();

        if (itemDropped.getAmount() > 1) {
            itemDropped.setAmount(1);
        }

        for (ItemStack itemDB : ItemDB.getDBItems()) {
            List<ItemFlags> itemFlags = ItemDB.getFlags(itemDB);
            if (itemFlags.contains(ItemFlags.IgnoreDisplayName)) {
                removeDisplayName(itemDropped);
                removeDisplayName(itemDB);
            }

            if (itemFlags.contains(ItemFlags.IgnoreEnchantments)) {
                removeEnchantments(itemDropped);
                removeEnchantments(itemDB);
            }

            if (itemFlags.contains(ItemFlags.IgnoreLore)) {
                removeLore(itemDropped);
                removeLore(itemDB);
            }
            if (itemFlags.contains(ItemFlags.IgnoreNBT)) {
                NBTItem nbt = new NBTItem(itemDropped);
                nbt.clearCustomNBT();
                itemDropped = nbt.getItem();
            }

            result = compareItems(itemDropped, itemDB);

            if (result) {
                break;
            }
        }
        return result;
    }

    public static final ItemStack generateItem(Map<Enchantment, Integer> enchantments, Material material, String displayName, List<String> lore, List<ItemFlag> flags) {
        ItemMeta itemMeta = Bukkit.getItemFactory().getItemMeta(material);

        if (enchantments != null) {
            enchantments.forEach((enchant, level) -> {
                itemMeta.addEnchant(enchant, level, true);
            });
        }

        if (displayName != null) {
            itemMeta.setDisplayName(displayName);
        }

        if (lore != null) {
            itemMeta.setLore(lore);
        }

        if (flags != null) {
            flags.forEach(flag -> {
                itemMeta.addItemFlags(flag);
            });
        }

        ItemStack newItem = new ItemStack(material);
        newItem.setItemMeta(itemMeta);
        return newItem;
    }

    //*********************
    //      Helper
    //*********************
    /**
     * Removes all exceptions into exceptions list to the NBT provided
     *
     * @param nbtItem The NBT container of the item.
     * @param exceptions A list with all exceptions to ignore
     */
    public static void filterNBT(NBTContainer nbtItem, List<String> exceptions) {
        List<String> cloneNBT = new ArrayList<>();
        cloneNBT.addAll(nbtItem.getKeys());

        cloneNBT.forEach(key -> {
            if (exceptions.contains(key)) {
                nbtItem.removeKey(key);
            }
        });
    }

    /**
     * Compare two items using NBT containers
     *
     * @param item1 Item 1
     * @param item2 Item 2
     * @return boolean
     */
    public static boolean compareItems(ItemStack item1, ItemStack item2) {

        List<String> NBTExceptions = new ArrayList<>();
        NBTExceptions.add("UUID");

        NBTContainer nbtItem1 = NBTItem.convertItemtoNBT(item1);
        filterNBT(NBTItem.convertItemtoNBT(item1), NBTExceptions);
        String strNBTItem1 = nbtItem1.toString();

        NBTContainer nbtItem2 = NBTItem.convertItemtoNBT(item2);
        filterNBT(nbtItem2, NBTExceptions);
        String strNBTItem2 = nbtItem2.toString();

//        System.out.println("First item NBT: " + strNBTItem1);
//        System.out.println("Second item NBT: " + strNBTItem2);
        return strNBTItem1.equals(strNBTItem2);
    }

    /**
     * If the item has displayname, will be remove it.
     *
     * @param item The item to remove displayName
     */
    private static void removeDisplayName(ItemStack item) {
//        System.out.println("Before display: " + new NBTItem(item).toString());
        ItemMeta itemMeta = item.hasItemMeta() ? item.getItemMeta() : null;
        if (itemMeta == null) {
            return;
        }
        if (itemMeta.hasDisplayName()) {
            itemMeta.setDisplayName(null);
        }
        item.setItemMeta(itemMeta);
//        System.out.println("After display: " + new NBTItem(item).toString());
    }

    /**
     * If the item has lore, will be remove it.
     *
     * @param item The item to remove lore
     */
    private static void removeLore(ItemStack item) {
//        System.out.println("Before lore: " + new NBTItem(item).toString());
        ItemMeta itemMeta = item.hasItemMeta() ? item.getItemMeta() : null;
        if (itemMeta == null) {
            return;
        }
        if (itemMeta.hasLore()) {
            itemMeta.setLore(null);
        }
        item.setItemMeta(itemMeta);
//        System.out.println("after lore: " + new NBTItem(item).toString());
    }

    /**
     * If the item has enchantments, will be remove it.
     *
     * @param item The item to remove enchantments
     */
    private static void removeEnchantments(ItemStack item) {
//        System.out.println("Before ench: " + new NBTItem(item).toString());

        ItemMeta itemMeta = item.hasItemMeta() ? item.getItemMeta() : null;
        if (itemMeta == null) {
            return;
        }
        ItemMeta itemResult = itemMeta.clone();
        if (itemMeta.hasEnchants()) {
            itemMeta.getEnchants().keySet().forEach(key -> {
                itemResult.removeEnchant(key);
            });
        }
        item.setItemMeta(itemResult);
//        System.out.println("after ench: " + new NBTItem(item).toString());
    }

}
