package net.obnoxint.mcdev.feature.event;

import net.obnoxint.mcdev.feature.Feature;

public abstract class FeaturePropertiesEvent extends FeatureEvent {

    protected FeaturePropertiesEvent(Feature feature) {
        super(feature);
    }

}
