package mysqlite;

import de.tr7zw.nbtapi.NBTItem;
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

        String MATERIAL = item.getType().toString();
        String DISPLAYNAME = !item.getItemMeta().getDisplayName().equals("") ? item.getItemMeta().getDisplayName() : null;
        String LORE = item.getItemMeta().getLore() != null ? String.join(";", item.getItemMeta().getLore()) : null;

        NBTItem nbtItem = new NBTItem(item);
        String NBT = !nbtItem.toString().equals("{}") ? nbtItem.toString() : null;

        String sql = "INSERT INTO " + table + "(material, displayname, lore, nbt) VALUES(?, ?, ?, ?)";

        try ( Connection conn = connect();  PreparedStatement db = conn.prepareStatement(sql)) {

            // set the corresponding param
            db.setString(1, MATERIAL);
            db.setString(2, DISPLAYNAME);
            db.setString(3, LORE);
            db.setString(4, NBT);

            // update 
            db.executeUpdate();
            UserData.itemsDB.add(ItemUtils.createItem(MATERIAL, DISPLAYNAME, LORE, NBT));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void remove(String MATERIAL, String DISPLAYNAME, String LORE, String NBT) {

        String sql = "DELETE FROM " + table + " WHERE material = ?, display = ?, lore = ?, nbt = ?";

        System.out.println(sql);

        try ( Connection conn = this.connect();  PreparedStatement db = conn.prepareStatement(sql)) {
            // set the corresponding param
            db.setString(1, MATERIAL);
            db.setString(2, DISPLAYNAME);
            db.setString(3, LORE);
            db.setString(4, NBT);
            db.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
