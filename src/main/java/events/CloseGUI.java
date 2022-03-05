package events;

import commands.GUIManager;
import helper.ItemUtils;
import mysqlite.ItemDB;
import org.bukkit.event.inventory.InventoryCloseEvent;
import user.UserData;

/**
 *
 * @author HappierGore
 */
public class CloseGUI {
    // Check for clicks on items

    public static void onCloseInv(InventoryCloseEvent e) {

        System.out.println("Fase 1");

        if (!GUIManager.currentData.containsKey(e.getPlayer().getUniqueId().toString())) {
            return;
        }

        System.out.println("Fase 2");

        GUIManager guiManager = GUIManager.getObj(e.getPlayer().getUniqueId().toString());

        if (!e.getInventory().equals(guiManager.inv)) {
            return;
        }

        System.out.println("Fase 3");

        ItemDB itemDB = new ItemDB();

        // When removes item
        ItemUtils.cloneDBItems().forEach(item -> {
            if (!e.getInventory().contains(item)) {
                itemDB.remove(item);
            }
        });

        //When item is added
        e.getInventory().forEach(item -> {
            if (item != null && !UserData.itemsDB.contains(item)) {
                itemDB.addItem(item);
            }
        });

        GUIManager.currentData.remove(e.getPlayer().getUniqueId().toString());

        //itemDB.loadAllData();
//        e.getInventory().forEach(item -> {
//            if (!UserData.itemsDB.contains(item)) {
//                itemDB.addItem(item);
//            }
//        });
    }

    // Cancel dragging in our inventory
//    @EventHandler
//    public void onInventoryClick(final InventoryDragEvent e) {
//        if (e.getInventory().equals(inv)) {
//            e.setCancelled(true);
//        }
//    }
}
