package gui.menus;

import gui.UpdateDBContent;
import sqlite.ItemDB;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappierGore
 */
public class DBGUI extends GUI {

    public DBGUI(Player player) {
        super(Bukkit.createInventory(null, (int) (Math.ceil((ItemDB.getDBItems().size() / 9.0f) + 0.5)) * 9, "Protected items"), player);
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        this.getInventory().clear();
        ItemDB.getDBItems().forEach((t) -> {
            this.getInventory().addItem(t);
        });
    }

    @Override
    public void evaluateItem(ItemStack item, InventoryClickEvent e) {
        if (e.getClick() == ClickType.RIGHT) {
            if (item != null && item.getType() != Material.AIR) {
                new ConfigGUI(item, this.getPlayer()).open(null);
            }
        }
    }

    @Override
    public void onClose(InventoryCloseEvent e) {
        UpdateDBContent.updateDBContent(e);
    }
}
