package pl.norbit.treecuter.service;

import pl.norbit.treecuter.config.Settings;

import java.util.HashMap;
import java.util.UUID;

public class ToggleService  {
    private static final HashMap<UUID, Boolean> toggleMap = new HashMap<>();

    private ToggleService() {
        throw new IllegalStateException("This class cannot be instantiated");
    }

    /**
     * Get player toggle state
     * @param playerUUID Player UUID
     * @return Toggle state
     */

    public static boolean getToggle(UUID playerUUID){
        toggleMap.putIfAbsent(playerUUID, true);
        return toggleMap.get(playerUUID);
    }

    /**
     * Change player toggle state
     * @param playerUUID Player UUID
     * @return New toggle state
     */

    public static boolean changeToggle(UUID playerUUID){
        boolean toggle = getToggle(playerUUID);

        toggleMap.put(playerUUID, !toggle);

        return !toggle;
    }

    /**
     * Load player data from config
     * Should be called after calling Settings.loadConfig()
     */
    public static void loadPlayerData() {
        for (UUID uuid: Settings.getPlayerData().keySet()) {
            toggleMap.put(uuid, Settings.getPlayerData().get(uuid));
        }
    }


    /**
     * Save player data to config
     * Should be called before calling Settings.saveConfig()
     */
    public static void savePlayerData() {
        for (UUID uuid: toggleMap.keySet()) {
            Settings.getPlayerData().put(uuid, toggleMap.get(uuid));
        }
    }
}
