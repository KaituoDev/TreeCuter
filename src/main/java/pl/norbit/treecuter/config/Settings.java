package pl.norbit.treecuter.config;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import pl.norbit.treecuter.TreeCuter;
import pl.norbit.treecuter.service.TreePlanterService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Settings {
    public static int EFFECT_LEVEL,  MAX_BLOCKS;
    public static boolean SHIFT_MINING, APPLY_MINING_EFFECT, ITEMS_TO_INVENTORY, WORLDGUARD_IS_ENABLED,
            ITEMS_ADDER_IS_ENABLED, AUTO_PLANT, USE_PERMISSIONS, GLOWING_BLOCKS;
    public static List<Material> ACCEPT_TOOLS, ACCEPT_WOOD_BLOCKS, AUTO_PLANT_SAPLINGS;
    public static List<String> BLOCK_WORLDS;
    public static String PERMISSION;
    public static ChatColor GLOWING_COLOR;
    public static String TOOL_NAME;
    public static String TOOL_MATERIAL;
    public static boolean TOOL_ENABLE;

    public static String PERMISSION_MESSAGE, TOGGLE_MESSAGE_ON, TOGGLE_MESSAGE_OFF;

    public static void loadConfig(boolean reload) {

        var javaPlugin = TreeCuter.getInstance();

        if(!reload) javaPlugin.saveDefaultConfig();
        else javaPlugin.reloadConfig();

        var config = javaPlugin.getConfig();

        MAX_BLOCKS = config.getInt("max-blocks");
        EFFECT_LEVEL = config.getInt("effect-level");

        SHIFT_MINING = config.getBoolean("shift-mining");
        APPLY_MINING_EFFECT = config.getBoolean("apply-mining-effect");
        ITEMS_TO_INVENTORY = config.getBoolean("items-to-inventory");

        AUTO_PLANT = config.getBoolean("auto-plant");
        USE_PERMISSIONS = config.getBoolean("use-permissions");
        PERMISSION = config.getString("permission");

        BLOCK_WORLDS = config.getStringList("block-worlds");

        TOOL_ENABLE = config.getBoolean("custom-tool.enable");
        TOOL_NAME = config.getString("custom-tool.name");

        TOOL_MATERIAL = config.getString("custom-tool.material");

        ACCEPT_TOOLS = new ArrayList<>();
        ACCEPT_WOOD_BLOCKS = new ArrayList<>();
        AUTO_PLANT_SAPLINGS = new ArrayList<>();

        config.getStringList("accept-tools")
                .stream()
                .map(Material::getMaterial)
                .filter(Objects::nonNull)
                .forEach(ACCEPT_TOOLS::add);

        config.getStringList("accept-wood-blocks")
                .stream()
                .map(Material::getMaterial)
                .filter(Objects::nonNull)
                .forEach(ACCEPT_WOOD_BLOCKS::add);

        config.getStringList("auto-plant-saplings")
                .stream()
                .map(Material::getMaterial)
                .filter(Objects::nonNull)
                .forEach(AUTO_PLANT_SAPLINGS::add);

        GLOWING_BLOCKS = config.getBoolean("glowing-blocks");

        String color = config.getString("glowing-color");

        try {
            GLOWING_COLOR = ChatColor.valueOf(color.toUpperCase());
        }catch (IllegalArgumentException e){
            TreeCuter.getInstance().getLogger().warning("Wrong glowing color: " + color);
            GLOWING_COLOR = ChatColor.RED;
        }

        PERMISSION_MESSAGE = config.getString("permission-message");
        TOGGLE_MESSAGE_ON = config.getString("toggle.message-on");
        TOGGLE_MESSAGE_OFF = config.getString("toggle.message-off");

        if(Settings.AUTO_PLANT) TreePlanterService.start();
    }
}
