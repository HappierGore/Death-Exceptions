package com.happiergore.deathexceptions.gui.menus;

import com.happiergore.deathexceptions.gui.items.types.Behaviour;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

/**
 *
 * @author HappierGore
 */
public abstract class GUI implements InventoryHolder {

    //****************
    //    CLASS
    //***************
    private Inventory inventory;
    private final List<Behaviour> items = new ArrayList<>();
    private final Player player;

    public GUI(Player player) {
        this.player = player;
    }

    public final void open() {
        this.player.openInventory(this.getInventory());
        this.onOpen();
    }

    public final void close(InventoryCloseEvent e) {
        this.onClose(e);
    }

    public abstract void onInventoryClick(InventoryClickEvent event);

    public abstract void onOpen();

    public abstract void onClose(InventoryCloseEvent e);

    //*******************
    // Getters & Setters
    //*******************
    public List<Behaviour> getItems() {
        return items;
    }

    public Player getPlayer() {
        return this.player;
    }

    @Override
    public String toString() {
        return "GUI{" + "inventory=" + this.getInventory() + ", items=" + items + ", player=" + player + ", Memory = " + super.toString() + "}";
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

}
