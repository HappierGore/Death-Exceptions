package gui;

import helper.ItemUtils;
import helper.TextUtils;
import helper.VersionManager;
import java.util.ArrayList;
import java.util.List;
import mysqlite.ItemDB;
import org.bukkit.Material;
import org.bukkit.entity.Player;
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
        if (VersionManager.version >= 13) {
            noValidMaterial.add(Material.WHITE_SHULKER_BOX);
            noValidMaterial.add(Material.BLACK_SHULKER_BOX);
            noValidMaterial.add(Material.BLUE_SHULKER_BOX);
            noValidMaterial.add(Material.BROWN_SHULKER_BOX);
            noValidMaterial.add(Material.CYAN_SHULKER_BOX);
            noValidMaterial.add(Material.GRAY_SHULKER_BOX);
            noValidMaterial.add(Material.GREEN_SHULKER_BOX);
            noValidMaterial.add(Material.LIGHT_BLUE_SHULKER_BOX);
            noValidMaterial.add(Material.LIME_SHULKER_BOX);
            noValidMaterial.add(Material.MAGENTA_SHULKER_BOX);
            noValidMaterial.add(Material.ORANGE_SHULKER_BOX);
            noValidMaterial.add(Material.PINK_SHULKER_BOX);
            noValidMaterial.add(Material.PURPLE_SHULKER_BOX);
            noValidMaterial.add(Material.RED_SHULKER_BOX);
            noValidMaterial.add(Material.getMaterial(VersionManager.parseMaterial("SHULKER_BOX")));
            noValidMaterial.add(Material.WHITE_SHULKER_BOX);
            noValidMaterial.add(Material.YELLOW_SHULKER_BOX);
        }
        noValidMaterial.add(Material.getMaterial(VersionManager.parseMaterial("FIREWORK_STAR")));
        noValidMaterial.add(Material.getMaterial(VersionManager.parseMaterial("Material.FIREWORK_ROCKET")));

        //When item is added
        e.getInventory().forEach(item -> {
            if (item != null) {
                if (noValidMaterial.contains(item.getType())) {
                    e.getPlayer().sendMessage(TextUtils.parseColor("&cDeath Exceptions lite doesn't support items of type " + item.getType().toString()));
                    e.getPlayer().getInventory().addItem(item);
                    return;
                }
                if (!UserData.itemsDB.contains(item)) {
                    itemDB.addItem(item, (Player) e.getPlayer());
                }
            }
        });
    }
}
