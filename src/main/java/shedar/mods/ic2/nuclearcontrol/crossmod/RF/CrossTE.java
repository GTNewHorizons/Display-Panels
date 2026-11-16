package shedar.mods.ic2.nuclearcontrol.crossmod.RF;

import net.minecraft.item.Item;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import shedar.mods.ic2.nuclearcontrol.IC2NuclearControl;
import shedar.mods.ic2.nuclearcontrol.crossmod.ModLib;

public class CrossTE {

    public static Item RFSensorCard;

    public static void intergrateTE() {
        if (Loader.isModLoaded(ModLib.TE) || Loader.isModLoaded(ModLib.ENDER_IO)) {
            registerThermalExpansion();
            IC2NuclearControl.logger.info("Another Energy System is Loaded. Adding Intergration!");
        }
    }

    private static void registerThermalExpansion() {
        RFSensorCard = new ItemCardRFEnergyLocation();

        GameRegistry.registerItem(RFSensorCard, "RFSensorCard");
    }
}
