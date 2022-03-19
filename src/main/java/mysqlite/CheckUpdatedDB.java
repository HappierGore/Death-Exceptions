package mysqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import static mysqlite.SQLite.TABLE;
import static mysqlite.SQLite.connect;

/**
 *
 * @author HappierGore
 */
public class CheckUpdatedDB extends SQLite {

    public static void checkDBVersion() {

        String sql = "SELECT Modules FROM " + TABLE;

        try (Connection conn = connect(); PreparedStatement db = conn.prepareStatement(sql)) {
            db.executeQuery();

        } catch (SQLException e) {
            if (e.getErrorCode() == 1 || e.toString().contains("no such column")) {
                System.out.println("[DataBase] The column \"Modules\" wasn't found. Trying to create one...");
                generateModuleColumn();
                return;
            }
            System.out.println("[DataBase] Error from getFlags, with code: " + e.getErrorCode() + "\n" + e);
        }
    }

    private static void generateModuleColumn() {

        String sql = "ALTER TABLE " + TABLE + " ADD Modules TEXT";

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("[DataBase] Column \"Modules\" has been created successfully");

        } catch (SQLException e) {
            System.out.println("[DataBase] Error while creating the column \"Modules\": " + e);
        }
    }
}
