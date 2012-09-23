package net.obnoxint.mcdev.omclib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.bukkit.entity.Player;

final class PlayerPropertiesManager {

    private static final String PLAYER_PROPERTIES_FILE_EXTENSION = ".dat";
    private static final String PLAYER_PROPERTIES_FOLDER_NAME = "playerProperties";

    private final Map<String, PlayerProperties> playerProperties = new HashMap<>();
    private final File playerPropertiesFolder;

    PlayerPropertiesManager(final OmcLibPlugin plugin) {
        this.playerPropertiesFolder = new File(plugin.getDataFolder(), PLAYER_PROPERTIES_FOLDER_NAME);
        init();
    }

    PlayerProperties getPlayerProperties(final Player player) {
        PlayerProperties r = null;
        if (player != null) {
            final String n = player.getName();
            if (!playerProperties.containsKey(n)) {
                loadPlayerProperties(n);
            }
            r = playerProperties.get(n);
        }
        return r;
    }

    void storeAllPlayerProperties() {
        for (final String n : playerProperties.keySet()) {
            storePlayerProperties(n);
        }
    }

    private void init() {
        if (!playerPropertiesFolder.exists()) {
            playerPropertiesFolder.mkdirs();
        }
    }

    private void loadPlayerProperties(final String name) {
        final File f = new File(playerPropertiesFolder, name + PLAYER_PROPERTIES_FILE_EXTENSION);
        if (f.exists()) {
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(new GZIPInputStream(new FileInputStream(f)));
                playerProperties.put(name, (PlayerProperties) ois.readObject());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                ois.close();
            } catch (NullPointerException | IOException e) {}
        } else {
            try {
                f.createNewFile();
            } catch (final IOException e) {
                e.printStackTrace();
            }
            playerProperties.put(name, new PlayerProperties());
            storePlayerProperties(name);
        }
    }

    private void storePlayerProperties(final String name) {
        final File f = new File(playerPropertiesFolder, name + PLAYER_PROPERTIES_FILE_EXTENSION);
        ObjectOutputStream ous;
        try {
            ous = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(f)));
            ous.writeObject(playerProperties.get(name));
            ous.flush();
            ous.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

}
