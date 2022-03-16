package gui.items;

import gui.GUIManager;
import helper.TextUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 *
 * @author HappierGore
 */
public class Back extends ItemGUI {

    private final Player player;

    public Back(Inventory inventory, int slot, Player player) {
        super(inventory, slot);

        final String displayName = TextUtils.parseColor("&9Regresar");

        this.setItem(this.generateItem(null, Material.BARRIER, displayName, null, null));
        this.player = player;

    }

    @Override
    public void onClick() {
        player.closeInventory();
        GUIManager guiManager = GUIManager.getObj(player);
        guiManager.openItemsDB();
    }

}
