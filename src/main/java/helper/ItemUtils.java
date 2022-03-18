package helper;

import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTItem;
import gui.items.ItemFlags;
import java.util.ArrayList;
import java.util.List;
import mysqlite.ItemDB;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author HappierGore
 */
public class ItemUtils {

    public static List<ItemStack> cloneDBItems() {
        List<ItemStack> newList = new ArrayList<>();
        for (ItemStack item : ItemDB.itemsDB) {
            newList.add(item.clone());
        }
        return newList;
    }

    public static boolean compareItems(ItemStack item) {

        ItemStack itemDropped = item.clone();

        if (itemDropped.getAmount() > 1) {
            itemDropped.setAmount(1);
        }

        for (ItemStack itemDB : cloneDBItems()) {
            List<ItemFlags> itemFlags = ItemDB.getFlags(itemDB);

            List<String> NBTExceptions = new ArrayList<>();
            NBTExceptions.add("UUID");

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

            NBTContainer nbtDroppedTemp = NBTItem.convertItemtoNBT(itemDropped);
            filterNBT(NBTItem.convertItemtoNBT(itemDropped), NBTExceptions);
            String nbtDropped = nbtDroppedTemp.toString();

            NBTContainer nbtItemDB = NBTItem.convertItemtoNBT(itemDB);
            filterNBT(nbtItemDB, NBTExceptions);
            String nbtDB = nbtItemDB.toString();

            if (nbtDropped.equals(nbtDB)) {
                return true;
            }
        }

        return false;
    }

    public static void filterNBT(NBTContainer nbtItem, List<String> exceptions) {

        List<String> cloneNBT = new ArrayList<>();
        cloneNBT.addAll(nbtItem.getKeys());

        cloneNBT.forEach(key -> {
            if (exceptions.contains(key)) {
                nbtItem.removeKey(key);
            }
        });
    }

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
