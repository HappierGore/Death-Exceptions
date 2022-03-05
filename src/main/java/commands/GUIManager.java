package commands;

import de.tr7zw.nbtapi.NBTItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import user.UserData;

/**
 *
 * @author HappierGore
 */
public class GUIManager {

    public static Map<String, GUIManager> currentData;

    static {
        currentData = new HashMap<>();
    }

    public final Inventory inv;

    public GUIManager(String UUID) {

        int rows = (int) (Math.ceil((UserData.itemsDB.size() / 9.0f) + 0.5));

        inv = Bukkit.createInventory(null, rows * 9, "Example");
    }

    private void updateInv() {
        inv.clear();
        UserData.itemsDB.forEach((t) -> {
            NBTItem nbtItem = new NBTItem(t);
            inv.addItem(t);
        });
    }

    public static GUIManager getObj(String UUID) {
        if (currentData.containsKey(UUID)) {
            return currentData.get(UUID);
        }
        GUIManager guiManager = new GUIManager(UUID);
        currentData.put(UUID, guiManager);
        return guiManager;
    }

    // You can open the inventory with this
    public void openInventory(final HumanEntity ent) {
        updateInv();
        ent.openInventory(inv);
    }

}
