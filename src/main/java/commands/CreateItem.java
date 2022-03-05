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
            player.sendMessage(TextUtils.parseColor("&cNo tienes los permisos necesarios para ejecutar este comando"));
            return;
        }
        
        if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
            player.sendMessage(TextUtils.parseColor("&cNecesitas tener un item en tu mano para ejecutar este comando."));
            return;
        }
        
        NBTItem item = new NBTItem(player.getInventory().getItemInMainHand());
        
        int slot = player.getInventory().getHeldItemSlot();
        
        item.setString("keepInventory", "true");
        
        player.getInventory().setItem(slot, item.getItem());
        
        player.sendMessage(TextUtils.parseColor("&aTu item ahora no se dropeará al morir."));
        
    }
    
}
