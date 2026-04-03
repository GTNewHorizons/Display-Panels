package shedar.mods.ic2.nuclearcontrol.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemStackUtils {

    /**
     * Retrieve the NBT tag of an ItemStack and create one if there was none. DO NOT USE that method if you just
     * want to read the NBT of an ItemStack, otherwise you'll just create empty tags without noticing.
     * @param itemStack
     * @return the NBT tag if it exists, otherwise create one, apply it to the ItemStack, then return it.
     */
    public static NBTTagCompound getTagCompound(ItemStack itemStack) {
        NBTTagCompound nbtTagCompound = itemStack.getTagCompound();
        if (nbtTagCompound == null) {
            nbtTagCompound = new NBTTagCompound();
            itemStack.setTagCompound(nbtTagCompound);
        }
        return nbtTagCompound;
    }
}
