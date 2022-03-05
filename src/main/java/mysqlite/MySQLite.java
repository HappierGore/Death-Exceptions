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

        StringBuilder ench = new StringBuilder();

        item.getEnchantments().entrySet().forEach((t) -> {
            ench.append(t.getKey().getKey().getKey()).append(":").append(t.getValue()).append(";");
        });

        String ENCHANTMENTS = ench.toString().trim().equals("") ? null : ench.toString();

        String MATERIAL = item.getType().toString();
        String DISPLAYNAME = !item.getItemMeta().getDisplayName().equals("") ? item.getItemMeta().getDisplayName() : null;
        String LORE = item.getItemMeta().getLore() != null ? String.join(";", item.getItemMeta().getLore()) : null;

        String NBT = ItemUtils.FilterNBT(item, null);

        String sql = "INSERT INTO " + table + "(material, displayname, lore, nbt, enchantments) VALUES(?, ?, ?, ?, ?)";

        try ( Connection conn = connect();  PreparedStatement db = conn.prepareStatement(sql)) {

            // set the corresponding param
            db.setString(1, MATERIAL);
            db.setString(2, DISPLAYNAME);
            db.setString(3, LORE);
            db.setString(4, NBT);
            db.setString(5, ENCHANTMENTS);

            // update 
            db.executeUpdate();
            UserData.itemsDB.add(ItemUtils.createItem(MATERIAL, DISPLAYNAME, LORE, NBT, ENCHANTMENTS));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void remove(ItemStack item) {

        StringBuilder ench = new StringBuilder();

        item.getEnchantments().entrySet().forEach((t) -> {
            ench.append(t.getKey().getKey().getKey()).append(":").append(t.getValue()).append(";");
        });

        String ENCHANTMENTS = ench.toString().trim().equals("") ? null : ench.toString();

        String MATERIAL = item.getType().toString();
        String DISPLAYNAME = !item.getItemMeta().getDisplayName().equals("") ? item.getItemMeta().getDisplayName() : null;
        String LORE = item.getItemMeta().getLore() != null ? String.join(";", item.getItemMeta().getLore()) : null;

        NBTItem nbtItem = new NBTItem(item);
        String NBT = ItemUtils.FilterNBT(item, null);

        String sql = "DELETE FROM " + table + " WHERE enchantments is ? AND material is ? AND displayname is ? AND lore is ? AND nbt is ?";

        try ( Connection conn = this.connect();  PreparedStatement db = conn.prepareStatement(sql)) {
            // set the corresponding param
            db.setString(1, ENCHANTMENTS);
            db.setString(2, MATERIAL);
            db.setString(3, DISPLAYNAME);
            db.setString(4, LORE);
            db.setString(5, NBT);

            UserData.itemsDB.remove(item);

            db.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
