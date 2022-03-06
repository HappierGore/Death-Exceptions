package mysqlite;

import helper.DEXItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.inventory.ItemStack;
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

                UserData.itemsDB.add(new DEXItem(enchantments, material, displayName, lore, nbt).getItem());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateFlags(ItemStack item, List<String> flags) {
        String flagString = String.join(";", flags).equals("") ? null : String.join(";", flags);
        DEXItem fixedItem = new DEXItem(item);
        String sql = "UPDATE " + this.table + " SET flags = ? WHERE enchantments is ? AND material is ? AND displayname is ? AND lore is ? AND nbt is ?";

        try ( Connection conn = connect();  PreparedStatement db = conn.prepareStatement(sql)) {

            // set the corresponding param
            db.setString(1, flagString);
            db.setString(2, fixedItem.ENCHANTMENTS);
            db.setString(3, fixedItem.MATERIAL);
            db.setString(4, fixedItem.DISPLAYNAME);
            db.setString(5, fixedItem.LORE);
            db.setString(6, fixedItem.NBT);

            // update 
            db.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<String> getFlags(ItemStack item) {
        List<String> flags = new ArrayList<>();

        DEXItem fixedItem = new DEXItem(item);
        String sql = "SELECT flags FROM " + this.table + " WHERE enchantments is ? AND material is ? AND displayname is ? AND lore is ? AND nbt is ?";

        try ( Connection conn = this.connect();  PreparedStatement db = conn.prepareStatement(sql)) {

            db.setString(1, fixedItem.ENCHANTMENTS);
            db.setString(2, fixedItem.MATERIAL);
            db.setString(3, fixedItem.DISPLAYNAME);
            db.setString(4, fixedItem.LORE);
            db.setString(5, fixedItem.NBT);

            // set the corresponding param
            ResultSet rs = db.executeQuery();

            String flagDB = rs.getString("flags");
            if (flagDB != null) {
                flags.addAll(Arrays.asList(flagDB.split(";")));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return flags;
    }
}
