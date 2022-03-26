package gui.items.configMenu;

import gui.items.types.Behaviour;
import gui.menus.ModulesGUI;
import helper.ItemUtils;
import static helper.TextUtils.parseColor;
import static helper.VersionManager.parseMaterial;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappierGore
 */
public class SeeModules extends Behaviour {

    private final ItemStack configItem;

    public SeeModules(Inventory inventory, int slot, ItemStack configItem) {
        super(inventory, slot);
        this.configItem = configItem;
    }

    @Override
    public ItemStack generateMainItem() {
        List<String> lore = new ArrayList<>();
        lore.add(parseColor("&bAdd extra modules to the item"));
        Material material = Material.getMaterial(parseMaterial("EXPERIENCE_BOTTLE"));
        return ItemUtils.generateItem(null, material, parseColor("&bModules"), lore, null);
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        new ModulesGUI(this.configItem, (Player) e.getWhoClicked()).open(null);
    }

    @Override
    public void onLoad() {
        this.getInventory().setItem(this.getSlot(), this.getItem());
    }

}
