package helper;

import de.tr7zw.nbtapi.NBTItem;
import java.util.ArrayList;
import java.util.List;
import mysqlite.ItemDB;
import org.bukkit.inventory.ItemStack;
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

    public static boolean CompareItemsDB(ItemStack item) {
        ItemStack droppedItem = item.clone();
        ItemDB itemDB = new ItemDB();
        boolean result = false;

        for (int i = 0; i < UserData.itemsDB.size(); i++) {
            List<String> flags = itemDB.getFlags(UserData.itemsDB.get(i));

            ItemStack filteredItem = UserData.itemsDB.get(i).clone();
            if (droppedItem.getAmount() > 1) {
                droppedItem.setAmount(1);
            }

            if (flags.contains("IgnoreNBT")) {
                NBTItem nbtItem = new NBTItem(filteredItem);
                NBTItem nbtTwo = new NBTItem(droppedItem);
                nbtItem.clearCustomNBT();
                nbtTwo.clearCustomNBT();
                filteredItem = nbtItem.getItem();
                droppedItem = nbtTwo.getItem();
            } else {
                NBTItem first = new NBTItem(droppedItem);
                NBTItem second = new NBTItem(filteredItem);
                if (first.toString().equals(second.toString())) {
                    result = true;
                    break;
                }
            }
            result = droppedItem.equals(filteredItem);
            if (result) {
                break;
            }
        }

        return result;
    }

}
