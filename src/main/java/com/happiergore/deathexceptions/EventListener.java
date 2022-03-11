package com.happiergore.deathexceptions;

import commands.DeathExceptions;
import commands.argsAutocomplete;
import events.CloseGUI;
import events.OnClickGUI;
import events.OnDeathPlayer;
import events.OnRespawnPlayer;
import static helper.IOHelper.ExportResource;
import helper.TextUtils;
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

        if (!setupManager()) {
            return;
        }

        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
            //Generar base de datos en caso de que no exista
            generateDB();
        }

        MySQLite.path = "jdbc:sqlite:" + dbPath.replace('\\', '/') + "/SavedItems.db";

        new ItemDB().loadAllData();

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

        if (sversion.contains("v1_8")) {
            SuccessMessage("v1.8");
            return true;
        } else if (sversion.contains("v1_9")) {
            SuccessMessage("v1.9");
            return true;
        } else if (sversion.contains("v1_10")) {
            SuccessMessage("v1.10");
            return true;
        } else if (sversion.contains("v1_11")) {
            SuccessMessage("v1.11");
            return true;
        } else if (sversion.contains("v1_12")) {
            SuccessMessage("v1.12");
            return true;
        } else if (sversion.contains("v1_13")) {
            SuccessMessage("v1.13");
            return true;
        } else if (sversion.contains("v1_14")) {
            SuccessMessage("v1.14");
            return true;
        } else if (sversion.contains("v1_15")) {
            SuccessMessage("v1.15");
            return true;
        } else if (sversion.contains("v1_16")) {
            SuccessMessage("v1.16");
            return true;
        } else if (sversion.contains("v1_17")) {
            SuccessMessage("v1.17");
            return true;
        } else if (sversion.contains("v1_18")) {
            SuccessMessage("v1.18");
            return true;
        }
        return false;
    }

    private void SuccessMessage(String version) {
        System.out.println("\n§3------------------ §bDeathExceptions - Logger §3------------------");
        System.out.println("\nClient version " + sversion + " \nPlugin version selected: " + version);
    }
}
