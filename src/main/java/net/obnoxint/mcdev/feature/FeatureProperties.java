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

    private final Feature feature;

    private Properties properties = new Properties();

    private File propertiesDirectory = null;

    private File propertiesFile = null;

    public FeatureProperties(Feature feature) {
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
            FileInputStream fis = new FileInputStream(getPropertiesFile());
            getProperties().load(fis);
            fis.close();
            onLoaded();
            Bukkit.getPluginManager().callEvent(new FeaturePropertiesLoadedEvent(feature));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final void storeProperties() {
        if (isDirty()) {
            try {
                onStore();
                FileOutputStream fos = new FileOutputStream(getPropertiesFile());
                getProperties().store(fos, getComment());
                fos.flush();
                fos.close();
                setDirty(false);
                onStored();
                Bukkit.getPluginManager().callEvent(new FeaturePropertiesStoredEvent(feature));
            } catch (IOException e) {
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
            File f = new File(((getPropertiesDirectory() == null) ? getFeature().getFeaturePlugin().getDataFolder() : getPropertiesDirectory()), getFeature().getFeatureName()
                    + PROPERTIES_FILE_EXTENSION);
            if (!f.exists()) {
                try {
                    f.createNewFile();
                    onFileCreated();
                    setDirty();
                } catch (IOException e) {
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

    protected void setComment(String comment) {
        this.comment = comment;
    }

    protected void setDirty() {
        if (!isDirty()) {
            setDirty(true);
        }
    }

    protected final void setPropertiesDirectory(File propertiesDirectory) {
        if (propertiesDirectory != null && this.propertiesDirectory == null) {
            this.propertiesDirectory = propertiesDirectory;
        }
    }

    /**
     * @param dirty set this to true if there are changes to be make or to false, when the properties were successfully synchronized with the properties file.
     */
    private void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

}
