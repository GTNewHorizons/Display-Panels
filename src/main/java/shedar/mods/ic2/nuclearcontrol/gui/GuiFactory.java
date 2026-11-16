package shedar.mods.ic2.nuclearcontrol.gui;

import com.gtnewhorizon.gtnhlib.config.SimpleGuiFactory;
import net.minecraft.client.gui.GuiScreen;

@SuppressWarnings("unused")
/**
 * Referenced through {@link shedar.mods.ic2.nuclearcontrol.Refstrings.GUI_FACTORY this field}.
 */
public class GuiFactory implements SimpleGuiFactory {

    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return ConfigGui.class;
    }
}

