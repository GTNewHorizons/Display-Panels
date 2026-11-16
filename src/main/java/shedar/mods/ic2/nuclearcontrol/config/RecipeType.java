package shedar.mods.ic2.nuclearcontrol.config;

public enum RecipeType {
    normal(RecipeTypeNames.normal),
    normalForce(RecipeTypeNames.normalForce),
    gregtech(RecipeTypeNames.gregtech),
    gregtechForce(RecipeTypeNames.gregtechForce),
    gregtech5(RecipeTypeNames.gregtech5),
    old(RecipeTypeNames.old);

    public final String type;

    RecipeType(String type){
        this.type = type;
    }

    public static final class RecipeTypeNames{
        public static final String normal = "normal";
        public static final String normalForce = "normal-force";
        public static final String gregtech = "gregtech";
        public static final String gregtechForce = "gregtech-force";
        public static final String gregtech5 = "gregtech5";
        public static final String old = "old";
    }
}
