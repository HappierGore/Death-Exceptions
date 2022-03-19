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
    public final Inventory DBInventory;
    public final Inventory configInv;
    public ConfigItems configItems;
    public boolean configuring = false;
    private final Player player;

    public GUIManager(Player player) {

        this.player = player;
        int rows = (int) (Math.ceil((ItemDB.getDBItems().size() / 9.0f) + 0.5));

        DBInventory = Bukkit.createInventory(null, rows * 9, "Protected items");

        configInv = Bukkit.createInventory(null, 27, "Configure item");
    }

    public void openItemsDB() {
        DBInventory.clear();
        ItemDB.getDBItems().forEach((t) -> {
            DBInventory.addItem(t);
        });
        player.openInventory(DBInventory);
    }

    public void openConfigItem(final ItemStack item) {
        configuring = true;
        this.configItems = new ConfigItems(item, configInv, player);
        configItems.loadAllItems();
        player.openInventory(configInv);
    }

    public Player getPlayer() {
        return this.player;
    }
}
