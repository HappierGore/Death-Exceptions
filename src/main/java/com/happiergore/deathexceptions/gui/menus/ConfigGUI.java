package com.happiergore.deathexceptions.gui.menus;

import com.happiergore.deathexceptions.gui.items.*;
import com.happiergore.deathexceptions.gui.items.configMenu.*;
import com.happiergore.deathexceptions.gui.items.types.*;
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
public final class ConfigGUI extends GUI {

    private final ItemStack configureItem;

    public ConfigGUI(ItemStack configureItem, Player player) {
        super(player);

        this.setInventory(Bukkit.createInventory((GUI) this, 27, "Configure item"));
        this.configureItem = configureItem;

        this.getItems().add(new DisableDisplay(this.getInventory(), 16, this.configureItem));
        this.getItems().add(new DisableLore(this.getInventory(), 14, this.configureItem));
        this.getItems().add(new DisableEnchants(this.getInventory(), 12, this.configureItem));
        this.getItems().add(new DisableNBT(this.getInventory(), 10, this.configureItem));
        this.getItems().add(new BackDBMenu(this.getInventory(), 22, player));
        this.getItems().add(new SeeModules(this.getInventory(), 4, this.configureItem));

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
