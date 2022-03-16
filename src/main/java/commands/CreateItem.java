package commands;

import de.tr7zw.nbtapi.NBTItem;
import helper.TextUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 *
 * @author HappierGore
 */
public class CreateItem {
    
    public static void createItem(Player player) {
        
        if (!player.hasPermission("deathExceptions.createItem")) {
            player.sendMessage(TextUtils.parseColor("&cYou don't have permissions to execute this command"));
            return;
        }
        
        if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
            player.sendMessage(TextUtils.parseColor("&cYou need an item to execute this command"));
            return;
        }
        
        NBTItem item = new NBTItem(player.getInventory().getItemInMainHand());
        
        int slot = player.getInventory().getHeldItemSlot();
        
        item.setString("keepInventory", "true");
        
        player.getInventory().setItem(slot, item.getItem());
        
        player.sendMessage(TextUtils.parseColor("&aYour item won't be dropped anymore"));
        
    }
    
}
