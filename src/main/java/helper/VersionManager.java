package helper;

/**
 *
 * @author HappierGore
 */
public class VersionManager {

    public static int version;

    public static String parseMaterial(String material) {
        if (VersionManager.version >= 13) {
            return material;
        }
        switch (material) {
            case "ENCHANTING_TABLE": {
                return "ENCHANTMENT_TABLE";
            }
            case "OAK_SIGN": {
                return "SIGN";
            }
            case "FIREWORK_STAR": {
                return "FIREWORK_CHARGE";
            }
            case "FIREWORK_ROCKET": {
                return "FIREWORK";
            }
            case "SHULKER_BOX": {
                return "SILVER_SHULKER_BOX";
            }
            case "EXPERIENCE_BOTTLE": {
                return "EXP_BOTTLE";
            }

        }

        return null;
    }
}
