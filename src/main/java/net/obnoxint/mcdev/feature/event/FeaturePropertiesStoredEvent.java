package net.obnoxint.mcdev.feature.event;

import net.obnoxint.mcdev.feature.Feature;

import org.bukkit.event.HandlerList;

public final class FeaturePropertiesStoredEvent extends FeaturePropertiesEvent {

    private static final HandlerList handlerList = new HandlerList();

    public static HandlerList getHandlerlist() {
        return handlerList;
    }

    public FeaturePropertiesStoredEvent(Feature feature) {
        super(feature);
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

}
