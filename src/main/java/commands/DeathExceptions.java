package commands;

import gui.GUIManager;
import helper.TextUtils;
import mysqlite.ItemDB;
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
            System.out.println("This command is only for players");
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(TextUtils.parseColor("&cImcomplent command, need to specify arguments. Ex: &bcreate, &aadd"));
            return false;
        }

        String noPermissionMsg = TextUtils.parseColor("&cYou don't have permissions to execute this command");

        switch (args[0].toLowerCase()) {
            case "createitem": {
                if (!sender.hasPermission("deathExceptions.createItem")) {
                    sender.sendMessage(noPermissionMsg);
                    break;
                }
                CreateItem.createItem(player);
                break;
            }
            case "additem": {
                if (!sender.hasPermission("deathExceptions.addItem")) {
                    sender.sendMessage(noPermissionMsg);
                    break;
                }
                AddItem.addItem(player);
                break;
            }
            case "gui": {
                if (!sender.hasPermission("deathExceptions.gui")) {
                    sender.sendMessage(noPermissionMsg);
                    break;
                }
                GUIManager.getObj(player).openItemsDB();
                break;
            }
            case "debug": {
                ItemDB.itemsDB.forEach(item -> {
                    player.getInventory().addItem(item);
                });
                break;
            }
            case "remove": {
                ItemDB.remove(player.getInventory().getItemInMainHand());
                break;
            }
            default: {
                player.sendMessage(TextUtils.parseColor("&cThat command doesn't exist."));
                break;
            }
        }

        return false;
    }
}
