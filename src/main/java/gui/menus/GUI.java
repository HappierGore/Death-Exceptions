package gui.menus;

import gui.items.types.Behaviour;
import helper.ItemUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappierGore
 */
public abstract class GUI {

    public static Map<Player, Set<GUI>> GUIData = new HashMap<>();

    //****************
    //    CLASS
    //***************
    private final Inventory inventory;
    private final List<Behaviour> items = new ArrayList<>();
    private final Player player;

    public GUI(Inventory inventory, Player player) {
        this.inventory = inventory;
        this.player = player;
        if (GUIData.containsKey(player)) {
            GUIData.get(player).add((GUI) this);
        } else {
            Set<GUI> gui = new HashSet<>();
            gui.add((GUI) this);
            GUIData.put(player, gui);
        }
    }

    public void evaluateItem(ItemStack item, InventoryClickEvent event) {
        for (Behaviour entry : this.getItems()) {
            if (ItemUtils.compareItems(entry.getItem(), item)) {
                entry.onClick(event);
                return;
            }
        }
    }

    public final void open(InventoryOpenEvent event) {
        this.player.openInventory(this.inventory);
        this.onOpen(event);
    }

    public final void close(InventoryCloseEvent e) {
        onClose(e);
    }

    public void removeMenu(GUI menu) {
        GUIData.get(this.player).remove(menu);
    }

    public void onOpen(InventoryOpenEvent event) {
        this.items.forEach(item -> {
            ((Behaviour) item).onLoad();
        });
    }

    public abstract void onClose(InventoryCloseEvent e);

    //*******************
    // Getters & Setters
    //*******************
    public Inventory getInventory() {
        return inventory;
    }

    public List<Behaviour> getItems() {
        return items;
    }

    public Player getPlayer() {
        return this.player;
    }

    @Override
    public String toString() {
        return "GUI{" + "inventory=" + inventory + ", items=" + items + ", player=" + player + '}';
    }

}
