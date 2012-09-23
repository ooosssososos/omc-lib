package net.obnoxint.mcdev.feature.event;

import net.obnoxint.mcdev.feature.Feature;
import net.obnoxint.mcdev.feature.FeatureManager;

import org.bukkit.event.HandlerList;

public final class FeatureActiveStateChangedEvent extends FeatureManagerEvent {

    private static final HandlerList handlerList = new HandlerList();

    public static HandlerList getHandlerlist() {
        return handlerList;
    }

    private final boolean active;

    public FeatureActiveStateChangedEvent(final FeatureManager manager, final Feature feature, final boolean active) {
        super(manager, feature);
        this.active = active;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public boolean isActive() {
        return active;
    }

}
