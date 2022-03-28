package gui.menus;

import gui.UpdateDBContent;
import sqlite.ItemDB;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappierGore
 */
public class DBGUI extends GUI {

    public DBGUI(Player player) {
        super(player);
        this.setInventory(Bukkit.createInventory((GUI) this, (int) (Math.ceil((ItemDB.getDBItems().size() / 9.0f) + 0.5)) * 9, "Protected items"));
    }

    @Override
    public void onInventoryClick(InventoryClickEvent e) {
        ItemStack item = e.getCurrentItem();
        if (e.getClick() == ClickType.RIGHT) {
            if (item != null && item.getType() != Material.AIR) {
                e.setCancelled(true);
                new ConfigGUI(item, this.getPlayer()).open();
            }
        }
    }

    @Override
    public void onOpen() {
        this.getInventory().clear();
        ItemDB.getDBItems().forEach((t) -> {
            this.getInventory().addItem(t);
        });
    }

    @Override
    public void onClose(InventoryCloseEvent e) {
        UpdateDBContent.updateDBContent(e);
    }
}
