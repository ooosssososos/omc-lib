package net.obnoxint.mcdev.feature.event;

import net.obnoxint.mcdev.feature.Feature;

import org.bukkit.event.Event;

public abstract class FeatureEvent extends Event {

    private final Feature feature;

    protected FeatureEvent(Feature feature) {
        this.feature = feature;
    }

    public Feature getFeature() {
        return feature;
    }

}
