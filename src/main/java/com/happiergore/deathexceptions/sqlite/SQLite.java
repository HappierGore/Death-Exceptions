package com.happiergore.deathexceptions.sqlite;

import com.happiergore.deathexceptions.EventListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author HappierGore
 */
public abstract class SQLite {

    public static String path;
    public static final String TABLE = "savedItems";
    public static final String DBNAME = "SavedItems.db";

    public static boolean initialize() {
        try {
            try {
                Class.forName("org.sqlite.JDBC").newInstance();
            } catch (InstantiationException | IllegalAccessException ex) {
                ex.printStackTrace(System.out);
                return false;
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace(System.out);
            return false;
        }

        File dataFolder = EventListener.getPlugin(EventListener.class).getDataFolder();

        if (!dataFolder.exists()) {
            dataFolder.mkdir();
            //Generar base de datos en caso de que no exista
        }

        path = "jdbc:sqlite:" + dataFolder.getAbsolutePath().replace('\\', '/') + "/" + DBNAME;

        generateDB();

        ItemDAO.loadItems();

        CheckUpdatedDB.checkDBVersion();

        return true;
    }

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(path);
        } catch (SQLException e) {
            System.out.println("Error from connect: " + e.getMessage());
        }
        return conn;
    }

    private static void generateDB() {

        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE + "(\"NBT\" TEXT NOT NULL UNIQUE, \"flags\" TEXT, \"modules\" TEXT, PRIMARY KEY(\"NBT\"))";

        try ( Connection conn = connect();  Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            stmt.close();

        } catch (SQLException e) {
            System.out.println("[DataBase] Error while creating database" + e);
        }
    }

}
