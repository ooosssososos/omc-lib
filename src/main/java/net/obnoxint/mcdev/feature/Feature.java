package net.obnoxint.mcdev.feature;

import org.bukkit.plugin.Plugin;

public interface Feature {

    /**
     * @return the name of this feature.
     */
    String getFeatureName();

    /**
     * @return the Plugin instance which holds this feature.
     */
    Plugin getFeaturePlugin();

    /**
     * @return the {@link FeatureProperties} of this feature.
     */
    FeatureProperties getFeatureProperties();

    /**
     * @return true if this feature is active.
     */
    boolean isFeatureActive();

    /**
     * @param active true in order to activate this feature.
     */
    void setFeatureActive(boolean active);

}
