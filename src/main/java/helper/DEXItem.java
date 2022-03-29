package helper;

import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTItem;
import gui.items.ItemFlags;
import gui.modules.Modules;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author HappierGore
 */
public final class DEXItem {

    private static final List<String> DEFAULT_EXCEPTIONS = new ArrayList<>();

    static {
        DEFAULT_EXCEPTIONS.add("UUID");
    }

    private ItemStack item;
    private final Set<ItemFlags> flags = new HashSet<>();
    private final Map<Modules, String> modules = new HashMap<>();
    private String NBT;

    //Used when created from game
    public DEXItem(ItemStack item) {
        fixAmount(item);
        NBTContainer gottenNBT = NBTItem.convertItemtoNBT(item);
        ItemUtils.filterNBT(gottenNBT, DEFAULT_EXCEPTIONS);
        this.NBT = gottenNBT.toString();
        this.item = item;
    }

    //Used when load from database
    public DEXItem(String NBT) {
        this.NBT = NBT;
        NBTContainer nbtContainer = new NBTContainer(NBT);
        this.item = NBTItem.convertNBTtoItem(nbtContainer);
    }

    private void fixAmount(ItemStack item) {
        if (item.getAmount() > 1) {
            item.setAmount(1);
        }
    }

    //***************************
    //     Getters & Setters
    //***************************
    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public Set<ItemFlags> getFlags() {
        return flags;
    }

    public Map<Modules, String> getModules() {
        return modules;
    }

    public String getNBT() {
        return NBT;
    }

    public void setNBT(String NBT) {
        this.NBT = NBT;
    }

    public DEXItem getClone() {
        return new DEXItem(this.NBT);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DEXItem{");
        sb.append("item=").append(item);
        sb.append(", flags=").append(flags);
        sb.append(", modules=").append(modules);
        sb.append(", NBT=").append(NBT);
        sb.append(", memory =").append(super.toString());
        sb.append('}');
        return sb.toString();
    }

}
