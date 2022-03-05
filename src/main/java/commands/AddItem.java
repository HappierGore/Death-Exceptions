package commands;

import helper.TextUtils;
import mysqlite.ItemDB;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import user.UserData;

/**
 *
 * @author HappierGore
 */
public class AddItem {

    public static void addItem(Player player) {

        if (!player.hasPermission("deathExceptions.addItem")) {
            player.sendMessage(TextUtils.parseColor("&cNo tienes los permisos necesarios para ejecutar este comando"));
            return;
        }

        if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
            player.sendMessage(TextUtils.parseColor("&cNecesitas tener un item en tu mano para ejecutar este comando."));
            return;
        }

        new ItemDB().addItem(player.getInventory().getItemInMainHand());

        player.sendMessage(TextUtils.parseColor("&aSe ha añadido el objeto de tu mano a la base de datos para prevenir su dropeo!"));

    }
}
