package shedar.mods.ic2.nuclearcontrol.crossmod;

import cpw.mods.fml.common.event.FMLInterModComms;
import shedar.mods.ic2.nuclearcontrol.crossmod.RF.CrossTE;
import shedar.mods.ic2.nuclearcontrol.crossmod.appeng.CrossAppeng;
import shedar.mods.ic2.nuclearcontrol.crossmod.vanilla.Vanilla;
import shedar.mods.ic2.nuclearcontrol.crossmod.waila.CrossWaila;

public class CrossModLoader {

    public static void init() {
        // Registers waila stuff
        FMLInterModComms.sendMessage(
                "Waila",
                "register",
                CrossWaila.class.getName()+".callbackRegister");
        CrossAppeng.registrationCheck();
        Vanilla.initVanilla();
    }

    public static void postinit() {
        CrossTE.intergrateTE();
    }
}
