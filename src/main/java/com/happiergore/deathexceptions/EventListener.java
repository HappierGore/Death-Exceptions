/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.happiergore.deathexceptions;

import commands.ItemKeepInventory;
import events.OnDeathPlayer;
import events.OnRespawnPlayer;
import static helper.IOHelper.ExportResource;
import helper.TextUtils;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import mysqlite.ItemDB;
import mysqlite.MySQLite;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author HappierGore
 */
public class EventListener extends JavaPlugin implements Listener {

    public static FileConfiguration configYML;

    @Override
    public void onEnable() {

        MySQLite.path = "jdbc:sqlite:" + dbPath.replace('\\', '/') + "/SavedItems.db";

        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        new ItemDB().loadAllData();

        configYML = getConfig();

        //Generar base de datos en caso de que no exista
        generateDB();

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
        enabledMsg.append("&bDeath Exceptions cargado correctamente\n");
        enabledMsg.append("&b¡Gracias por tu preferencia!\n");
        enabledMsg.append("&6Autor: HappierGore");
        enabledMsg.append("&9Discord: &nHappierGore#\n\n");
        enabledMsg.append("&9********************\n");

        System.out.println(TextUtils.parseColor(enabledMsg.toString()));

        //Registrar eventos
        getServer().getPluginManager().registerEvents(this, this);
    }

    //****************
    // EVENTOS
    //****************
    String dbPath = getDataFolder().getAbsolutePath();

    @EventHandler
    public void PlayerDeathEvent(PlayerDeathEvent e) {
        OnDeathPlayer.registerKill(e);
    }

    @EventHandler
    public void PlayerRespawnEvent(PlayerRespawnEvent e) {
        OnRespawnPlayer.onRespawnPlayer(e);
    }

    //********************
    //Own functions
    //********************
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
        this.getCommand("itemkeep").setExecutor(new ItemKeepInventory());
    }
}
