package commands;

import helper.TextUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author HappierGore
 */
public class DeathExceptions implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if (!(sender instanceof Player)) {
            System.out.println("Este comando sólo puede ser ejecutado por jugadores");
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(TextUtils.parseColor("&cComando incompleto. Necesitas especificar un argumento. Ex: &bcreate, &aadd"));
            return false;
        }

        switch (args[0].toLowerCase()) {
            case "createitem" -> {
                CreateItem.createItem(player);
            }
            case "additem" -> {
                AddItem.addItem(player);
            }
            case "gui" -> {
                GUIManager.getObj(player.getUniqueId().toString()).openInventory(player);
            }
            default -> {
                player.sendMessage(TextUtils.parseColor("&cEse comando no existe."));
            }
        }

        return false;
    }
}
