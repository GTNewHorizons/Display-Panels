package shedar.mods.ic2.nuclearcontrol.crossmod.waila;

import java.util.List;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityAdvancedInfoPanel;
import shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityInfoPanel;


/**
 * Provider for info panel. The color thing is very useful for those color blind.
 * 
 * @author xbony2
 *
 */
public class InfoPanelProvider implements IWailaDataProvider {

    @Override
    public List<String> getWailaBody(ItemStack itemstack, List<String> currenttip, IWailaDataAccessor accessor,
            IWailaConfigHandler handler) {
        if (accessor.getTileEntity() instanceof TileEntityInfoPanel) {
            if (((TileEntityInfoPanel) accessor.getTileEntity()).getIsWeb()) StatCollector.translateToLocal("msg.nc.waila.Web");
            if (((TileEntityInfoPanel) accessor.getTileEntity()).getColored()) {
                if (!(accessor.getTileEntity() instanceof TileEntityAdvancedInfoPanel)) {
                    currenttip.add(StatCollector.translateToLocal("msg.nc.waila.Color"));
                }
                currenttip.add(
                        StatCollector.translateToLocal("msg.nc.waila.ColorBackground")
                                + getColor(((TileEntityInfoPanel) accessor.getTileEntity()).getColorBackground()));
                currenttip.add(
                        StatCollector.translateToLocal("msg.nc.waila.ColorText")
                                + getColor(((TileEntityInfoPanel) accessor.getTileEntity()).getColorText()));
            }
        }
        return currenttip;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemstack, List<String> currenttip, IWailaDataAccessor accessor,
            IWailaConfigHandler handler) {
        return currenttip;
    }

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler handler) {
        return null;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemstack, List<String> currenttip, IWailaDataAccessor accessor,
            IWailaConfigHandler handler) {
        return currenttip;
    }

    public static String getColor(int i) {
        switch (i) {
            case 0:
                return StatCollector.translateToLocal("msg.nc.waila.ColorBlack");
            case 1:
                return StatCollector.translateToLocal("msg.nc.waila.ColorRed");
            case 2:
                return StatCollector.translateToLocal("msg.nc.waila.ColorGreen");
            case 3:
                return StatCollector.translateToLocal("msg.nc.waila.ColorBrown");
            case 4:
                return StatCollector.translateToLocal("msg.nc.waila.ColorDarkBlue");
            case 5:
                return StatCollector.translateToLocal("msg.nc.waila.ColorPurple");
            case 6:
                return StatCollector.translateToLocal("msg.nc.waila.ColorLightBlue");
            case 7:
                return StatCollector.translateToLocal("msg.nc.waila.ColorWhite");
            case 8:
                return StatCollector.translateToLocal("msg.nc.waila.ColorBlack");
            case 9:
                return StatCollector.translateToLocal("msg.nc.waila.ColorPink");
            case 10:
                return StatCollector.translateToLocal("msg.nc.waila.ColorLimeGreen");
            case 11:
                return StatCollector.translateToLocal("msg.nc.waila.ColorYellow");
            case 12:
                return StatCollector.translateToLocal("msg.nc.waila.ColorBlue");
            case 13:
                return StatCollector.translateToLocal("msg.nc.waila.ColorMagenta");
            case 14:
                return StatCollector.translateToLocal("msg.nc.waila.ColorOrange");
            default:
                return "COLOR ERROR REPORT PLZ THX";
        }
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity tileentity, NBTTagCompound tag, World world,
            int x, int y, int z) {
        return null;
    }
}
