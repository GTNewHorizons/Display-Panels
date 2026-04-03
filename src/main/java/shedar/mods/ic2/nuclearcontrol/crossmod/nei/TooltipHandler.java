package shedar.mods.ic2.nuclearcontrol.crossmod.nei;

import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import codechicken.nei.guihook.IContainerTooltipHandler;

public class TooltipHandler implements IContainerTooltipHandler {

    @Override
    public List<String> handleItemDisplayName(GuiContainer gui, ItemStack itemstack, List<String> arg2) {
        if (itemstack != null && itemstack.hasTagCompound()) {
            NBTTagCompound tags = itemstack.getTagCompound();
            if (tags.hasKey("_webSensorId")) {
                long id = tags.getLong("_webSensorId");
                if (id > 0) arg2.add("Web Id: " + id);
            }
        }
        return arg2;
    }
}
