package com.happiergore.deathexceptions;

import commands.DeathExceptions;
import commands.argsAutocomplete;
import events.CloseGUI;
import events.OnClickGUI;
import events.OnDeathPlayer;
import events.OnPlayerChat;
import events.OnRespawnPlayer;
import helper.TextUtils;
import helper.VersionManager;
import sqlite.ItemDAO;
import sqlite.SQLite;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author HappierGore
 */
public class EventListener extends JavaPlugin implements Listener {

    private String sversion;

    public static FileConfiguration configYML;

    @Override
    public void onEnable() {

        System.out.println(TextUtils.parseColor("\n&3------------------ §bDeathExceptions - Logger §3------------------"));

        if (!SQLite.initialize() || !checkDependencies() || !setupManager()) {
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        System.out.println(TextUtils.parseColor("&9------------------------------------------------------------------"));

        ItemDAO.loadItems();

        configYML = getConfig();

        //Crear config.yml en caso de que no exista
        saveDefaultConfig();

        //Comandos
        registerCommands();

        //Registrar eventos
        getServer().getPluginManager().registerEvents(this, this);
    }

    //***********************
    //        EVENTOS
    //***********************
    @EventHandler
    public void PlayerDeathEvent(PlayerDeathEvent e) {
        OnDeathPlayer.registerKill(e);
    }

    @EventHandler
    public void PlayerRespawnEvent(PlayerRespawnEvent e) {
        OnRespawnPlayer.onRespawnPlayer(e);
    }

    @EventHandler
    public void InventoryCloseEvent(InventoryCloseEvent e) {
        CloseGUI.onCloseInv(e);
    }

    @EventHandler
    public void InventoryClickEvent(InventoryClickEvent e) {
        OnClickGUI.onInventoryClick(e);
    }

    @EventHandler
    public void OnPlayerChat(PlayerChatEvent e) {
        OnPlayerChat.OnPlayerChat(e);
    }

    //***********************
    //        Helper
    //***********************
    private boolean checkDependencies() {
        if (getServer().getPluginManager().getPlugin("NBTAPI") == null) {
            System.out.println("\n************** ERROR **************\n\n[Dependencies] NBT API wasn't found. This plugin is necesary to DeathExceptions.\nPlease, download it.\nLink: https://www.spigotmc.org/resources/nbt-api.7939/\n\n***********************************");
            getServer().getPluginManager().disablePlugin(this);
            return false;
        }
        System.out.println("[Dependencies] NBT API Found, starting...");
        return true;
    }

    private void registerCommands() {
        this.getCommand("deathexceptions").setTabCompleter(new argsAutocomplete());
        this.getCommand("deathexceptions").setExecutor(new DeathExceptions());
    }

    private void successMessage(String version) {

        StringBuilder enabledMsg = new StringBuilder();
        enabledMsg.append("\n&bDeath Exceptions has been loaded sucessfuly!\n\n");
        enabledMsg.append("Client version: ").append(sversion).append(" \nPlugin version selected: ").append(version);
        enabledMsg.append("\n\n&6Autor: HappierGore\n");
        enabledMsg.append("&9Discord: &nHappierGore#1197\n");
        enabledMsg.append("&3Spigot: https://www.spigotmc.org/resources/death-exceptions.100568/\n");
        enabledMsg.append("&eSupport: https://discord.gg/ZKy5eVPxW5\n");
        enabledMsg.append("&9Please, leave me a rating! <3\n\n");

        System.out.println(TextUtils.parseColor(enabledMsg.toString()));
    }

    private boolean setupManager() {
        sversion = "N/A";
        try {
            sversion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("WARNING: DeathExceptions wasn't able to get your client version.\nWill start with default version...");
            sversion = "v1_18";
            return false;
        }

        if (sversion.contains("v1_5")) {
            successMessage("v1.5");
            VersionManager.version = 5;
            return true;
        } else if (sversion.contains("v1_6")) {
            successMessage("v1.6");
            VersionManager.version = 6;
            return true;
        } else if (sversion.contains("v1_7")) {
            successMessage("v1.7");
            VersionManager.version = 7;
            return true;
        } else if (sversion.contains("v1_8")) {
            successMessage("v1.8");
            VersionManager.version = 8;
            return true;
        } else if (sversion.contains("v1_9")) {
            successMessage("v1.9");
            VersionManager.version = 9;
            return true;
        } else if (sversion.contains("v1_10")) {
            successMessage("v1.10");
            VersionManager.version = 10;
            return true;
        } else if (sversion.contains("v1_11")) {
            successMessage("v1.11");
            VersionManager.version = 11;
            return true;
        } else if (sversion.contains("v1_12")) {
            successMessage("v1.12");
            VersionManager.version = 12;
            return true;
        } else if (sversion.contains("v1_13")) {
            successMessage("v1.13");
            VersionManager.version = 13;
            return true;
        } else if (sversion.contains("v1_14")) {
            successMessage("v1.14");
            VersionManager.version = 14;
            return true;
        } else if (sversion.contains("v1_15")) {
            successMessage("v1.15");
            VersionManager.version = 15;
            return true;
        } else if (sversion.contains("v1_16")) {
            successMessage("v1.16");
            VersionManager.version = 16;
            return true;
        } else if (sversion.contains("v1_17")) {
            successMessage("v1.17");
            VersionManager.version = 17;
            return true;
        } else if (sversion.contains("v1_18")) {
            successMessage("v1.18");
            VersionManager.version = 18;
            return true;
        }
        System.out.println("The version " + sversion + " is not suported.");
        return false;
    }
}
