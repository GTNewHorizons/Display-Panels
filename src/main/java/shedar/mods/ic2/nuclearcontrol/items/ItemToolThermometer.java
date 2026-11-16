package shedar.mods.ic2.nuclearcontrol.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorChamber;
import shedar.mods.ic2.nuclearcontrol.IC2NuclearControl;
import shedar.mods.ic2.nuclearcontrol.Refstrings;
import shedar.mods.ic2.nuclearcontrol.utils.NuclearHelper;
import shedar.mods.ic2.nuclearcontrol.utils.NuclearNetworkHelper;

public class ItemToolThermometer extends Item {

    public final ResourceLocation texture;
    public ItemToolThermometer() {
        super();
        setMaxDamage(102);
        setMaxStackSize(1);
        setCreativeTab(IC2NuclearControl.tabIC2NC);
        texture = new ResourceLocation(Refstrings.ASSETS_FOLDER, getTextureName());
        setTextureName(texture.toString());

    }

    protected String getTextureName(){
        return "thermometer";
    }

    protected boolean canTakeDamage(ItemStack itemstack, int i) {
        return true;
    }

    @Override
    public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side,
            float hitX, float hitY, float hitZ) {
        boolean isServer = player instanceof EntityPlayerMP;
        if (!isServer) return false;
        if (!canTakeDamage(itemstack, 2)) {
            return false;
        }
        try {
            IReactor reactor = NuclearHelper.getReactorAt(world, x, y, z);
            if (reactor == null) {
                IReactorChamber chamber = NuclearHelper.getReactorChamberAt(world, x, y, z);
                if (chamber != null) {
                    reactor = chamber.getReactor();
                }
                if (reactor == null && chamber == null) {
                    reactor = ItemCard55Reactor.getReactor(world, x, y, z);
                }
            }
            if (reactor != null) {
                messagePlayer(player, reactor);
                damage(itemstack, 1, player);
                return true;
            }
        } catch (NullPointerException e) {
            IC2NuclearControl.logger.error(e);
        }
        return false;

    }

    protected void messagePlayer(EntityPlayer entityplayer, IReactor reactor) {
        int heat = reactor.getHeat();
        NuclearNetworkHelper.chatMessage(entityplayer, "Thermo:" + heat);
    }

    protected void damage(ItemStack itemstack, int i, EntityPlayer entityplayer) {
        itemstack.damageItem(10, entityplayer);
    }

}
