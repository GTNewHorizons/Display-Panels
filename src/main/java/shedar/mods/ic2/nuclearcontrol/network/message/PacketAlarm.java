package shedar.mods.ic2.nuclearcontrol.network.message;

import java.util.ArrayList;
import java.util.Arrays;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import shedar.mods.ic2.nuclearcontrol.IC2NuclearControl;
import shedar.mods.ic2.nuclearcontrol.config.Configuration;

public class PacketAlarm implements IMessage, IMessageHandler<PacketAlarm, IMessage> {

    private int maxAlarmRange;
    private String allowedAlarms;

    public PacketAlarm() {}

    @Deprecated
    public PacketAlarm(int range, String alarms) {
        maxAlarmRange = range;
        allowedAlarms = alarms;
    }

    public PacketAlarm(int range, String[] alarms){
        maxAlarmRange = range;
        allowedAlarms = String.join(",", alarms);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        maxAlarmRange = buf.readInt();
        allowedAlarms = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(maxAlarmRange);
        ByteBufUtils.writeUTF8String(buf, allowedAlarms);
    }

    @Override
    public IMessage onMessage(PacketAlarm message, MessageContext ctx) {
        Configuration.maxAlarmRange = message.maxAlarmRange;
        IC2NuclearControl.instance.serverAllowedAlarms = new ArrayList<>(
                Arrays.asList(message.allowedAlarms.split(",")));
        return null;
    }
}
