package net.obnoxint.mcdev.omclib;

import net.obnoxint.mcdev.feature.Feature;
import net.obnoxint.mcdev.feature.FeatureManager;

public final class OmcLibFeatureManager extends FeatureManager {

    private boolean active = false;

    private final OmcLibFeatureProperties properties;

    OmcLibFeatureManager(final OmcLibPlugin plugin) {
        super(plugin);
        setFeatureName("omc-lib");
        properties = new OmcLibFeatureProperties(this);
        properties.loadProperties();
        if (properties.isAutoEnable()) {
            setFeatureActive(true);
        }
        addAutoEnabledImplementedFeatures();
    }

    @Override
    public boolean addFeature(final Feature feature) throws UnsupportedOperationException {
        boolean r = false;
        if (properties.isFeatureBlacklisted(feature)) {
            throw new UnsupportedOperationException("The feature \"" + feature.getFeatureName() + "\" is blacklisted.");
        }
        r = super.addFeature(feature);
        if (r && properties.isAutoSetFeatureActiveState()) {
            feature.setFeatureActive(active);
        }
        return r;

    }

    @Override
    public Feature getFeature(final String name) {
        return super.getFeature(name);
    }

    @Override
    public OmcLibPlugin getFeaturePlugin() {
        return (OmcLibPlugin) super.getFeaturePlugin();
    }

    @Override
    public OmcLibFeatureProperties getFeatureProperties() {
        return properties;
    }

    public boolean hasFeature(final Feature feature) {
        return hasFeature(feature.getFeatureName());
    }

    public boolean hasFeature(final String name) {
        return getFeatures().containsKey(name);
    }

    @Override
    public boolean isFeatureActive() {
        return active;
    }

    @Override
    public void setFeatureActive(final boolean active) {
        if (this.active != active) {
            super.setFeatureActive(active);
            if (active) {
                properties.loadProperties();
            } else {
                properties.storeProperties();
            }
            this.active = active;
        }
    }

    private void addAutoEnabledImplementedFeatures() {
        for (final ImplementedFeature v : ImplementedFeature.values()) {
            if (properties.isAutoEnableImplementedFeature(v)) {
                try {
                    addFeature(v.getClazz().newInstance());
                } catch (UnsupportedOperationException | InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
