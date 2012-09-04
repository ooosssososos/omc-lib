package net.obnoxint.mcdev.feature;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import net.obnoxint.mcdev.feature.event.FeaturePropertiesLoadedEvent;
import net.obnoxint.mcdev.feature.event.FeaturePropertiesStoredEvent;

import org.bukkit.Bukkit;

public abstract class FeatureProperties {

    public static final String PROPERTIES_FILE_EXTENSION = ".properties";

    protected static final String PROPERTY_DEFAULT_BOOLEAN_FALSE = "false";
    protected static final String PROPERTY_DEFAULT_BOOLEAN_TRUE = "true";
    protected static final String PROPERTY_DEFAULT_NULL = "";

    private String comment = null;
    private boolean dirty = false;
    private File propertiesDirectory = null;
    private File propertiesFile = null;

    private final Feature feature;
    private final Properties properties = new Properties();

    public FeatureProperties(final Feature feature) {
        this.feature = feature;
    }

    public final String getComment() {
        return comment;
    }

    /**
     * @return true if there are unsaved changes.
     */
    public final boolean isDirty() {
        return dirty;
    }

    public final void loadProperties() {
        try {
            onLoad();
            final FileInputStream fis = new FileInputStream(getPropertiesFile());
            getProperties().load(fis);
            fis.close();
            onLoaded();
            Bukkit.getPluginManager().callEvent(new FeaturePropertiesLoadedEvent(feature));
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public final void storeProperties() {
        if (isDirty()) {
            try {
                onStore();
                final FileOutputStream fos = new FileOutputStream(getPropertiesFile());
                getProperties().store(fos, getComment());
                fos.flush();
                fos.close();
                setDirty(false);
                onStored();
                Bukkit.getPluginManager().callEvent(new FeaturePropertiesStoredEvent(feature));
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected Feature getFeature() {
        return feature;
    }

    protected final Properties getProperties() {
        return properties;
    }

    protected final File getPropertiesDirectory() {
        if (propertiesDirectory != null) {
            if (!propertiesDirectory.exists()) {
                propertiesDirectory.mkdirs();
            }
        }
        return propertiesDirectory;
    }

    protected final File getPropertiesFile() {
        if (propertiesFile == null) {
            final File f = new File(((getPropertiesDirectory() == null) ? getFeature().getFeaturePlugin().getDataFolder() : getPropertiesDirectory()), getFeature().getFeatureName()
                    + PROPERTIES_FILE_EXTENSION);
            if (!f.exists()) {
                try {
                    f.createNewFile();
                    onFileCreated();
                    setDirty();
                } catch (final IOException e) {
                    e.printStackTrace();
                    getFeature().setFeatureActive(false);
                }
            }
            propertiesFile = f;
        }
        return propertiesFile;
    }

    /**
     * Called when the properties file was successfully created. Should be overridden.
     */
    protected void onFileCreated() {}

    /**
     * Called before the properties are loaded from the file. Should be overridden.
     */
    protected void onLoad() {}

    /**
     * Called when the properties were successfully loaded from the file. Should be overridden.
     */
    protected void onLoaded() {}

    /**
     * Called before the properties are stored to the file. Should be overridden.
     */
    protected void onStore() {}

    /**
     * Called when the properties were successfully stored to the file. Should be overridden.
     */
    protected void onStored() {}

    protected void setComment(final String comment) {
        this.comment = comment;
    }

    protected void setDirty() {
        if (!isDirty()) {
            setDirty(true);
        }
    }

    protected final void setPropertiesDirectory(final File propertiesDirectory) {
        if (propertiesDirectory != null && this.propertiesDirectory == null) {
            this.propertiesDirectory = propertiesDirectory;
        }
    }

    /**
     * @param dirty set this to true if there are changes to be make or to false, when the properties were successfully synchronized with the properties file.
     */
    private void setDirty(final boolean dirty) {
        this.dirty = dirty;
    }

}
