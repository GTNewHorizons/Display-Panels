package shedar.mods.ic2.nuclearcontrol.blocks.subblocks;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;

import net.minecraft.util.ResourceLocation;
import shedar.mods.ic2.nuclearcontrol.Refstrings;
import shedar.mods.ic2.nuclearcontrol.containers.ContainerEmpty;
import shedar.mods.ic2.nuclearcontrol.gui.GuiIndustrialAlarm;
import shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityHowlerAlarm;
import shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityIndustrialAlarm;
import shedar.mods.ic2.nuclearcontrol.utils.BlockDamages;

public class IndustrialAlarm extends Subblock {

    private static final int DAMAGE = BlockDamages.DAMAGE_INDUSTRIAL_ALARM;
    private static final float[] BOUNDS = { 0.125F, 0, 0.125F, 0.875F, 0.4375F, 0.875F };

    private final IIcon[] icons = new IIcon[IndustrialAlarmSides.values.length];

    private static final byte[][] mapping = {
            { IndustrialAlarmSides.BACK.id, IndustrialAlarmSides.FACE_DARK.id, IndustrialAlarmSides.SIDES_HOR_DARK.id, IndustrialAlarmSides.SIDES_HOR_DARK.id, IndustrialAlarmSides.SIDES_HOR_DARK.id, IndustrialAlarmSides.SIDES_HOR_DARK.id },
            { IndustrialAlarmSides.FACE_DARK.id, IndustrialAlarmSides.BACK.id, IndustrialAlarmSides.SIDES_HOR_DARK.id, IndustrialAlarmSides.SIDES_HOR_DARK.id, IndustrialAlarmSides.SIDES_HOR_DARK.id, IndustrialAlarmSides.SIDES_HOR_DARK.id },
            { IndustrialAlarmSides.SIDES_HOR_DARK.id, IndustrialAlarmSides.SIDES_HOR_DARK.id, IndustrialAlarmSides.BACK.id, IndustrialAlarmSides.FACE_DARK.id, IndustrialAlarmSides.SIDES_VERT_DARK.id, IndustrialAlarmSides.SIDES_VERT_DARK.id },
            { IndustrialAlarmSides.SIDES_HOR_DARK.id, IndustrialAlarmSides.SIDES_HOR_DARK.id, IndustrialAlarmSides.FACE_DARK.id, IndustrialAlarmSides.BACK.id, IndustrialAlarmSides.SIDES_VERT_DARK.id, IndustrialAlarmSides.SIDES_VERT_DARK.id },
            { IndustrialAlarmSides.SIDES_VERT_DARK.id, IndustrialAlarmSides.SIDES_VERT_DARK.id, IndustrialAlarmSides.SIDES_VERT_DARK.id, IndustrialAlarmSides.SIDES_VERT_DARK.id, IndustrialAlarmSides.BACK.id, IndustrialAlarmSides.FACE_DARK.id },
            { IndustrialAlarmSides.SIDES_VERT_DARK.id, IndustrialAlarmSides.SIDES_VERT_DARK.id, IndustrialAlarmSides.SIDES_VERT_DARK.id, IndustrialAlarmSides.SIDES_VERT_DARK.id, IndustrialAlarmSides.FACE_DARK.id, IndustrialAlarmSides.BACK.id } };

    public IndustrialAlarm() {
        super(DAMAGE, "tile.blockIndustrialAlarm");
    }

    @Override
    public TileEntity getTileEntity() {
        return new TileEntityIndustrialAlarm();
    }

    @Override
    public boolean isSolidBlockRequired() {
        return true;
    }

    @Override
    public boolean hasGui() {
        return true;
    }

    @Override
    public float[] getBlockBounds(TileEntity tileEntity) {
        return BOUNDS;
    }

    @Override
    public Container getServerGuiElement(TileEntity tileEntity, EntityPlayer player) {
        return new ContainerEmpty(tileEntity);
    }

    @Override
    public Object getClientGuiElement(TileEntity tileEntity, EntityPlayer player) {
        return new GuiIndustrialAlarm((TileEntityHowlerAlarm) tileEntity);
    }

    @Override
    public IIcon getIcon(int index) {
        return icons[index];
    }

    @Override
    protected byte[][] getMapping() {
        return mapping;
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        for (IndustrialAlarmSides side: IndustrialAlarmSides.values){
            icons[side.id] = iconRegister.registerIcon(side.texture.toString());
        }
    }

    public enum IndustrialAlarmSides{
        BACK(0, "industrialAlarm/back"),
        SIDES_HOR_DARK(1, "industrialAlarm/sidesHor0"),
        SIDES_HOR_MID(2, "industrialAlarm/sidesHor1"),
        SIDES_HOR_BRIGHT(3, "industrialAlarm/sidesHor2"),
        SIDES_VERT_DARK(4, "industrialAlarm/sidesVert0"),
        SIDES_VERT_MID(5, "industrialAlarm/sidesVert1"),
        SIDES_VERT_BRIGHT(6, "industrialAlarm/sidesVert2"),
        FACE_DARK(7, "industrialAlarm/face0"),
        FACE_MID(8, "industrialAlarm/face1"),
        FACE_BRIGHT(9, "industrialAlarm/face2");

        public static final IndustrialAlarmSides[] values = IndustrialAlarmSides.values();

        public final byte id;
        public final ResourceLocation texture;

        IndustrialAlarmSides(int id, String ressourcePath){
            this.id = (byte) id;
            this.texture = new ResourceLocation(Refstrings.ASSETS_FOLDER, ressourcePath);
        }
    }
}
