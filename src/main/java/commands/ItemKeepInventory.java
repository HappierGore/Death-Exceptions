/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package commands;

import de.tr7zw.nbtapi.NBTItem;
import mysqlite.ItemDB;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import user.UserData;

/**
 *
 * @author HappierGore
 */
public class ItemKeepInventory implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] strings) {
        if (!(sender instanceof Player)) {
            System.out.println("Este comando sólo puede ser ejecutado por jugadores");
            return false;
        }

        Player player = (Player) sender;

//        NBTItem item = new NBTItem(player.getInventory().getItemInMainHand());
//        int slot = player.getInventory().getHeldItemSlot();
//
//        item.setString("keepInventory", "true");
//
//        player.getInventory().setItem(slot, item.getItem());
//
//        ItemDB itemdb = new ItemDB();
//
//        player.sendMessage("Tu item ahora no puede ser dropeado");
//
        new ItemDB().addItem(player.getInventory().getItemInMainHand());

        UserData.itemsDB.forEach(entry -> {
            player.getInventory().addItem(entry);
        });

        return false;
    }
}
