package events;

import gui.GUIManager;
import gui.UpdateDBContent;
import org.bukkit.entity.Player;

import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 *
 * @author HappierGore
 */
public class CloseGUI {
    // Check for clicks on items

    public static void onCloseInv(InventoryCloseEvent e) {

        if (!GUIManager.currentData.containsKey(e.getPlayer().getUniqueId().toString())) {
            return;
        }

        GUIManager guiManager = GUIManager.getObj((Player) e.getPlayer());

        if (e.getInventory().equals(guiManager.inv)) {
            UpdateDBContent.updateDBContent(e);
        }
        if (e.getInventory().equals(guiManager.configInv)) {
            guiManager.configuring = false;
        }
        if (!guiManager.configuring) {
            GUIManager.currentData.remove(e.getPlayer().getUniqueId().toString());
        }

    }
}
