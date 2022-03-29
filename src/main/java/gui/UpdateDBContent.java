package gui;

import static helper.TextUtils.parseColor;
import sqlite.ItemDAO;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 *
 * @author HappierGore
 */
public class UpdateDBContent {

    public static void updateItems(InventoryCloseEvent e) {
        // When removes item
        ItemDAO.getItemDB().forEach(item -> {
            if (!e.getInventory().contains(item.getItem())) {
                if (ItemDAO.removeItem(item.getItem())) {
                    e.getPlayer().sendMessage(parseColor("&aThe item(s) has been removed successfully"));
                    ItemDAO.updateItemInfo(item);
                } else {
                    e.getPlayer().sendMessage(parseColor("&cThere was an error while trying to remove the item(s)"));
                }
            }
        });

        //When item is added
        e.getInventory().forEach(item -> {
            if (item != null) {
                if (ItemDAO.addItem(item)) {
                    e.getPlayer().sendMessage(parseColor("&aThe item(s) has been added successfully."));
                }
            }
        });
    }

}
