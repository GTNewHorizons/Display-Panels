package shedar.mods.ic2.nuclearcontrol.crossmod.RF;

import cpw.mods.fml.common.Optional;
import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import shedar.mods.ic2.nuclearcontrol.crossmod.EnergyStorageData;
import shedar.mods.ic2.nuclearcontrol.crossmod.ModLib;
import shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityAverageCounter;
import shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityEnergyCounter;

public class CrossRF {

    public boolean _RFModPresent = false;

    public CrossRF() {
        if (Loader.isModLoaded(ModLib.COFHCore)) {
            _RFModPresent = true;
            registerTiles();
        }
    }

    public void registerTiles() {
        GameRegistry.registerTileEntity(RFTileEntityAverageCounter.class, "IC2NCAverageCounterRF");
        GameRegistry.registerTileEntity(RFTileEntityEnergyCounter.class, "IC2NCEnergyCounterRF");
    }

    public TileEntityAverageCounter getAverageCounter() {
        if (_RFModPresent) {
            return getRFTileENtityAverageCounter();
        }
        return null;
    }

    public TileEntityEnergyCounter getEnergyCounter() {
        if (_RFModPresent) {
            return getTileEntityEnergyCounter();
        }
        return null;
    }

    @Optional.Method(modid = ModLib.COFHCore)
    private static RFTileEntityAverageCounter getRFTileENtityAverageCounter(){
        return new RFTileEntityAverageCounter();
    }

    @Optional.Method(modid = ModLib.COFHCore)
    private static TileEntityEnergyCounter getTileEntityEnergyCounter (){
        return new TileEntityEnergyCounter();
    }

    public EnergyStorageData getStorageData(TileEntity target) {
        if (!_RFModPresent || target == null) return null;
        RFTileEntityAverageCounter tile = new RFTileEntityAverageCounter();
        EnergyStorageData result = new EnergyStorageData();
        result.capacity = tile.storage.getMaxEnergyStored();
        result.stored = tile.storage.getEnergyStored();
        result.units = EnergyStorageData.UNITS_RF;
        result.type = EnergyStorageData.TARGET_TYPE_RF;
        return result;
    }
}
