package events;

import gui.GUIManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappierGore
 */
public class OnClickGUI {

    public static void onClickGUI(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        if (!GUIManager.currentData.containsKey(player.getUniqueId().toString())) {
            return;
        }

        GUIManager guiManager = GUIManager.getObj(player);

        //First inventory, where all items in DB are loaded
        if (e.getClickedInventory() == null) {
            return;
        }
        if (e.getClickedInventory().equals(guiManager.inv)) {
            if (e.getClick().isRightClick()) {

                if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
                    return;
                }

                e.setCancelled(true);
                guiManager.openConfigItem(e.getCurrentItem());
            }
        }

        //Second inventory, it's to configure
        if (e.getClickedInventory().equals(guiManager.configInv)) {
            if (e.getCurrentItem() == null) {
                return;
            }
            e.setCancelled(true);
            ItemStack item = e.getCurrentItem();
            guiManager.configItems.evaluate(item);
        }
    }
}
