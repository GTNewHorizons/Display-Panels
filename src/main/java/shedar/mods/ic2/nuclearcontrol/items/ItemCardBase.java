package shedar.mods.ic2.nuclearcontrol.items;

import java.util.List;
import java.util.UUID;

import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import shedar.mods.ic2.nuclearcontrol.IC2NuclearControl;
import shedar.mods.ic2.nuclearcontrol.Refstrings;
import shedar.mods.ic2.nuclearcontrol.api.CardState;
import shedar.mods.ic2.nuclearcontrol.api.DisplaySettingHelper;
import shedar.mods.ic2.nuclearcontrol.api.ICardWrapper;
import shedar.mods.ic2.nuclearcontrol.api.IPanelDataSource;
import shedar.mods.ic2.nuclearcontrol.api.PanelSetting;
import shedar.mods.ic2.nuclearcontrol.api.PanelString;

public abstract class ItemCardBase extends Item implements IPanelDataSource {

    public final ResourceLocation texture;

    public ItemCardBase(String textureItemName) {
        super();
        setMaxStackSize(1);
        canRepair = false;
        this.setCreativeTab(IC2NuclearControl.tabIC2NC);
        texture = new ResourceLocation(Refstrings.ASSETS_FOLDER, textureItemName);
        setTextureName(texture.toString());
    }

    @Override
    public boolean isDamageable() {
        return true;
    }

    @Override
    abstract public CardState update(TileEntity panel, ICardWrapper card, int range);

    @Override
    abstract public CardState update(World world, ICardWrapper card, int range);

    @Override
    abstract public UUID getCardType();

    @Override
    abstract public List<PanelString> getStringData(DisplaySettingHelper displaySettings, ICardWrapper card,
            boolean showLabels);

    public List<PanelString> getStringData(int displaySettings, ICardWrapper card, boolean showLabels) {
        return getStringData(new DisplaySettingHelper(displaySettings), card, true);
    }

    @Override
    abstract public List<PanelSetting> getSettingsList();

}
