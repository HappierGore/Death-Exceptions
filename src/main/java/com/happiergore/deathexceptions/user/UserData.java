/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.happiergore.deathexceptions.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappierGore
 */
public class UserData {

    public static Map<String, UserData> userData;

    static {
        userData = new HashMap<>();
    }

    public static UserData getObj(String UUID) {
        if (UserData.userData.containsKey(UUID)) {
            return UserData.userData.get(UUID);
        }
        UserData us = new UserData();
        UserData.userData.put(UUID, us);
        return us;
    }

    public List<ItemStack> itemsToRespawn = new ArrayList<>();

}
