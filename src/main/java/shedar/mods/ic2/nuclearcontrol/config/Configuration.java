package shedar.mods.ic2.nuclearcontrol.config;

import com.gtnewhorizon.gtnhlib.config.Config;
import com.gtnewhorizon.gtnhlib.config.ConfigurationManager;
import shedar.mods.ic2.nuclearcontrol.Refstrings;

@Config(modid = Refstrings.MOD_ID)
@Config.LangKey("gui.config.category.general")
public class Configuration {
    @Config.DefaultInt(64)
    public static int alarmRange;

    @Config.DefaultInt(128)
    public static int maxAlarmRange;

    @Config.DefaultStringList({AlarmType.AlarmNames.defaultAlarmName, AlarmType.AlarmNames.scifiAlarmName, AlarmType.AlarmNames.factorioAlarmName})
    @Config.RequiresMcRestart
    public static String[] allowedAlarms;

    @Config.DefaultInt(1)
    public static int remoteThermalMonitorEnergyConsumption;

    @Config.DefaultInt(20)
    public static int infoPanelRefreshPeriod;

    @Config.Comment("Cannot be longer than infoPanelRefreshPeriod")
    @Config.DefaultInt(4)
    public static int sensorDataRefreshPeriod;

    @Config.DefaultInt(20)
    public static int rangeTriggerRefreshPeriod;

    @Config.DefaultInt(256)
    public static int SMPMaxAlarmRange;

    @Config.DefaultEnum(RecipeType.RecipeTypeNames.normal)
    public static RecipeType recipes = RecipeType.normal;

    // Useful in case later on we need to trigger a config save
    @SuppressWarnings("unused")
    public static void save() {
        ConfigurationManager.save(Configuration.class);
    }
}
