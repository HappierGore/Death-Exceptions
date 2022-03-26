package sqlite;

import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTItem;
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
public class ItemDB extends SQLite {

    //*************************************
    //              Flags
    //*************************************
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
            db.close();
            rs.close();

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

    //*******************
    //      Helper
    //*******************
    private static void updateFlags(ItemStack item, List<ItemFlags> flags) {
        String flagString = flags.toString().equals("[]") ? null : flags.toString().replace("[", "").replace("]", "");
        String sql = "UPDATE " + TABLE + " SET flags = ? WHERE NBT is ?";

        try (Connection conn = connect(); PreparedStatement db = conn.prepareStatement(sql)) {

            // set the corresponding param
            db.setString(1, flagString);
            db.setString(2, new DEXItem(item).NBT);

            // update 
            db.executeUpdate();

            db.close();
        } catch (SQLException e) {
            System.out.println("Error from updateFlags: " + e.getMessage());
        }
    }

    //*************************************
    //              Items
    //*************************************
    private static final List<ItemStack> itemsDB;

    static {
        itemsDB = new ArrayList<>();
    }

    public static boolean addItem(ItemStack item) {

        DEXItem fixedItem = new DEXItem(item);

        if (itemsDB.contains(fixedItem.getItem())) {
            return false;
        }

        String sql = "INSERT INTO " + TABLE + "(NBT) VALUES(?)";

        try (Connection conn = connect(); PreparedStatement db = conn.prepareStatement(sql)) {
            db.setString(1, fixedItem.NBT);

            db.executeUpdate();
            ItemDB.itemsDB.add(fixedItem.getItem());
            db.close();
        } catch (SQLException e) {
            System.out.println("Error from addItem: " + e.getMessage());
        }
        
        return true;
    }

    public static boolean removeItem(ItemStack item) {

        DEXItem fixedItem = new DEXItem(item);

        String sql = "DELETE FROM " + TABLE + " WHERE NBT is ?";

        try (Connection conn = connect(); PreparedStatement db = conn.prepareStatement(sql)) {

            db.setString(1, fixedItem.NBT);

            itemsDB.remove(fixedItem.getItem());

            db.executeUpdate();
            
            db.close();

        } catch (SQLException e) {

            System.out.println("Error from remove: " + e.getMessage());
            return false;
        }
        return true;
    }

    public static List<ItemStack> getDBItems() {
        List<ItemStack> newList = new ArrayList<>();
        for (ItemStack item : itemsDB) {
            newList.add(item.clone());
        }
        return newList;
    }

    //*******************
    //      Helper
    //*******************
    public static void loadItems() {

        String sql = "SELECT NBT FROM " + TABLE;

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                NBTContainer nbtItem = new NBTContainer(rs.getString("NBT"));
                itemsDB.add(NBTItem.convertNBTtoItem(nbtItem));
            }
            
            pstmt.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error from loadItems: " + e.getMessage());
        }
    }

}
