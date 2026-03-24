package shedar.mods.ic2.nuclearcontrol.blocks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import shedar.mods.ic2.nuclearcontrol.IC2NuclearControl;
import shedar.mods.ic2.nuclearcontrol.Refstrings;

public class BlockNuclearControlLight extends Block {

    public static Map<Integer, Boolean> subblocks;
    private final IIcon[] icon;

    public BlockNuclearControlLight() {
        super(Material.redstoneLight);
        subblocks = new HashMap<>();
        this.setHardness(0.3F);
        this.setCreativeTab(IC2NuclearControl.tabIC2NC);
        setStepSound(soundTypeGlass);
        icon = new IIcon[LightDamages.values.length];
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta % 2 == 1) return 15;
        return 0;
    }

    @Override
    public int damageDropped(int i) {
        return i%2 == 0 ? i : i-1;
    }

    @Override
    public IIcon getIcon(int side, int metadata) {
        return icon[metadata];
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        for (LightDamages block: LightDamages.values) {
            icon[block.id] = register.registerIcon(block.texture.toString());
        }
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);
        setBlockWithCorrectState(world, x, y, z);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbor) {
        setBlockWithCorrectState(world, x, y, z);
    }

    public void setBlockWithCorrectState(World world, int x, int y, int z){
        if (world.isRemote) {
            return;
        }

        int meta = world.getBlockMetadata(x, y, z);
        boolean isLit = meta % 2 == 1;

        if (isLit && !world.isBlockIndirectlyGettingPowered(x, y, z)) {
            world.setBlock(x, y, z, this, meta - 1, 2);

        } else if (!isLit && world.isBlockIndirectlyGettingPowered(x, y, z)) {
            world.setBlock(x, y, z, this, meta + 1, 2);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void getSubBlocks(Item id, CreativeTabs tab, List itemList) {
        for (LightDamages light : LightDamages.values) {
            itemList.add(new ItemStack(this, 1, light.id));
        }
    }

    public enum LightDamages{
        DAMAGE_WHITE_OFF(0),
        DAMAGE_WHITE_ON(1),
        DAMAGE_ORANGE_OFF(2),
        DAMAGE_ORANGE_ON(3);

        public final int id;
        public final ResourceLocation texture;

        public static final LightDamages[] values = LightDamages.values();

        LightDamages(int id){
            this.id = id;
            this.texture = new ResourceLocation(Refstrings.ASSETS_FOLDER,"light/lamp"+id);
        }
    }



}
