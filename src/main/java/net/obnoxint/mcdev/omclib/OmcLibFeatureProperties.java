package net.obnoxint.mcdev.omclib;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import net.obnoxint.mcdev.feature.Feature;
import net.obnoxint.mcdev.feature.FeatureProperties;

public final class OmcLibFeatureProperties extends FeatureProperties {

    private static final String PROPERTY_NAME_AUTO_ENABLE = "autoEnable";
    private static final String PROPERTY_NAME_AUTO_SET_FEATURE_ACTIVE_STATE = "autoSetFeatureActiveState";
    private static final String PROPERTY_NAME_AUTO_ENABLE_IMPLEMENTED_FEATURES = "autoEnableImplementedFeatures";
    private static final String PROPERTY_NAME_BLACKLISTED_FEATURES = "blackListedFeatures";

    private static final String ITEM_SEPARATOR = ";";

    private boolean autoEnable = true;
    private boolean autoSetFeatureActiveState = true;
    private final Set<String> autoEnableImplementedFeatures = new HashSet<>();
    private final Set<String> blacklistedFeatures = new HashSet<>();

    OmcLibFeatureProperties(final OmcLibFeatureManager feature) {
        super(feature);
    }

    public boolean isAutoEnable() {
        return autoEnable;
    }

    public boolean isAutoEnableImplementedFeature(final ImplementedFeature feature) {
        return autoEnableImplementedFeatures.contains(feature.getInternalName());
    }

    public boolean isAutoSetFeatureActiveState() {
        return autoSetFeatureActiveState;
    }

    public boolean isFeatureBlacklisted(final Feature feature) {
        return blacklistedFeatures.contains(feature.getFeatureName());
    }

    @Override
    protected void onFileCreated() {
        setDefaultAutoEnableImplementedFeatures();
        onStore();
    }

    @Override
    protected void onLoaded() {
        final Properties p = getProperties();

        autoEnable = Boolean.parseBoolean(p.getProperty(PROPERTY_NAME_AUTO_ENABLE));
        autoSetFeatureActiveState = Boolean.parseBoolean(p.getProperty(PROPERTY_NAME_AUTO_SET_FEATURE_ACTIVE_STATE));

        { // auto-enable implemented feature
            final String[] prop = p.getProperty(PROPERTY_NAME_AUTO_ENABLE_IMPLEMENTED_FEATURES).split(ITEM_SEPARATOR);
            for (final String s : prop) {
                if (!s.isEmpty()) {
                    autoEnableImplementedFeatures.add(s);
                }
            }
        }

        { // blacklisted features
            final String[] prop = p.getProperty(PROPERTY_NAME_BLACKLISTED_FEATURES).split(ITEM_SEPARATOR);
            for (final String s : prop) {
                if (!s.isEmpty()) {
                    blacklistedFeatures.add(s);
                }
            }
        }
    }

    @Override
    protected void onStore() {
        final Properties p = getProperties();

        p.setProperty(PROPERTY_NAME_AUTO_ENABLE, String.valueOf(autoEnable));
        p.setProperty(PROPERTY_NAME_AUTO_SET_FEATURE_ACTIVE_STATE, String.valueOf(autoSetFeatureActiveState));

        { // auto-enable implemented feature
            final StringBuilder sb = new StringBuilder();
            final Iterator<String> it = autoEnableImplementedFeatures.iterator();
            while (it.hasNext()) {
                sb.append(it.next());
                if (it.hasNext()) {
                    sb.append(ITEM_SEPARATOR);
                }
            }
            p.setProperty(PROPERTY_NAME_AUTO_ENABLE_IMPLEMENTED_FEATURES, sb.toString());
        }

        { // blacklisted features
            final StringBuilder sb = new StringBuilder();
            final Iterator<String> it = blacklistedFeatures.iterator();
            while (it.hasNext()) {
                sb.append(it.next());
                if (it.hasNext()) {
                    sb.append(ITEM_SEPARATOR);
                }
            }
            p.setProperty(PROPERTY_NAME_BLACKLISTED_FEATURES, sb.toString());
        }
    }

    void setAutoEnable(final boolean autoEnable) {
        if (this.autoEnable != autoEnable) {
            this.autoEnable = autoEnable;
            setDirty();
        }
    }

    void setAutoEnableImplementedFeature(final ImplementedFeature feature, final boolean autoEnableFeature) {
        if (feature != null) {
            final String n = feature.getInternalName();
            if (autoEnableFeature && !autoEnableImplementedFeatures.contains(n)) {
                autoEnableImplementedFeatures.add(n);
                setDirty();
            } else if (!autoEnableFeature && autoEnableImplementedFeatures.contains(n)) {
                autoEnableImplementedFeatures.remove(n);
                setDirty();
            }
        }
    }

    void setAutoSetFeatureActiveState(final boolean autoSetFeatureActiveState) {
        if (this.autoSetFeatureActiveState != autoSetFeatureActiveState) {
            this.autoSetFeatureActiveState = autoSetFeatureActiveState;
            setDirty();
        }
    }

    void setFeatureBlacklisted(final Feature feature, final boolean blacklisted) {
        final String n = feature.getFeatureName();
        if (blacklisted) {
            if (!blacklistedFeatures.contains(n)) {
                blacklistedFeatures.add(n);
                setDirty();
            }
        } else {
            if (blacklistedFeatures.contains(n)) {
                blacklistedFeatures.remove(n);
                setDirty();
            }
        }
    }

    private void setDefaultAutoEnableImplementedFeatures() {
        autoEnableImplementedFeatures.clear();
        for (final ImplementedFeature v : ImplementedFeature.values()) {
            autoEnableImplementedFeatures.add(v.getInternalName());
        }
    }

}
