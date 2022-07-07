package com.happiergore.deathexceptions.gui.items.types;

import com.happiergore.deathexceptions.gui.menus.GUI;
import static com.happiergore.deathexceptions.helper.TextUtils.parseColor;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.Inventory;

/**
 *
 * @author HappierGore
 */
public abstract class TextRequest extends Behaviour {

    public static Map<Player, TextRequest> StandingBy = new HashMap<>();

    private String text = "";
    private final String msgRequest;

    public TextRequest(Inventory inventory, int slot, String msgRequest) {
        super(inventory, slot);
        this.msgRequest = msgRequest;
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        e.getWhoClicked().closeInventory();
        e.getWhoClicked().sendMessage(parseColor(msgRequest));
        StandingBy.put((Player) e.getWhoClicked(), this);
    }

    public void onTextEntry(PlayerChatEvent e) {
        this.text = e.getMessage();
        StandingBy.remove((e.getPlayer()));
        e.setCancelled(true);
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
