package shedar.mods.ic2.nuclearcontrol.crossmod.appeng;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.registry.GameRegistry;
import shedar.mods.ic2.nuclearcontrol.IC2NuclearControl;
import shedar.mods.ic2.nuclearcontrol.config.Configuration;
import shedar.mods.ic2.nuclearcontrol.config.RecipeType;
import shedar.mods.ic2.nuclearcontrol.crossmod.ModLib;

public class CrossAppeng {

    public static Block networklink;
    public static Item kitAppeng;
    public static Item cardAppeng;

    public static void registrationCheck() {
        if (Loader.isModLoaded(ModLib.AE2)) {
            IC2NuclearControl.logger.info("Large Storage System? We can help to monitor that!");
            addBlocksItemsTiles();
        }
    }

    @Optional.Method(modid = ModLib.AE2)
    private static void addBlocksItemsTiles() {
        networklink = new BlockNetworkLink();
        kitAppeng = new ItemKitAppeng();
        cardAppeng = new ItemCardAppeng();
        GameRegistry.registerBlock(networklink, "networkLink");
        GameRegistry.registerItem(kitAppeng, "KitAppeng");
        GameRegistry.registerItem(cardAppeng, "CardAppeng");
        GameRegistry.registerTileEntity(TileEntityNetworkLink.class, "networkLink");

        if (Configuration.recipes == RecipeType.normal) {
            AppengRecipes.addRecipesToRegistry();
            return;
        }

        if (Configuration.recipes == RecipeType.gregtech) {
            AppengRecipes.addGregtechRecipes();
        }
    }
}
