package helper;

import de.tr7zw.nbtapi.NBTItem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author HappierGore
 */
public final class DEXItem {

    public final String ENCHANTMENTS;
    public final String MATERIAL;
    public final String DISPLAYNAME;
    public final String LORE;
    public final String NBT;
    private final ItemStack finalItem;

    public List<String> NBTExceptions = new ArrayList<>();

    public DEXItem(ItemStack item) {
        StringBuilder ench = new StringBuilder();

        item.getEnchantments().entrySet().forEach((t) -> {
            //Entries on no old version Enchantment[minecraft:aqua_affinity, WATER_WORKER]
            //Entries on old version Enchantment[16, WATER_WORKER]
            String enc = t.getKey().toString().replace("Enchantment", "").split(",")[1].trim().replace("]", "");
            int level = t.getValue();

            ench.append(enc).append(":").append(Integer.toString(level)).append(";");
        });

        this.ENCHANTMENTS = ench.toString().trim().equals("") ? null : ench.toString();

        this.MATERIAL = item.getType().toString();
        this.DISPLAYNAME = item.getItemMeta() != null ? item.getItemMeta().getDisplayName() : null;
        this.LORE = item.getItemMeta() != null && item.getItemMeta().getLore() != null ? String.join(";", item.getItemMeta().getLore()) : null;

        this.NBT = filterNBT(item);

        this.finalItem = createItem();
    }

    //Used when load from database
    public DEXItem(String ENCHANTMENTS, String MATERIAL, String DISPLAYNAME, String LORE, String NBT) {
        this.ENCHANTMENTS = ENCHANTMENTS;
        this.MATERIAL = MATERIAL;
        this.DISPLAYNAME = DISPLAYNAME;
        this.LORE = LORE;
        this.NBT = NBT;
        this.finalItem = createItem();
    }

    private ItemStack createItem() {
        ItemStack item = new ItemStack(Material.getMaterial(this.MATERIAL));

        ItemMeta itemMeta = Bukkit.getItemFactory().getItemMeta(item.getType());

        if (this.DISPLAYNAME != null) {
            itemMeta.setDisplayName(this.DISPLAYNAME);
            item.setItemMeta(itemMeta);
        }

        if (this.LORE != null) {
            generateLores(itemMeta, item);
        }

        if (this.ENCHANTMENTS != null) {
            generateEnchantments(itemMeta, item);
        }

        if (this.NBT != null) {
            item = generateNBT(item).getItem();
        }
        return item;
    }

    //***************************
    //          Helpers
    //***************************
    private NBTItem generateNBT(ItemStack item) {
        NBTItem nbtItem = new NBTItem(item);
        for (String entry : this.NBT.split(";")) {
            String dataType = entry.split(":")[1];
            String key = entry.split(":")[0];
            String info = entry.split(":")[2];

            switch (dataType) {
                case "String": {
                    nbtItem.setString(key, info);
                    break;
                }
                case "Integer": {
                    nbtItem.setInteger(key, Integer.parseInt(info));
                    break;
                }
                case "Float": {
                    nbtItem.setFloat(key, Float.parseFloat(info));
                    break;
                }
            }

        }
        return nbtItem;
    }

    private void generateLores(ItemMeta itemMeta, ItemStack item) {
        //Creating the result
        List<String> lores = new ArrayList<>();

        //Getting the data from DB
        String[] loreDB = this.LORE.split(";");

        //If we have data from DB then add it into result
        if (loreDB.length > 0) {
            lores.addAll(Arrays.asList(loreDB));
            itemMeta.setLore(lores);
        }
        item.setItemMeta(itemMeta);
    }

    private void generateEnchantments(ItemMeta itemMeta, ItemStack item) {
        for (String input : this.ENCHANTMENTS.split(";")) {
            Enchantment enchant = Enchantment.getByName(input.split(":")[0]);
            //Enchantment enchant = Enchantment.getByKey(NamespacedKey.fromString(input.split(":")[0]));
            int level = Integer.parseInt(input.split(":")[1]);
            itemMeta.addEnchant(enchant, level, true);
        }
        item.setItemMeta(itemMeta);
    }

    private String filterNBT(ItemStack item) {
        NBTItem nbtItem = new NBTItem(item);

        StringBuilder nbtNormal = new StringBuilder();

        NBTExceptions.add("Enchantments");
        NBTExceptions.add("ench");
        NBTExceptions.add("display");
        NBTExceptions.add("UUID");

        nbtItem.getKeys().forEach(key -> {
            if (!NBTExceptions.contains(key)) {
                nbtNormal.append(key).append(":");
                switch (nbtItem.getType(key)) {
                    case NBTTagString: {
                        nbtNormal.append("String:").append(nbtItem.getString(key));
                        break;
                    }
                    case NBTTagInt: {
                        nbtNormal.append("Integer:").append(nbtItem.getInteger(key));
                        break;
                    }
                    case NBTTagFloat: {
                        nbtNormal.append("Float:").append(nbtItem.getFloat(key));
                        break;
                    }
                }
                nbtNormal.append(";");
            }

        });
        return nbtNormal.toString().trim().equals("") ? null : nbtNormal.toString();
    }

    //***************************
    //          Getters
    //***************************
    public ItemStack getItem() {
        return finalItem;
    }

}
