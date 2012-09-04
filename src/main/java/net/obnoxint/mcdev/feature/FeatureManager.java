package net.obnoxint.mcdev.feature;

import java.util.HashMap;

import net.obnoxint.mcdev.feature.event.FeatureActiveStateChangedEvent;
import net.obnoxint.mcdev.feature.event.FeatureAddedEvent;
import net.obnoxint.mcdev.feature.event.FeatureRemovedEvent;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public abstract class FeatureManager implements Feature {

    private final HashMap<String, Feature> features = new HashMap<>();

    private String name = null;

    private final Plugin plugin;

    public FeatureManager(final Plugin plugin) {
        if (plugin == null) {
            throw new NullPointerException();
        }
        this.plugin = plugin;
    }

    @Override
    public String getFeatureName() {
        if (name == null) {
            name = getFeaturePlugin().getName() + ".features";
        }
        return name;
    }

    @Override
    public Plugin getFeaturePlugin() {
        return plugin;
    }

    @Override
    public void setFeatureActive(final boolean active) {
        for (final Feature f : getFeatures().values()) {
            if (active != f.isFeatureActive()) {
                f.setFeatureActive(active);
                Bukkit.getPluginManager().callEvent(new FeatureActiveStateChangedEvent(this, f, active));
            }
        }
    }

    protected boolean addFeature(final Feature feature) {
        if (!getFeatures().containsKey(feature.getFeatureName())) {
            features.put(feature.getFeatureName(), feature);
            Bukkit.getPluginManager().callEvent(new FeatureAddedEvent(this, feature));
            return true;
        }
        return false;
    }

    protected Feature getFeature(final String name) {
        return getFeatures().get(name);
    }

    protected final HashMap<String, Feature> getFeatures() {
        return new HashMap<>(features);
    }

    protected boolean removeFeature(final Feature feature) {
        final boolean r = features.remove(feature.getFeatureName()) != null;
        if (r) {
            Bukkit.getPluginManager().callEvent(new FeatureRemovedEvent(this, feature));
        }
        return r;
    }

    protected String setFeatureName(final String name) {
        if (this.name == null && name != null && !name.trim().isEmpty()) {
            this.name = name.trim();
        }
        return this.name;
    }
}
