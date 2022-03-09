package commands;

import gui.GUIManager;
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

        String noPermissionMsg = TextUtils.parseColor("&cNo tienes los permisos necesarios para ejecutar este comando.");

        switch (args[0].toLowerCase()) {
            case "createitem" -> {
                if (!sender.hasPermission("deathExceptions.createItem")) {
                    sender.sendMessage(noPermissionMsg);
                    break;
                }
                CreateItem.createItem(player);
            }
            case "additem" -> {
                if (!sender.hasPermission("deathExceptions.addItem")) {
                    sender.sendMessage(noPermissionMsg);
                    break;
                }
                AddItem.addItem(player);
            }
            case "gui" -> {
                if (!sender.hasPermission("deathExceptions.gui")) {
                    sender.sendMessage(noPermissionMsg);
                    break;
                }
                GUIManager.getObj(player).openItemsDB();
            }
            default -> {
                player.sendMessage(TextUtils.parseColor("&cEse comando no existe."));
            }
        }

        return false;
    }
}
