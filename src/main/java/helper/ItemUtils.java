package helper;

import java.util.ArrayList;
import java.util.List;
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

}
