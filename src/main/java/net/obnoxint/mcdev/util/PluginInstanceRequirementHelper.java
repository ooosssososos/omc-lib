package net.obnoxint.mcdev.util;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPluginLoader;

/**
 * <p>
 * This class can help to achieve a reference to any plugin instance loaded by a {@link JavaPluginLoader} if the implementing {@link Plugin} is also loaded by the same
 * JavaPluginLoader.
 * </p>
 * <p>
 * If used inside an instance of Thread, this class will try to acquire a reference to a Plugin-instance of the name declared in the constructor call. The run() method can also be
 * called manually but will block until the reference was acquired. It is therefore recommended to call the run() method by the start() method of a Thread-instance.
 * </p>
 * <p>
 * As soon as the reference was acquired the getPlugin() method will return the Plugin-instance. Before that it will always return null.
 * </p>
 * 
 * @author obnoxint
 */
public final class PluginInstanceRequirementHelper implements Runnable {

    /**
     * <p>
     * A class implementing this interface must be passed to the constructor of {@link PluginInstanceRequirementHelper}. As soon as a reference to the requested plugin was
     * acquired, the onPluginInstanceAcquired()-method will be called passing a reference to the requested {@link Plugin}
     * </p>
     * <p>
     * The same instance of the implementing class can be (re-)used to acquire different plugin references.
     * </p>
     */
    public static interface PluginInstanceRequester {

        /**
         * Will be called by a {@link PluginInstanceRequirementHelper} as soon as a reference to the requested {@link Plugin} was acquired. The passed reference
         * is synchronized and this method should therefore return as soon as possible.
         * 
         * @param plugin
         */
        public void onPluginInstanceAcquired(Plugin plugin);
    }

    private static final long INTERVAL = 100L;

    private boolean acquired = false;
    private boolean stopped = false;
    private Plugin plugin = null;

    private final String pluginName;
    private final PluginInstanceRequester requester;

    /**
     * Creates a new PluginInstanceRequirementHelper.
     * 
     * @param pluginName the name of the requested plugin. Must not be null and pluginName.trim().isEmpty must return false or an IllegalArgumentException will
     *            be thrown.
     * @param requester the instance of {@link PluginInstanceRequester} to notify as soon as a reference was acquired.
     */
    public PluginInstanceRequirementHelper(final String pluginName, final PluginInstanceRequester requester) {
        if (pluginName == null || requester == null || pluginName.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.pluginName = pluginName.trim();
        this.requester = requester;
    }

    /**
     * Gets the acquired {@link Plugin} reference.
     * 
     * @return the {@link Plugin} or null if not acquired.
     */
    public Plugin getPlugin() {
        return plugin;
    }

    /**
     * @return true if a reference to the requested plugin was acquired.
     */
    public boolean isAcquired() {
        return acquired;
    }

    /**
     * @return true if the execution has been stopped by calling the stop() method.
     */
    public boolean isStopped() {
        return stopped;
    }

    /**
     * Manually resumes the PluginInstanceRequirementHelper. Counterpart to stop(). Has no effect if a reference to the requested plugin is already acquired.
     */
    public void resume() {
        stopped = false;
    }

    @Override
    public void run() {
        long lastRun = System.currentTimeMillis();
        synchronized (this) {
            while (!isAcquired() && !isStopped() && (lastRun + INTERVAL < System.currentTimeMillis())) {
                setPlugin(Bukkit.getPluginManager().getPlugin(getPluginName()));
                lastRun += INTERVAL;
            }
        }
    }

    /**
     * Manually stops the PluginInstanceRequirementHelper from trying to acquire a reference to the requested plugin.
     */
    public void stop() {
        stopped = true;
    }

    private String getPluginName() {
        return pluginName;
    }

    private PluginInstanceRequester getRequester() {
        return requester;
    }

    private void setAcquired() {
        acquired = true;
        stop();
        synchronized (plugin) {
            getRequester().onPluginInstanceAcquired(getPlugin());
        }
    }

    private void setPlugin(final Plugin plugin) {
        if (plugin != null) {
            this.plugin = plugin;
            setAcquired();
        }
    }

}
