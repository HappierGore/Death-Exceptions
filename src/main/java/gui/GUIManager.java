package gui;

import java.util.HashMap;
import java.util.Map;
import mysqlite.ItemDB;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappierGore
 */
public class GUIManager {

    //******************
    //      Static
    //******************
    public static Map<String, GUIManager> currentData;

    static {
        currentData = new HashMap<>();
    }

    public static GUIManager getObj(Player player) {
        String UUID = player.getUniqueId().toString();
        if (currentData.containsKey(UUID)) {
            return currentData.get(UUID);
        }
        GUIManager guiManager = new GUIManager(player);
        currentData.put(UUID, guiManager);
        return guiManager;
    }

    //******************
    //      Class
    //******************
    public final Inventory inv;
    public final Inventory configInv;
    public boolean configuring = false;
    private final Player player;
    public ConfigItems configItems;

    public GUIManager(Player player) {

        this.player = player;
        int rows = (int) (Math.ceil((ItemDB.itemsDB.size() / 9.0f) + 0.5));

        inv = Bukkit.createInventory(null, rows * 9, "Protected items");

        configInv = Bukkit.createInventory(null, 27, "Configure item");
    }

    public void openItemsDB() {
        inv.clear();
        ItemDB.itemsDB.forEach((t) -> {
            inv.addItem(t);
        });
        player.openInventory(inv);
    }

    public void openConfigItem(final ItemStack item) {
        configItems = new ConfigItems(item, configInv, player);
        configuring = true;
        player.openInventory(configInv);
    }
}
