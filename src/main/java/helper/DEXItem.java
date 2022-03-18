package helper;

import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTItem;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author HappierGore
 */
public final class DEXItem {

    private static final List<String> defaultExceptions;

    static {
        defaultExceptions = new ArrayList<>();
        defaultExceptions.add("UUID");
    }

    public final String NBT;
    private final ItemStack finalItem;

    //Used when created from game
    public DEXItem(ItemStack item) {
        NBTContainer gottenNBT = NBTItem.convertItemtoNBT(item);
        ItemUtils.filterNBT(gottenNBT, defaultExceptions);
        this.NBT = gottenNBT.toString();
        fixAmount(item);
        this.finalItem = item;
    }

    //Used when load from database
    public DEXItem(String NBT) {
        this.NBT = NBT;
        NBTContainer nbtContainer = new NBTContainer(NBT);
        this.finalItem = NBTItem.convertNBTtoItem(nbtContainer);
    }

    private void fixAmount(ItemStack item) {
        if (item.getAmount() > 1) {
            item.setAmount(1);
        }
    }

    //***************************
    //          Getters
    //***************************
    public ItemStack getItem() {
        return finalItem;
    }

}
