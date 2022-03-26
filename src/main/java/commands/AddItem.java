package commands;

import helper.TextUtils;
import sqlite.ItemDB;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 *
 * @author HappierGore
 */
public class AddItem {

    public static void addItem(Player player) {

        if (!player.hasPermission("deathExceptions.addItem")) {
            player.sendMessage(TextUtils.parseColor("&cYou don't have permissions to execute this command"));
            return;
        }

        if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
            player.sendMessage(TextUtils.parseColor("&cYou need an item to execute this command"));
            return;
        }

        if (ItemDB.addItem(player.getInventory().getItemInMainHand())) {
            player.sendMessage(TextUtils.parseColor("&aThis item has been added into exceptions!"));
        } else {
            player.sendMessage(TextUtils.parseColor("&cThis item is already in exceptions list."));

        }
    }
}
