package events;

import gui.menus.GUI;
import java.util.Set;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 *
 * @author HappierGore
 */
public class OnClickGUI {

    public static void onClickGUI(InventoryClickEvent e) {

        if (e.getClickedInventory() == null || e.getCurrentItem() == null) {
            return;
        }
        Player player = (Player) e.getWhoClicked();

        if (!GUI.GUIData.containsKey(player)) {
            return;
        }

        Set<GUI> guis = GUI.GUIData.get(player);

        guis.forEach(gui -> {
            if (gui.getInventory().hashCode() == e.getClickedInventory().hashCode()) {
                gui.evaluateItem(e.getCurrentItem(), e);
            }
        });
    }
}
