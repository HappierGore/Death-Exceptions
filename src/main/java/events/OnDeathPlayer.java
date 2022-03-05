package events;

import de.tr7zw.nbtapi.NBTItem;
import helper.ItemUtils;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import user.UserData;

/**
 *
 * @author HappierGore
 */
public final class OnDeathPlayer {

    public static void registerKill(PlayerDeathEvent e) {
        Player player = e.getEntity();

        if (player.hasPermission("deathExceptions.keepInventory.all")) {
            UserData userData = UserData.getObj(player.getUniqueId().toString());
            userData.itemsToRespawn.clear();
            e.getDrops().clear();
            e.setKeepInventory(true);
            e.setKeepLevel(true);
        } else if (player.hasPermission("deathExceptions.keepInventory")) {
            List<ItemStack> items = e.getDrops();

            UserData userData = UserData.getObj(player.getUniqueId().toString());
            userData.itemsToRespawn.clear();

            items.forEach(item -> {
                NBTItem nbt = new NBTItem(item);
                if (nbt.hasKey("keepInventory")) {
                    userData.itemsToRespawn.add(item);
                    return;
                }

                //Add ignore NBT, Enchantments, name, lore option.
                //Add GUI menu
                //Prevent reset dropData if reload (Save another DB with userInfo), like PlayerVaults
                if (UserData.itemsDB.contains(ItemUtils.fixItem(item))) {
                    userData.itemsToRespawn.add(item);
                }

            });

            userData.itemsToRespawn.forEach(item -> {
                e.getDrops().remove(item);
            });
        }
    }
}
