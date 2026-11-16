/**
 * 
 * @author Zuxelus (I copied him)
 * 
 */
package shedar.mods.ic2.nuclearcontrol;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.world.WorldEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import shedar.mods.ic2.nuclearcontrol.config.Configuration;
import shedar.mods.ic2.nuclearcontrol.network.ChannelHandler;
import shedar.mods.ic2.nuclearcontrol.network.message.PacketAlarm;

public class ServerTickHandler {

    public final static ServerTickHandler instance = new ServerTickHandler();

    // Function onWorldUnload from CommonProxy.java
    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {
        IC2NuclearControl.instance.screenManager.clearWorld(event.world);
    }

    // Function playerLoggedIn from ConnectionHandler.java
    @SubscribeEvent
    public void onPlayerLogin(PlayerLoggedInEvent event) {
        ChannelHandler.network.sendTo(
                new PacketAlarm(Configuration.maxAlarmRange, Configuration.allowedAlarms),
                (EntityPlayerMP) event.player);
    }
}
