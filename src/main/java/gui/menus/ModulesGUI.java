package gui.menus;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappierGore
 */
public final class ModulesGUI extends GUI {

    private ItemStack configItem;

    public ModulesGUI(ItemStack configItem, Player player) {
        super(Bukkit.createInventory(null, 27, "Modules"), player);
        this.configItem = configItem;
    }

    @Override
    public void onClose(InventoryCloseEvent e) {
    }

}
