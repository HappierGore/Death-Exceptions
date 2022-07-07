package com.happiergore.deathexceptions.gui.menus;

import com.happiergore.deathexceptions.gui.items.BackConfigMenu;
import com.happiergore.deathexceptions.gui.items.modulesMenu.enableDropChance;
import com.happiergore.deathexceptions.gui.items.types.Behaviour;
import com.happiergore.deathexceptions.helper.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappierGore
 */
public final class ModulesGUI extends GUI {

    private final ItemStack configItem;

    public ModulesGUI(ItemStack configItem, Player player) {
        super(player);

        this.setInventory(Bukkit.createInventory((GUI) this, 27, "Modules"));

        this.configItem = configItem;

        this.getItems().add(new enableDropChance(this.getInventory(), 10, this.configItem));
        this.getItems().add(new BackConfigMenu(this.getInventory(), 22, player, this.configItem));
    }

    @Override
    public void onClose(InventoryCloseEvent e) {
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        this.getItems().forEach(item -> {
            if (ItemUtils.compareItems(item.getItem(), event.getCurrentItem())) {
                ((Behaviour) item).onClick(event);
                event.setCancelled(true);
            }
        });
    }

    @Override
    public void onOpen() {
        this.getItems().forEach(item -> {
            ((Behaviour) item).onLoad();
        });
    }

}
