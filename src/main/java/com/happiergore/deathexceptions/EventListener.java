package com.happiergore.deathexceptions;

import commands.DeathExceptions;
import commands.argsAutocomplete;
import events.CloseGUI;
import events.OnClickGUI;
import events.OnDeathPlayer;
import events.OnRespawnPlayer;
import static helper.IOHelper.ExportResource;
import helper.TextUtils;
import helper.VersionManager;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import mysqlite.ItemDB;
import mysqlite.MySQLite;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
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

        try {
            try {
                Class.forName("org.sqlite.JDBC").newInstance();
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(EventListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EventListener.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (!setupManager()) {
            return;
        }

        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
            //Generar base de datos en caso de que no exista
            generateDB();
        }

        MySQLite.path = "jdbc:sqlite:" + dbPath.replace('\\', '/') + "/SavedItems.db";

        ItemDB.loadAllData();

        configYML = getConfig();

        //Crear config.yml en caso de que no exista
        saveDefaultConfig();

        //Si no se cumple con alguna dependencia, se cancela el plugin
        if (!checkDependencies()) {
            return;
        }

        //Comandos
        registerCommands();

        StringBuilder enabledMsg = new StringBuilder();
        enabledMsg.append("&9********************\n\n");
        enabledMsg.append("&bDeath Exceptions has been loaded\n");
        enabledMsg.append("&b¡Thanks for your preference!\n");
        enabledMsg.append("&6Autor: HappierGore");
        enabledMsg.append("&9Discord: &nHappierGore#1197\n\n");
        enabledMsg.append("&9********************\n");

        System.out.println(TextUtils.parseColor(enabledMsg.toString()));

        //Registrar eventos
        getServer().getPluginManager().registerEvents(this, this);
    }

    //****************
    // EVENTOS
    //****************
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
        OnClickGUI.onClickGUI(e);
    }

    //********************
    //Own functions
    //********************
    String dbPath = getDataFolder().getAbsolutePath();

    private void generateDB() {
        String path = getDataFolder().getAbsolutePath();

        File dataBase = new File(path + "/SavedItems.db");

        if (!dataBase.exists()) {
            try {
                ExportResource("/SavedItems.db", path);
            } catch (Exception ex) {
                Logger.getLogger(EventListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private boolean checkDependencies() {

        if (getServer().getPluginManager().getPlugin("NBTAPI") == null) {
            System.out.println("************** ERROR **************\n\nNo se encontró NBTI API. Este plugin es necesario para funcionar.\nLink: https://www.spigotmc.org/resources/nbt-api.7939/\n\n***********************************");
            getServer().getPluginManager().disablePlugin(this);
            return false;
        }

        return true;
    }

    private void registerCommands() {
        this.getCommand("deathexceptions").setTabCompleter(new argsAutocomplete());
        this.getCommand("deathexceptions").setExecutor(new DeathExceptions());
    }

    private boolean setupManager() {
        sversion = "N/A";
        try {
            sversion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }

        if (sversion.contains("v1_5")) {
            SuccessMessage("v1.5");
            VersionManager.version = 5;
            return true;
        } else if (sversion.contains("v1_6")) {
            SuccessMessage("v1.6");
            VersionManager.version = 6;
            return true;
        } else if (sversion.contains("v1_7")) {
            SuccessMessage("v1.7");
            VersionManager.version = 7;
            return true;
        } else if (sversion.contains("v1_8")) {
            SuccessMessage("v1.8");
            VersionManager.version = 8;
            return true;
        } else if (sversion.contains("v1_9")) {
            SuccessMessage("v1.9");
            VersionManager.version = 9;
            return true;
        } else if (sversion.contains("v1_10")) {
            SuccessMessage("v1.10");
            VersionManager.version = 10;
            return true;
        } else if (sversion.contains("v1_11")) {
            SuccessMessage("v1.11");
            VersionManager.version = 11;
            return true;
        } else if (sversion.contains("v1_12")) {
            SuccessMessage("v1.12");
            VersionManager.version = 12;
            return true;
        } else if (sversion.contains("v1_13")) {
            SuccessMessage("v1.13");
            VersionManager.version = 13;
            return true;
        } else if (sversion.contains("v1_14")) {
            SuccessMessage("v1.14");
            VersionManager.version = 14;
            return true;
        } else if (sversion.contains("v1_15")) {
            SuccessMessage("v1.15");
            VersionManager.version = 15;
            return true;
        } else if (sversion.contains("v1_16")) {
            SuccessMessage("v1.16");
            VersionManager.version = 16;
            return true;
        } else if (sversion.contains("v1_17")) {
            SuccessMessage("v1.17");
            VersionManager.version = 17;
            return true;
        } else if (sversion.contains("v1_18")) {
            SuccessMessage("v1.18");
            VersionManager.version = 18;
            return true;
        }

        System.out.println("The version " + sversion + " is not suported.");
        this.getServer().getPluginManager().disablePlugin(this);

        return false;
    }

    private void SuccessMessage(String version) {
        System.out.println("\n§3------------------ §bDeathExceptions - Logger §3------------------");
        System.out.println("\nClient version " + sversion + " \nPlugin version selected: " + version);
    }
}
