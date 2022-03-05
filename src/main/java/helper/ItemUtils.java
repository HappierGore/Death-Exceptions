/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helper;

import de.tr7zw.nbtapi.NBTItem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import user.UserData;

/**
 *
 * @author HappierGore
 */
public class ItemUtils {

    public static ItemStack createItem(String material, String displayName, String lore, String nbt, String enchantments) {
        ItemStack item = new ItemStack(Material.getMaterial(material));

        ItemMeta itemMeta = Bukkit.getItemFactory().getItemMeta(item.getType());

        if (displayName != null) {
            itemMeta.setDisplayName(displayName);
        }
        if (lore != null) {
            //Creating the result
            List<String> lores = new ArrayList<>();

            //Getting the data from DB
            String[] loreDB = lore.split(";");

            //If we have data from DB then add it into result
            if (loreDB.length > 0) {
                lores.addAll(Arrays.asList(loreDB));
                itemMeta.setLore(lores);
            }
        }
        if (nbt != null) {

            if (!nbt.equals("{}")) {
                String nbtFixed = nbt.replace("{", "").replace("}", "").replace("\"\"", "");
                NBTItem nbtItem = new NBTItem(item);
                for (String entry : nbtFixed.split(",")) {
                    String key = entry.split(":")[0];
                    String info = entry.split(":")[1];
                    nbtItem.setString(key, info);
                }
                item = nbtItem.getItem();
            }
        }

        if (enchantments != null) {
            for (String input : enchantments.split(";")) {
                Enchantment enchant = Enchantment.getByKey(NamespacedKey.fromString(input.split(":")[0]));
                int level = Integer.parseInt(input.split(":")[1]);
                itemMeta.addEnchant(enchant, level, true);
            }
        }

        item.setItemMeta(itemMeta);

        return item;
    }

    //Compare everything, but ignoring UUID nbt
    public static boolean compareItemInDB(ItemStack item) {

        StringBuilder ench = new StringBuilder();

        item.getEnchantments().entrySet().forEach((t) -> {
            ench.append(t.getKey().getKey().getKey()).append(":").append(t.getValue()).append(";");
        });

        String ENCHANTMENTS = ench.toString().trim().equals("") ? null : ench.toString();

        String MATERIAL = item.getType().toString();
        String DISPLAYNAME = !item.getItemMeta().getDisplayName().equals("") ? item.getItemMeta().getDisplayName() : null;
        String LORE = item.getItemMeta().getLore() != null ? String.join(";", item.getItemMeta().getLore()) : null;

        NBTItem nbtItem = new NBTItem(item);
        //Tags to ignore
        nbtItem.removeKey("UUID");
        String NBT = !nbtItem.toString().equals("{}") ? nbtItem.toString() : null;

        ItemStack finalItem = createItem(MATERIAL, DISPLAYNAME, LORE, NBT, ENCHANTMENTS);

        return UserData.itemsDB.contains(finalItem);
    }

}
