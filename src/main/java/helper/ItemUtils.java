package helper;

import de.tr7zw.nbtapi.NBTItem;
import gui.items.ItemFlags;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import mysqlite.ItemDB;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import user.UserData;

/**
 *
 * @author HappierGore
 */
public class ItemUtils {

    public static List<ItemStack> cloneDBItems() {
        List<ItemStack> newList = new ArrayList<>();
        newList.addAll(UserData.itemsDB);
        return newList;
    }

    public static boolean CompareItemsDB(ItemStack itemDropped) {
        boolean NBTOK = false, ENCHOK = false, NAMEOK = false, LOREOK = false, result = false;

        for (int i = 0; i < UserData.itemsDB.size(); i++) {
            ItemStack itemInDB = UserData.itemsDB.get(i);

            if (itemInDB.getType() != itemDropped.getType()) {
                continue;
            }

            List<ItemFlags> flags = ItemDB.getFlags(UserData.itemsDB.get(i));

//            System.out.println("Flags: " + flags.toString() + "\n");
            ItemMeta metaFiltered = itemInDB.getItemMeta();
            ItemMeta metaDropped = itemDropped.getItemMeta();

            if (metaFiltered != null && metaDropped != null) {

                if (flags.contains(ItemFlags.IgnoreEnchantments)) {
                    ENCHOK = true;
                } else {
                    if (metaFiltered.hasEnchants() && metaDropped.hasEnchants()) {
//                        System.out.println("Enchantments: \n");
//                        System.out.println("Enchants 1: " + metaFiltered.getEnchants().toString());
//                        System.out.println("Enchants 2 : " + metaDropped.getEnchants().toString());

                        int filtSize = metaFiltered.getEnchants().size();
                        int dropSize = metaDropped.getEnchants().size();

                        Set<Enchantment> enchToLoop = filtSize > dropSize ? metaFiltered.getEnchants().keySet() : metaDropped.getEnchants().keySet();

                        for (Enchantment ench : enchToLoop) {
//                            System.out.println("Comparing: " + ench.toString());
//                            System.out.println("ENCH: " + ENCHOK);
                            if (!Objects.equals(metaDropped.getEnchants().get(ench), metaFiltered.getEnchants().get(ench))) {
                                ENCHOK = false;
                                break;
                            }
                            ENCHOK = true;
                        }
                    } else {
//                        System.out.println("NO enchantments");
                        ENCHOK = !(metaFiltered.hasEnchants() || metaDropped.hasEnchants());
                    }
                }

                String fixedName1;
                String fixedName2;

                if (VersionManager.version <= 12) {
                    if (metaDropped.getDisplayName() != null) {
                        fixedName1 = metaDropped.hasEnchants() ? metaDropped.getDisplayName().replace(TextUtils.parseColor("&b"), "") : metaDropped.getDisplayName();
                    } else {
                        fixedName1 = null;
                    }
                    if (metaFiltered.getDisplayName() != null) {
                        fixedName2 = metaFiltered.hasEnchants() ? metaFiltered.getDisplayName().replace(TextUtils.parseColor("&b"), "") : metaFiltered.getDisplayName();
                    } else {
                        fixedName2 = null;
                    }
                } else {
                    fixedName1 = metaDropped.hasEnchants() ? metaDropped.getDisplayName().replace(TextUtils.parseColor("&b"), "") : metaDropped.getDisplayName();
                    fixedName2 = metaFiltered.hasEnchants() ? metaFiltered.getDisplayName().replace(TextUtils.parseColor("&b"), "") : metaFiltered.getDisplayName();
                }

                fixedName1 = fixedName1 == null ? "" : fixedName1;
                fixedName2 = fixedName2 == null ? "" : fixedName2;
//                System.out.println("DISPLAYNAME:\n");
//                System.out.println("FixedName 1 : " + fixedName1);
//                System.out.println("FixedName 2 : " + fixedName2);

                NAMEOK = flags.contains(ItemFlags.IgnoreDisplayName) || fixedName1.equals(fixedName2);

                if (!metaDropped.hasLore() && !metaFiltered.hasLore()) {
                    LOREOK = true;
                } else {
                    LOREOK = flags.contains(ItemFlags.IgnoreLore) || (metaDropped.hasLore() && metaFiltered.hasLore() && metaDropped.getLore().toString().equals(metaFiltered.getLore().toString()));
                }

            }
            if (flags.contains(ItemFlags.IgnoreNBT)) {
                NBTOK = true;
            } else {
                NBTItem first = new NBTItem(itemDropped);
                NBTItem second = new NBTItem(itemInDB);

                List<String> exceptions = new ArrayList<>();
                exceptions.add("Enchantments");
                exceptions.add("UUID");
                exceptions.add("ench");
                exceptions.add("display");

                exceptions.forEach(exc -> {
                    first.removeKey(exc);
                    second.removeKey(exc);
                });

//                System.out.println("NBT:\n");
//                System.out.println("Fixed NBT 1 : " + first.toString());
//                System.out.println("Fixed NBT 2 : " + second.toString());
                if (first.toString().equals(second.toString())) {
                    NBTOK = true;
                }
            }

//            System.out.println("ENCH : " + ENCHOK + "\nNBT: " + NBTOK + "\nName: " + NAMEOK + "\nLore :" + LOREOK);
            if (ENCHOK && NBTOK && NAMEOK && LOREOK) {
                result = true;
                break;
            }
        }

        return result;
    }

}
