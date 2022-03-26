package events;

import gui.UpdateDBContent;
import gui.menus.GUI;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.entity.Player;

import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 *
 * @author HappierGore
 */
public class CloseGUI {
    // Check for clicks on items

    public static void onCloseInv(InventoryCloseEvent e) {
        
        Player player = (Player) e.getPlayer();
        
        if (!GUI.GUIData.containsKey(player)) {
            return;
        }
        
        Set<GUI> guiManager = GUI.GUIData.get(player);
        
        Set<GUI> guiManagerClone = new HashSet<>();
        
        guiManagerClone.addAll(guiManager);
        
        guiManagerClone.forEach(gui -> {
            System.out.println("Hash Code :" + e.getInventory().hashCode() + " | " + gui.getInventory().hashCode());
            if (gui.getInventory().hashCode() == e.getInventory().hashCode()) {
                gui.close(e);
                guiManager.remove(gui);
            }
        });
        
    }
}
