package shedar.mods.ic2.nuclearcontrol.config;

public enum AlarmType {
    defaultAlarm(AlarmNames.defaultAlarmName),
    scifiAlarm(AlarmNames.scifiAlarmName),
    factorioAlarm(AlarmNames.factorioAlarmName);

    public final String alarm;


    AlarmType(String alarm){
        this.alarm = alarm;
    }

    public static final class AlarmNames{
        public static final String defaultAlarmName = "default";
        public static final String scifiAlarmName = "sci-fi";
        public static final String factorioAlarmName = "factorio-rocket-silo";
    }
}
