package net.obnoxint.mcdev.feature.event;

import net.obnoxint.mcdev.feature.Feature;
import net.obnoxint.mcdev.feature.FeatureManager;

public abstract class FeatureManagerEvent extends FeatureEvent {

    private final FeatureManager manager;

    protected FeatureManagerEvent(FeatureManager manager, Feature feature) {
        super(feature);
        this.manager = manager;
    }

    public FeatureManager getManager() {
        return manager;
    }

}
