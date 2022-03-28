package commands;

import gui.menus.DBGUI;
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
            System.out.println("This command is only for players");
            return false;
        }

        Player player = (Player) sender;

        String availableParameters = TextUtils.parseColor("&cUnknown argument. Available arguments: &acreate, add, gui");

        if (args.length == 0) {
            player.sendMessage(availableParameters);
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
                new DBGUI(player).open();
                break;
            }
//            case "debug": {
//                ItemDB.itemsDB.forEach(item -> {
//                    player.getInventory().addItem(item);
//                });
//                break;
//            }
//            case "remove": {
//                ItemDB.remove(player.getInventory().getItemInMainHand());
//                break;
//            }
            default: {
                player.sendMessage(availableParameters);
                break;
            }
        }

        return false;
    }
}
