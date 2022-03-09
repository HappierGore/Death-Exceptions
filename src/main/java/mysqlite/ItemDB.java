package mysqlite;

import gui.items.ItemFlags;
import helper.DEXItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    private void updateFlags(ItemStack item, List<ItemFlags> flags) {
        String flagString = flags.toString().equals("[]") ? null : flags.toString().replace("[", "").replace("]", "");
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

    public List<ItemFlags> getFlags(ItemStack item) {
        List<ItemFlags> flags = new ArrayList<>();

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
                for (String flag : flagDB.split(",")) {
                    flags.add(ItemFlags.valueOf(flag.trim()));
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return flags;
    }

    public void addFlag(ItemStack item, ItemFlags flag) {
        List<ItemFlags> flags = this.getFlags(item);

        if (!flags.contains(flag)) {
            flags.add(flag);
            this.updateFlags(item, flags);
        }
    }

    public void removeFlag(ItemStack item, ItemFlags flag) {
        List<ItemFlags> flags = this.getFlags(item);
        if (flags.contains(flag)) {
            flags.remove(flag);
            this.updateFlags(item, flags);
        }
    }
}
