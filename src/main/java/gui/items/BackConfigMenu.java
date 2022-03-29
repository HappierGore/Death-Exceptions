package gui.items;

import gui.menus.ConfigGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappierGore
 */
public class BackConfigMenu extends BackDBMenu {

    private final ItemStack configItem;

    public BackConfigMenu(Inventory inventory, int slot, Player player, ItemStack configItem) {
        super(inventory, slot, player);
        this.configItem = configItem;
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        new ConfigGUI(this.configItem, this.getPlayer()).open();
    }

}
