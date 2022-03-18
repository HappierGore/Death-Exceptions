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

/**
 *
 * @author HappierGore
 */
public class ItemDB extends MySQLite {

    public static void loadAllData() {
        itemsDB.clear();
        itemsDB.addAll(getItems());
    }

    private static void updateFlags(ItemStack item, List<ItemFlags> flags) {
        String flagString = flags.toString().equals("[]") ? null : flags.toString().replace("[", "").replace("]", "");
        String sql = "UPDATE " + TABLE + " SET flags = ? WHERE NBT is ?";

        try (Connection conn = connect(); PreparedStatement db = conn.prepareStatement(sql)) {

            // set the corresponding param
            db.setString(1, flagString);
            db.setString(2, new DEXItem(item).NBT);

            // update 
            db.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error from updateFlags: " + e.getMessage());
        }
    }

    public static List<ItemFlags> getFlags(ItemStack item) {
        List<ItemFlags> flags = new ArrayList<>();

        String sql = "SELECT flags FROM " + TABLE + " WHERE NBT is ?";

        try (Connection conn = connect(); PreparedStatement db = conn.prepareStatement(sql)) {
            db.setString(1, new DEXItem(item).NBT);

            // set the corresponding param
            ResultSet rs = db.executeQuery();

            while (rs.next()) {
                String flagDB = rs.getString("flags");

                if (flagDB != null) {
                    for (String flag : flagDB.split(",")) {
                        flags.add(ItemFlags.valueOf(flag.trim()));
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error from getFlags: " + e);
        }
        return flags;
    }

    public static void addFlag(ItemStack item, ItemFlags flag) {
        List<ItemFlags> flags = getFlags(item);

        if (!flags.contains(flag)) {
            flags.add(flag);
            updateFlags(item, flags);
        }
    }

    public static void removeFlag(ItemStack item, ItemFlags flag) {
        List<ItemFlags> flags = getFlags(item);
        if (flags.contains(flag)) {
            flags.remove(flag);
            updateFlags(item, flags);
        }
    }
}
