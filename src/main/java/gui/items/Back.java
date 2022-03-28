package gui.items;

import gui.items.types.Behaviour;
import gui.menus.DBGUI;
import helper.ItemUtils;
import helper.TextUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappierGore
 */
public class Back extends Behaviour {

    private final Player player;

    public Back(Inventory inventory, int slot, Player player) {
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

}
