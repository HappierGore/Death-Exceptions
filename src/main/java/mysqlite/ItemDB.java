package mysqlite;

import de.tr7zw.nbtapi.NBTItem;
import helper.ItemUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import user.UserData;

/**
 *
 * @author HappierGore
 */
public class ItemDB extends MySQLite {

    public ItemDB() {
        super("items");
    }

    public void loadAllData() {

        String sql = "SELECT * FROM " + this.table;

        try ( Connection conn = this.connect();  PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String material = rs.getString("material");
                String displayName = rs.getString("displayname");
                String lore = rs.getString("lore");
                String nbt = rs.getString("nbt");
               
                UserData.itemsDB.add(ItemUtils.createItem(material, displayName, lore, nbt));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
