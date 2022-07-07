package com.happiergore.deathexceptions.gui.items.types;

import com.happiergore.deathexceptions.gui.items.ItemGUI;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

/**
 *
 * @author HappierGore
 */
public abstract class Behaviour extends ItemGUI {

    public Behaviour(Inventory inventory, int slot) {
        super(inventory, slot);
    }

    public abstract void onClick(InventoryClickEvent e);

    public abstract void onLoad();

}
