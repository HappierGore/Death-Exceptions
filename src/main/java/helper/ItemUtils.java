/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helper;

import de.tr7zw.nbtapi.NBTItem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
            item.setItemMeta(itemMeta);
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
            item.setItemMeta(itemMeta);
        }

        if (enchantments != null) {
            for (String input : enchantments.split(";")) {
                Enchantment enchant = Enchantment.getByKey(NamespacedKey.fromString(input.split(":")[0]));
                int level = Integer.parseInt(input.split(":")[1]);
                itemMeta.addEnchant(enchant, level, true);
            }
            item.setItemMeta(itemMeta);
        }

        if (nbt != null) {

            NBTItem nbtItem = new NBTItem(item);
            for (String entry : nbt.split(";")) {
                String dataType = entry.split(":")[1];
                String key = entry.split(":")[0];
                String info = entry.split(":")[2];

                switch (dataType) {
                    case "String" -> {
                        nbtItem.setString(key, info);
                    }
                    case "Integer" -> {
                        nbtItem.setInteger(key, Integer.parseInt(info));
                    }
                    case "Float" -> {
                        nbtItem.setFloat(key, Float.parseFloat(info));
                    }
                }

            }

            System.out.println("FINAL NBT ITEM FROM CREATION: " + nbtItem.toString());
            item = nbtItem.getItem();

        }

        return item;
    }

    public static ItemStack fixItem(ItemStack item) {
        StringBuilder ench = new StringBuilder();

        item.getEnchantments().entrySet().forEach((t) -> {
            ench.append(t.getKey().getKey().getKey()).append(":").append(t.getValue()).append(";");
        });

        String ENCHANTMENTS = ench.toString().trim().equals("") ? null : ench.toString();

        String MATERIAL = item.getType().toString();
        String DISPLAYNAME = !item.getItemMeta().getDisplayName().equals("") ? item.getItemMeta().getDisplayName() : null;
        String LORE = item.getItemMeta().getLore() != null ? String.join(";", item.getItemMeta().getLore()) : null;

        NBTItem nbtItem = new NBTItem(item);

        List<String> exceptions = new ArrayList<>();
        exceptions.add("UUID");

        String NBT = FilterNBT(item, exceptions);
        //System.out.println("Fixed Item: " + NBT);

        return createItem(MATERIAL, DISPLAYNAME, LORE, NBT, ENCHANTMENTS);
    }

    public static String FilterNBT(ItemStack item, List<String> otherExceptions) {
        NBTItem nbtItem = new NBTItem(item);

        StringBuilder nbtNormal = new StringBuilder();

        List<String> ignoreKeys = new ArrayList<>();
        ignoreKeys.add("Enchantments");
        ignoreKeys.add("display");

        if (otherExceptions != null) {
            ignoreKeys.addAll(otherExceptions);
        }

        nbtItem.getKeys().forEach(key -> {
            if (!ignoreKeys.contains(key)) {
                //System.out.println("KEY: " + key);
                nbtNormal.append(key).append(":");
                switch (nbtItem.getType(key)) {
                    case NBTTagString -> {
                        nbtNormal.append("String:").append(nbtItem.getString(key));
                    }
                    case NBTTagInt -> {
                        nbtNormal.append("Integer:").append(nbtItem.getInteger(key));
                    }
                    case NBTTagFloat -> {
                        nbtNormal.append("Float:").append(nbtItem.getFloat(key));
                    }
                }
                nbtNormal.append(";");
            }

        });
        return nbtNormal.toString().trim().equals("") ? null : nbtNormal.toString();
    }

    public static List<ItemStack> cloneDBItems() {
        List<ItemStack> newList = new ArrayList<>();
        newList.addAll(UserData.itemsDB);
        return newList;
    }

}
