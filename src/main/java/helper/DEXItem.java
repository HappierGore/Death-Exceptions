package helper;

import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTItem;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappierGore
 */
public final class DEXItem {

    public final String NBT;
    private final ItemStack finalItem;

    //Used when created from game
    public DEXItem(ItemStack item) {
        NBTContainer gottenNBT = NBTItem.convertItemtoNBT(item);
        this.filterNBT(gottenNBT);
        this.NBT = gottenNBT.toString();
        this.finalItem = item;
    }

    //Used when load from database
    public DEXItem(String NBT) {
        this.NBT = NBT;
        NBTContainer nbtContainer = new NBTContainer(NBT);
        this.finalItem = NBTItem.convertNBTtoItem(nbtContainer);
    }

    //***************************
    //          Helpers
    //***************************
    private void filterNBT(NBTContainer nbtItem) {
        List<String> NBTExceptions = new ArrayList<>();

        NBTExceptions.add("Enchantments");
        NBTExceptions.add("ench");
        NBTExceptions.add("display");
        NBTExceptions.add("UUID");

        nbtItem.getKeys().forEach(key -> {
            System.out.println("KEY: " + key);
            if (NBTExceptions.contains(key)) {
                nbtItem.removeKey(key);
            }
        });
    }

    //***************************
    //          Getters
    //***************************
    public ItemStack getItem() {
        return finalItem;
    }

}
