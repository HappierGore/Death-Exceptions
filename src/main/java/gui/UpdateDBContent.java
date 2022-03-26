package gui;

import static helper.TextUtils.parseColor;
import sqlite.ItemDB;
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
                if (ItemDB.removeItem(item)) {
                    e.getPlayer().sendMessage(parseColor("&aThe item(s) has been removed successfully"));
                } else {
                    e.getPlayer().sendMessage(parseColor("&cThere was an error while trying to remove the item(s)"));
                }
            }
        });

        //When item is added
        e.getInventory().forEach(item -> {
            if (item != null && !ItemDB.getDBItems().contains(item)) {
                if (ItemDB.addItem(item)) {
                    e.getPlayer().sendMessage(parseColor("&aThe item(s) has been added successfully."));
                } else {
                    e.getPlayer().sendMessage(parseColor("&cThe item(s) is already registered"));
                    e.getPlayer().getInventory().addItem(item);
                }
            }
        });
    }
}
