package net.obnoxint.mcdev.omclib;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import net.obnoxint.mcdev.feature.Feature;

public final class PlayerProperties implements Serializable {

    private static final long serialVersionUID = -5184296242775723515L;

    private final Map<String, HashMap<String, String>> settings;

    PlayerProperties() {
        settings = new HashMap<>();
    }

    public String getProperty(final Feature feature, final String id) {
        String r = null;
        if (hasProperty(feature, id)) {
            r = getProperties(feature).get(id);
        }
        return r;
    }

    public String getProperty(final String id) {
        return getProperty(null, id);
    }

    public byte getPropertyByte(final Feature feature, final String id) {
        return Byte.parseByte(getProperty(feature, id));
    }

    public byte getPropertyByte(final String id) {
        return Byte.parseByte(getProperty(id));
    }

    public double getPropertyDouble(final Feature feature, final String id) {
        return Double.parseDouble(getProperty(feature, id));
    }

    public double getPropertyDouble(final String id) {
        return Double.parseDouble(getProperty(id));
    }

    public float getPropertyFloat(final Feature feature, final String id) {
        return Float.parseFloat(getProperty(feature, id));
    }

    public float getPropertyFloat(final String id) {
        return Float.parseFloat(getProperty(id));
    }

    public int getPropertyInteger(final Feature feature, final String id) {
        return Integer.parseInt(getProperty(feature, id));
    }

    public int getPropertyInteger(final String id) {
        return Integer.parseInt(getProperty(id));
    }

    public long getPropertyLong(final Feature feature, final String id) {
        return Long.parseLong(getProperty(feature, id));
    }

    public long getPropertyLong(final String id) {
        return Long.parseLong(getProperty(id));
    }

    public short getPropertyShort(final Feature feature, final String id) {
        return Short.parseShort(getProperty(feature, id));
    }

    public short getPropertyShort(final String id) {
        return Short.parseShort(getProperty(id));
    }

    public boolean hasProperty(final Feature feature, final String id) {
        return getProperties(feature).containsKey(id);
    }

    public boolean hasProperty(final String id) {
        return hasProperty(null, id);
    }

    public void setProperty(final Feature feature, final String id, final byte value) {
        setProperty(feature, id, String.valueOf(value));
    }

    public void setProperty(final Feature feature, final String id, final double value) {
        setProperty(feature, id, String.valueOf(value));
    }

    public void setProperty(final Feature feature, final String id, final float value) {
        setProperty(feature, id, String.valueOf(value));
    }

    public void setProperty(final Feature feature, final String id, final int value) {
        setProperty(feature, id, String.valueOf(value));
    }

    public void setProperty(final Feature feature, final String id, final long value) {
        setProperty(feature, id, String.valueOf(value));
    }

    public void setProperty(final Feature feature, final String id, final short value) {
        setProperty(feature, id, String.valueOf(value));
    }

    public void setProperty(final Feature feature, final String id, final String value) {
        if (id != null) {
            final String key = id.trim();
            if (!key.isEmpty()) {
                getProperties(feature).put(key, value);
            }
        }
    }

    public void setProperty(final String id, final byte value) {
        setProperty(id, String.valueOf(value));
    }

    public void setProperty(final String id, final double value) {
        setProperty(id, String.valueOf(value));
    }

    public void setProperty(final String id, final float value) {
        setProperty(id, String.valueOf(value));
    }

    public void setProperty(final String id, final int value) {
        setProperty(id, String.valueOf(value));
    }

    public void setProperty(final String id, final long value) {
        setProperty(id, String.valueOf(value));
    }

    public void setProperty(final String id, final short value) {
        setProperty(id, String.valueOf(value));
    }

    public void setProperty(final String id, final String value) {
        setProperty(null, id, value);
    }

    private Map<String, String> getProperties(final Feature feature) {
        HashMap<String, String> r;
        final String s = (feature == null) ? null : feature.getFeatureName();
        if (settings.containsKey(s)) {
            r = settings.get(s);
        } else {
            r = new HashMap<>();
            settings.put(s, r);
        }
        return r;
    }

}