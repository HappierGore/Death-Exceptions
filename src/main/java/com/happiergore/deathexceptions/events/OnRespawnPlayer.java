/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.happiergore.deathexceptions.events;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;
import com.happiergore.deathexceptions.user.UserData;

/**
 *
 * @author HappierGore
 */
public class OnRespawnPlayer {

    public static void onRespawnPlayer(PlayerRespawnEvent e) {
        Player player = e.getPlayer();

        UserData userData = UserData.getObj(player.getUniqueId().toString());
        
        userData.itemsToRespawn.forEach(item -> {
            player.getInventory().addItem(item);
        });
    }
}
