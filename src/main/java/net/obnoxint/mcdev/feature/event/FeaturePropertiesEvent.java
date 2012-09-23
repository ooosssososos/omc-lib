package net.obnoxint.mcdev.feature.event;

import net.obnoxint.mcdev.feature.Feature;

public abstract class FeaturePropertiesEvent extends FeatureEvent {

    protected FeaturePropertiesEvent(final Feature feature) {
        super(feature);
    }

}
