package mysqlite;

import helper.ItemUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

        UserData.itemsDB.clear();

        String sql = "SELECT * FROM " + this.table;

        try ( Connection conn = this.connect();  PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String material = rs.getString("material");
                String displayName = rs.getString("displayname");
                String lore = rs.getString("lore");
                String nbt = rs.getString("nbt");
                String enchantments = rs.getString("enchantments");

//                System.out.println("INFORMATION FROM DATABASE:");
//                System.out.println("MATERIAL: " + material);
//                System.out.println("DISPLAYNAME: " + displayName);
//                System.out.println("LORE: " + lore);
//                System.out.println("NBT: " + nbt);
//                System.out.println("ENCHANTMENTS: " + enchantments);

                UserData.itemsDB.add(ItemUtils.fixItem(ItemUtils.createItem(material, displayName, lore, nbt, enchantments)));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
