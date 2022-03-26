package sqlite;

import com.happiergore.deathexceptions.EventListener;
import static helper.IOHelper.ExportResource;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;

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
                return false;
            }
        } catch (ClassNotFoundException ex) {
            return false;
        }

        File dataFolder = EventListener.getPlugin(EventListener.class).getDataFolder();

        path = "jdbc:sqlite:" + dataFolder.getAbsolutePath().replace('\\', '/') + "/" + DBNAME;

        if (!dataFolder.exists()) {
            dataFolder.mkdir();
            //Generar base de datos en caso de que no exista
        }

        generateDB(dataFolder.getAbsolutePath());

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

    private static void generateDB(String dataFolderPath) {
        File dataBase = new File(dataFolderPath + "/" + DBNAME);

        if (!dataBase.exists()) {
            try {
                ExportResource("/" + DBNAME, dataFolderPath);
            } catch (Exception ex) {
                System.out.println("An error occured when trying to export the database" + ex);
            }
        }
    }

}
