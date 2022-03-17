package mysqlite;

import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTItem;
import helper.DEXItem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.inventory.ItemStack;
import user.UserData;

/**
 * @author HappierGore
 */
public abstract class MySQLite {

    public static String path;
    public static final String TABLE = "savedItems";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(path);
        } catch (SQLException e) {
            System.out.println("Error from connect: " + e.getMessage());
        }
        return conn;
    }

    public static boolean addItem(ItemStack item) {

        DEXItem fixedItem = new DEXItem(item);

        String sql = "INSERT INTO " + TABLE + "(NBT) VALUES(?)";

        try (Connection conn = connect(); PreparedStatement db = conn.prepareStatement(sql)) {

            // set the corresponding param
            db.setString(1, fixedItem.NBT);

            // update 
            db.executeUpdate();
            UserData.itemsDB.add(fixedItem.getItem());
        } catch (SQLException e) {
            System.out.println("Error from addItem: " + e.getMessage());
        }
        return true;
    }

    public static void remove(ItemStack item) {

        DEXItem fixedItem = new DEXItem(item);

        String sql = "DELETE FROM " + TABLE + " WHERE NBT is ?";

        try (Connection conn = connect(); PreparedStatement db = conn.prepareStatement(sql)) {

            db.setString(1, fixedItem.NBT);

            UserData.itemsDB.remove(fixedItem.getItem());

            db.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error from remove: " + e.getMessage());
        }
    }

    public static List<ItemStack> getItems() {
        List<ItemStack> itemsDB = new ArrayList<>();

        String sql = "SELECT NBT FROM " + TABLE;

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                NBTContainer nbtItem = new NBTContainer(rs.getString("NBT"));
                itemsDB.add(NBTItem.convertNBTtoItem(nbtItem));
            }
        } catch (SQLException e) {
            System.out.println("Error from getItems: " + e.getMessage());
        }

        return itemsDB;
    }

}
