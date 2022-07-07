package com.happiergore.deathexceptions.gui.items;

import com.happiergore.deathexceptions.gui.items.types.Behaviour;
import com.happiergore.deathexceptions.gui.menus.DBGUI;
import com.happiergore.deathexceptions.helper.ItemUtils;
import com.happiergore.deathexceptions.helper.TextUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappierGore
 */
public class BackDBMenu extends Behaviour {

    private final Player player;

    public BackDBMenu(Inventory inventory, int slot, Player player) {
        super(inventory, slot);
        this.player = player;
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        new DBGUI(this.player).open();
    }

    @Override
    public void onLoad() {
        this.getInventory().setItem(getSlot(), this.getItem());
    }

    @Override
    public ItemStack generateMainItem() {
        return ItemUtils.generateItem(null, Material.BARRIER, TextUtils.parseColor("&9Regresar"), null, null);
    }
    
    public Player getPlayer(){
        return this.player;
    }

}
