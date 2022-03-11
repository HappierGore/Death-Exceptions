package gui;

import helper.ItemUtils;
import helper.TextUtils;
import java.util.ArrayList;
import java.util.List;
import mysqlite.ItemDB;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.event.inventory.InventoryCloseEvent;
import user.UserData;

/**
 *
 * @author HappierGore
 */
public class UpdateDBContent {

    public static void updateDBContent(InventoryCloseEvent e) {

        ItemDB itemDB = new ItemDB();

        // When removes item
        ItemUtils.cloneDBItems().forEach(item -> {
            if (!e.getInventory().contains(item)) {
                itemDB.remove(item);
            }
        });

        List<Material> noValidMaterial = new ArrayList<>();
        noValidMaterial.add(Material.ENCHANTED_BOOK);
        noValidMaterial.add(Material.POTION);
        noValidMaterial.add(Material.WRITTEN_BOOK);
        noValidMaterial.add(Material.MAP);
        noValidMaterial.addAll(Tag.SHULKER_BOXES.getValues());
        noValidMaterial.add(Material.FIREWORK_STAR);
        noValidMaterial.add(Material.FIREWORK_ROCKET);

        //When item is added
        e.getInventory().forEach(item -> {
            if (item != null) {
                if (noValidMaterial.contains(item.getType())) {
                    e.getPlayer().sendMessage(TextUtils.parseColor("&cDeath Exceptions lite doesn't support items of type " + item.getType().toString()));
                    e.getPlayer().getInventory().addItem(item);
                    return;
                }
                if (!UserData.itemsDB.contains(item)) {
                    itemDB.addItem(item);
                }
            }
        });
    }
}
