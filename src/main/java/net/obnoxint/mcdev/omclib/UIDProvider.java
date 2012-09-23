package net.obnoxint.mcdev.omclib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import net.obnoxint.mcdev.feature.Feature;

public final class UIDProvider {

    public static final String DEFAULT_UID_NAME = "default";

    private static final String UID_FILE_NAME = "uid";

    private final File uidFile;
    private final Map<String, Map<String, UUID>> uids = new HashMap<>();

    UIDProvider(final OmcLibPlugin plugin) {
        this.uidFile = new File(plugin.getDataFolder(), UID_FILE_NAME);
        init();
    }

    public boolean addUID(final Feature feature, final String name, final UUID uuid) {
        boolean r = false;
        if (feature != null && name != null && !name.isEmpty() && uuid != null) {
            final String f = feature.getFeatureName();
            if (!uids.containsKey(f)) { // TODO: remove redundancy (getUUID())
                uids.put(f, new HashMap<String, UUID>());
            }
            final Map<String, UUID> m = uids.get(f);
            if (!m.containsKey(name)) {
                m.put(name, uuid);
                updateStorage();
                r = true;
            }
        }
        return r;
    }

    public boolean addUID(final Feature feature, final UUID uuid) {
        return addUID(feature, DEFAULT_UID_NAME, uuid);
    }

    public UUID getUID(final Feature feature) {
        return getUID(feature, DEFAULT_UID_NAME);
    }

    public UUID getUID(final Feature feature, final boolean create) {
        return getUID(feature, DEFAULT_UID_NAME, create);
    }

    public UUID getUID(final Feature feature, final String name) {
        return getUID(feature, DEFAULT_UID_NAME, true);
    }

    public UUID getUID(final Feature feature, final String name, final boolean create) {
        UUID r = null;
        if (feature != null && name != null && !name.isEmpty()) {
            final String f = feature.getFeatureName();
            if (!uids.containsKey(f)) { // TODO: remove redundancy (addUID())
                uids.put(f, new HashMap<String, UUID>());
            }
            r = uids.get(f).get(name);
            if (r == null && create) {
                r = UUID.randomUUID();
                addUID(feature, name, r);
            }
        }
        return r;
    }

    public UUID removeUID(final Feature feature) {
        return removeUID(feature, DEFAULT_UID_NAME);
    }

    public UUID removeUID(final Feature feature, final String name) {
        UUID r = null;
        final String f = feature.getFeatureName();
        if (uids.containsKey(f)) {
            r = uids.get(f).remove(r);
            updateStorage();
        }
        return r;
    }

    @SuppressWarnings("unchecked")
    private void init() {
        ObjectInputStream ois = null;
        Map<String, Map<String, UUID>> m = null;
        try {
            if (!uidFile.exists()) {
                uidFile.createNewFile();
                updateStorage();
                return;
            }
            ois = new ObjectInputStream(new GZIPInputStream(new FileInputStream(uidFile)));
            m = (Map<String, Map<String, UUID>>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            ois.close();
        } catch (NullPointerException | IOException e) {}
        uids.putAll(m);
    }

    private void updateStorage() {
        try {
            final ObjectOutputStream ous = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(uidFile)));
            ous.writeObject(uids);
            ous.flush();
            ous.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

}
