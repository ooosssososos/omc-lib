package net.obnoxint.mcdev.omclib;

import java.util.HashMap;
import java.util.Map;

import net.obnoxint.mcdev.feature.Feature;
import net.obnoxint.mcdev.omclib.metrics.OmcLibMetricsFeature;

public enum ImplementedFeature {

    METRICS("metrics", OmcLibMetricsFeature.class);

    static final String FEATURE_PREFIX = "omc-lib_";

    private static final Map<String, ImplementedFeature> nameMap = new HashMap<>();

    static {
        for (final ImplementedFeature v : values()) {
            nameMap.put(v.name, v);
        }
    }

    static ImplementedFeature getByName(final String name) {
        return nameMap.get(name);
    }

    private final Class<? extends Feature> clazz;
    private final String name;

    private ImplementedFeature(final String name, final Class<? extends Feature> clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public String getName() {
        return FEATURE_PREFIX + name;
    }

    Class<? extends Feature> getClazz() {
        return clazz;
    }

    String getInternalName() {
        return name;
    }
}
