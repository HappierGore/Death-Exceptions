package gui;

import helper.ItemUtils;
import mysqlite.ItemDB;
import org.bukkit.event.inventory.InventoryCloseEvent;
import user.UserData;

/**
 *
 * @author HappierGore
 */
public class UpdateDBContent {

    public static void updateDBContent(InventoryCloseEvent e) {
        // When removes item
        ItemUtils.cloneDBItems().forEach(item -> {
            if (!e.getInventory().contains(item)) {
                ItemDB.remove(item);
            }
        });

        //When item is added
        e.getInventory().forEach(item -> {
            if (item != null) {
                if (!UserData.itemsDB.contains(item)) {
                    ItemDB.addItem(item);
                }
            }
        });
    }
}
