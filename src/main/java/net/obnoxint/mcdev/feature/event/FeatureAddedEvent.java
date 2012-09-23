package net.obnoxint.mcdev.feature.event;

import net.obnoxint.mcdev.feature.Feature;
import net.obnoxint.mcdev.feature.FeatureManager;

import org.bukkit.event.HandlerList;

public final class FeatureAddedEvent extends FeatureManagerEvent {

    private static final HandlerList handlerList = new HandlerList();

    public static HandlerList getHandlerlist() {
        return handlerList;
    }

    public FeatureAddedEvent(final FeatureManager manager, final Feature feature) {
        super(manager, feature);
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

}
