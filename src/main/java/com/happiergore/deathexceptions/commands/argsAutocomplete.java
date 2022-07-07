package com.happiergore.deathexceptions.commands;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

/**
 *
 * @author HappierGore
 */
public class argsAutocomplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

        List<String> completions = new ArrayList<>();

        if (args.length <= 1) {

            if (sender.hasPermission("deathExceptions.addItem")) {
                completions.add("addItem");
            }
            if (sender.hasPermission("deathExceptions.createItem")) {
                completions.add("createItem");
            }
            if(sender.hasPermission("deathExceptions.gui")){
                completions.add("gui");
            }

        }
        return completions;
    }

}
