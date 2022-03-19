package gui;

import mysqlite.ItemDB;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 *
 * @author HappierGore
 */
public class UpdateDBContent {

    public static void updateDBContent(InventoryCloseEvent e) {
        // When removes item
        ItemDB.getDBItems().forEach(item -> {
            if (!e.getInventory().contains(item)) {
                ItemDB.removeItem(item);
            }
        });

        //When item is added
        e.getInventory().forEach(item -> {
            if (item != null) {
                if (!ItemDB.getDBItems().contains(item)) {
                    ItemDB.addItem(item);
                }
            }
        });
    }
}
