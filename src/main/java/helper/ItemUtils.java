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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author HappierGore
 */
public class ItemUtils {

    public static ItemStack createItem(String material, String displayName, String lore, String nbt) {
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

        item.setItemMeta(itemMeta);

        return item;
    }
}
