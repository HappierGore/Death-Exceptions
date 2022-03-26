package gui.menus;

import gui.items.Back;
import gui.items.configMenu.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappierGore
 */
public final class ConfigGUI extends GUI {

    private final ItemStack configureItem;

    public ConfigGUI(ItemStack configureItem, Player player) {
        super(Bukkit.createInventory(null, 27, "Configure item"), player);
        this.configureItem = configureItem;

        this.getItems().add(new DisableDisplay(this.getInventory(), 16, this.configureItem));
        this.getItems().add(new DisableLore(this.getInventory(), 14, this.configureItem));
        this.getItems().add(new DisableEnchants(this.getInventory(), 12, this.configureItem));
        this.getItems().add(new DisableNBT(this.getInventory(), 10, this.configureItem));
        this.getItems().add(new Back(this.getInventory(), 22, player));
        this.getItems().add(new SeeModules(this.getInventory(), 4, this.configureItem));
    }

    @Override
    public void onClose(InventoryCloseEvent e) {
    }

}
