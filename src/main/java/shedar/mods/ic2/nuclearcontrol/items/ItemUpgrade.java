package shedar.mods.ic2.nuclearcontrol.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import net.minecraft.util.ResourceLocation;
import shedar.mods.ic2.nuclearcontrol.IC2NuclearControl;
import shedar.mods.ic2.nuclearcontrol.Refstrings;

public class ItemUpgrade extends Item {

    public static final int DAMAGE_RANGE = 0;
    public static final int DAMAGE_COLOR = 1;
    public static final int DAMAGE_WEB = 2;

    private static final ResourceLocation TEXTURE_RANGE = new ResourceLocation(Refstrings.ASSETS_FOLDER,"upgradeRange");
    private static final ResourceLocation TEXTURE_COLOR = new ResourceLocation(Refstrings.ASSETS_FOLDER,"upgradeColor");
    private static final ResourceLocation TEXTURE_WEB = new ResourceLocation(Refstrings.ASSETS_FOLDER,"upgradeWeb");

    private IIcon iconRange;
    private IIcon iconColor;
    private IIcon iconWeb;

    public ItemUpgrade() {
        super();
        setMaxDamage(0);
        setHasSubtypes(true);
        setCreativeTab(IC2NuclearControl.tabIC2NC);
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        iconRange = iconRegister.registerIcon(TEXTURE_RANGE.toString());
        iconColor = iconRegister.registerIcon(TEXTURE_COLOR.toString());
        iconWeb = iconRegister.registerIcon(TEXTURE_WEB.toString());
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        int damage = itemStack.getItemDamage();
        return switch (damage) {
            case DAMAGE_RANGE -> "item.itemRangeUpgrade";
            case DAMAGE_COLOR -> "item.ItemColorUpgrade";
            case DAMAGE_WEB -> "item.ItemWebUpgrade";
            default -> "";
        };
    }

    @Override
    public IIcon getIconFromDamage(int damage) {
        switch (damage) {
            case DAMAGE_COLOR:
                return iconColor;
            case DAMAGE_WEB:
                return iconWeb;
            default:
                return iconRange;
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List itemList) {
        itemList.add(new ItemStack(par1, 1, DAMAGE_RANGE));
        itemList.add(new ItemStack(par1, 1, DAMAGE_COLOR));
        itemList.add(new ItemStack(par1, 1, DAMAGE_WEB));
    }
}
