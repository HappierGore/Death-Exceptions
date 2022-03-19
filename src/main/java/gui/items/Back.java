package gui.items;

import gui.GUIManager;
import gui.items.types.Behaviour;
import helper.ItemUtils;
import helper.TextUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 *
 * @author HappierGore
 */
public class Back extends Behaviour {

    private final Player player;

    public Back(Inventory inventory, int slot, Player player) {
        final String displayName = TextUtils.parseColor("&9Regresar");

        Initialize(inventory, ItemUtils.generateItem(null, Material.BARRIER, displayName, null, null), slot);

        this.player = player;
    }

    @Override
    public void onClick() {
        player.closeInventory();
        GUIManager guiManager = GUIManager.getObj(player);
        guiManager.openItemsDB();
    }

    @Override
    public void onLoad() {
        this.getInventory().setItem(getSlot(), this.getItem());
    }

}
