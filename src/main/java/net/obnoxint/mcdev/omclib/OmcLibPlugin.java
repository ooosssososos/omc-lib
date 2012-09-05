package net.obnoxint.mcdev.omclib;

import java.io.File;

import net.obnoxint.mcdev.omclib.metrics.OmcLibMetricsFeature;
import net.obnoxint.util.RuntimeUtils;
import net.obnoxint.util.VersionNumber;
import net.obnoxint.util.VersionNumber.Versioned;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class OmcLibPlugin extends JavaPlugin implements Versioned {

    public static final String PLUGIN_NAME = "omc-lib";
    private static OmcLibPlugin instance = null;

    private static final String CLASSPATH = "plugins" + File.separator + "omc-lib.jar";

    public static OmcLibPlugin getInstance() {
        return instance;
    }

    /**
     * Prepares the classpath of the execution environment for products depending on omc-lib.
     * 
     * @return true if the class path was modified.
     */
    private static void prepareClassPath() {
        RuntimeUtils.removeFromClassPath(CLASSPATH);
        RuntimeUtils.addToClassPath(CLASSPATH);
    }

    private VersionNumber versionNumber;
    private UIDProvider uidProvider;
    private OmcLibFeatureManager featureManager;
    private PlayerPropertiesManager playerPropertiesManager;
    private OmcCommandExecutor commandExecutor;
    private TickUtil tickUtil;

    private File dataFolder = null;

    public OmcLibPlugin() {
        prepareClassPath();
        instance = this;
    }

    public OmcCommandExecutor getCommandExecutor() {
        return commandExecutor;
    }

    /**
     * Returns the folder that the plugin data's files are located in. If the folder doesn't exist it will be created.
     * 
     * @return The folder.
     */
    @Override
    public File getDataFolder() {
        if (dataFolder == null) {
            dataFolder = super.getDataFolder();
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
            }
        }
        return dataFolder;
    }

    public OmcLibFeatureManager getFeatureManager() {
        return featureManager;
    }

    public OmcLibMetricsFeature getMetricsFeature() {
        return (OmcLibMetricsFeature) featureManager.getFeature(ImplementedFeature.METRICS.getName());
    }

    public PlayerProperties getPlayerProperties(final Player player) {
        return playerPropertiesManager.getPlayerProperties(player);
    }

    public TickUtil getTickUtil() {
        return tickUtil;
    }

    public UIDProvider getUidProvider() {
        return uidProvider;
    }

    @Override
    public VersionNumber getVersionNumber() {
        return versionNumber;
    }

    @Override
    public void onDisable() {
        featureManager.setFeatureActive(false);
        playerPropertiesManager.storeAllPlayerProperties();
        instance = null;
    }

    @Override
    public void onEnable() {
        versionNumber = VersionNumber.fromString(getDescription().getVersion());
        uidProvider = new UIDProvider(this);
        playerPropertiesManager = new PlayerPropertiesManager(this);
        featureManager = new OmcLibFeatureManager(this);
        commandExecutor = new OmcCommandExecutor();
        tickUtil = new TickUtil();
        getCommand("omc").setExecutor(commandExecutor);
    }

}
