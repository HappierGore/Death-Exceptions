package events;

import gui.menus.GUI;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 *
 * @author HappierGore
 */
public class CloseGUI {
    // Check for clicks on items

    public static void onCloseInv(InventoryCloseEvent e) {
        if (e.getInventory().getHolder() == null) {
            return;
        }

        if (e.getInventory().getHolder() instanceof GUI gui) {
            gui.close(e);
        }
    }
}
