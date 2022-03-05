package mysqlite;

import de.tr7zw.nbtapi.NBTItem;
import helper.DEXItem;
import helper.ItemUtils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import java.sql.SQLException;
import org.bukkit.inventory.ItemStack;
import user.UserData;

/**
 * @author HappierGore
 */
public abstract class MySQLite {

    public static String path;
    public final String table;

    public MySQLite(String table) {
        this.table = table;
    }

    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(path);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void addItem(ItemStack item) {
        DEXItem fixedItem = new DEXItem(item);

        String sql = "INSERT INTO " + table + "(material, displayname, lore, nbt, enchantments) VALUES(?, ?, ?, ?, ?)";

        try ( Connection conn = connect();  PreparedStatement db = conn.prepareStatement(sql)) {

            // set the corresponding param
            db.setString(1, fixedItem.MATERIAL);
            db.setString(2, fixedItem.DISPLAYNAME);
            db.setString(3, fixedItem.LORE);
            db.setString(4, fixedItem.NBT);
            db.setString(5, fixedItem.ENCHANTMENTS);

            // update 
            db.executeUpdate();
            UserData.itemsDB.add(fixedItem.getItem());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void remove(ItemStack item) {

        DEXItem fixedItem = new DEXItem(item);

        String sql = "DELETE FROM " + table + " WHERE enchantments is ? AND material is ? AND displayname is ? AND lore is ? AND nbt is ?";

        try ( Connection conn = this.connect();  PreparedStatement db = conn.prepareStatement(sql)) {
            // set the corresponding param
            db.setString(1, fixedItem.ENCHANTMENTS);
            db.setString(2, fixedItem.MATERIAL);
            db.setString(3, fixedItem.DISPLAYNAME);
            db.setString(4, fixedItem.LORE);
            db.setString(5, fixedItem.NBT);

            UserData.itemsDB.remove(fixedItem.getItem());

            db.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
