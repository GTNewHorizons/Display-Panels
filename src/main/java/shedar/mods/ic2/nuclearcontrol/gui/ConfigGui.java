package shedar.mods.ic2.nuclearcontrol.gui;

import com.gtnewhorizon.gtnhlib.config.ConfigException;
import com.gtnewhorizon.gtnhlib.config.SimpleGuiConfig;
import net.minecraft.client.gui.GuiScreen;

import shedar.mods.ic2.nuclearcontrol.Refstrings;
import shedar.mods.ic2.nuclearcontrol.config.Configuration;

public class ConfigGui extends SimpleGuiConfig {

    public ConfigGui(GuiScreen parentScreen) throws ConfigException {
        super(
                parentScreen,
                Refstrings.MOD_ID,
                Refstrings.MOD_NAME,
                true,
                Configuration.class);
    }
}
