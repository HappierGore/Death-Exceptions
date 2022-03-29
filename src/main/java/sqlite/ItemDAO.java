package sqlite;

import gui.items.ItemFlags;
import gui.modules.Modules;
import helper.DEXItem;
import helper.ItemUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappierGore
 */
public class ItemDAO extends SQLite {

    public static DEXItem getItemFromDB(ItemStack item) {
        DEXItem result = null;

        for (DEXItem item2 : ITEMS) {
            if (ItemUtils.compareItems(item, item2.getItem())) {
                result = item2;
            }
        }
        return result;
    }

    private static final Set<DEXItem> ITEMS = new HashSet<>();

    public static Set<DEXItem> getItemDB() {
        Set<DEXItem> clone = new HashSet<>();
        ITEMS.forEach(item -> {
            clone.add(item.getClone());
        });
        return clone;

    }

    public static boolean addItem(ItemStack item) {

        DEXItem fixedItem = new DEXItem(item);

        if (checkSaved(fixedItem)) {
            return false;
        }

        String sql = "INSERT INTO " + TABLE + "(NBT) VALUES(?)";

        try ( Connection conn = connect();  PreparedStatement db = conn.prepareStatement(sql)) {
            db.setString(1, fixedItem.getNBT());

            db.executeUpdate();
            ItemDAO.ITEMS.add(fixedItem);
            db.close();
        } catch (SQLException e) {
            System.out.println("Error from addItem: ");
            e.printStackTrace(System.out);
            return false;
        }

        return true;
    }

    public static boolean removeItem(ItemStack item) {

        DEXItem fixedItem = new DEXItem(item);

        String sql = "DELETE FROM " + TABLE + " WHERE NBT is ?";

        try ( Connection conn = connect();  PreparedStatement db = conn.prepareStatement(sql)) {

            db.setString(1, fixedItem.getNBT());

            if (!checkSaved(fixedItem)) {
                return false;
            }

            db.executeUpdate();

            db.close();

        } catch (SQLException e) {
            System.out.println("Error from remove: ");
            e.printStackTrace(System.out);
            return false;
        }
        return true;
    }

    public static void updateItemInfo(DEXItem newItem) {

        String flagString = newItem.getFlags().isEmpty() ? null : newItem.getFlags().toString().replace("[", "").replace("]", "");

        String moduleString = newItem.getModules().isEmpty() ? null : newItem.getModules().toString().replace("{", "").replace("}", "").replace("=", ":");

        String sql = "UPDATE " + TABLE + " SET flags = ?, modules = ? WHERE NBT is ?";

        try ( Connection conn = connect();  PreparedStatement db = conn.prepareStatement(sql)) {

            // set the corresponding param
            db.setString(1, flagString);
            db.setString(2, moduleString);
            db.setString(3, newItem.getNBT());

            // update 
            db.executeUpdate();

            db.close();
        } catch (SQLException e) {
            System.out.println("Error from update: ");
            e.printStackTrace(System.out);
        }

    }

    //*******************
    //      Helper
    //*******************
    public static void loadItems() {

        String sql = "SELECT * FROM " + TABLE;

        try ( Connection conn = connect();  PreparedStatement pstmt = conn.prepareStatement(sql); // set the corresponding param
                  ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {

                DEXItem result = new DEXItem(rs.getString("NBT"));

                if (rs.getString("flags") != null) {
                    for (String entry : rs.getString("flags").split(",")) {
                        result.getFlags().add(ItemFlags.valueOf(entry.trim()));
                    }
                }
                if (rs.getString("modules") != null) {
                    for (String entry : rs.getString("modules").split(", ")) {
                        Modules module = Modules.valueOf(entry.trim().split(":")[0]);
                        String value = entry.trim().split(":")[1];
                        result.getModules().put(module, value);
                    }
                }

                ITEMS.add(result);
            }

            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error from loadItems: ");
            e.printStackTrace(System.out);

        }
    }

    public static boolean checkSaved(DEXItem item) {
        for (DEXItem itemDB : ITEMS) {
            if (itemDB.getNBT().equals(item.getNBT())) {
                return true;
            }
        }
        return false;
    }

}
