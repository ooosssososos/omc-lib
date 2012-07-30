package net.obnoxint.mcdev.util;

import java.io.Serializable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class SerializableLocation implements Serializable {

    private static final long serialVersionUID = 8130077079595390518L;

    public static SerializableLocation fromString(String string) {
        if (string != null && !string.isEmpty()) {
            String[] s = string.trim().split(" ");
            try {
                String worldName;
                double x, y, z;
                float pitch, yaw;
                if (s.length == 4) {
                    worldName = s[0];
                    x = Double.parseDouble(s[1]);
                    y = Double.parseDouble(s[2]);
                    z = Double.parseDouble(s[3]);
                    pitch = 0f;
                    yaw = 0f;
                } else if (s.length == 6) {
                    worldName = s[0];
                    x = Double.parseDouble(s[1]);
                    y = Double.parseDouble(s[2]);
                    z = Double.parseDouble(s[3]);
                    pitch = Float.parseFloat(s[4]);
                    yaw = Float.parseFloat(s[5]);
                } else {
                    return null;
                }
                return new SerializableLocation(worldName, x, y, z, pitch, yaw);
            } catch (NumberFormatException e) {}
        }
        return null;
    }

    private final float pitch, yaw;

    private final String worldName;

    private final double x, y, z;

    public SerializableLocation(Location location) {
        this(location.getWorld().getName(), location.getX(), location.getBlockY(), location.getZ(), location.getPitch(), location.getYaw());
    }

    public SerializableLocation(String worldName, double x, double y, double z) {
        this(worldName, x, y, z, 0f, 0f);
    }

    public SerializableLocation(String worldName, double x, double y, double z, float pitch, float yaw) {
        this.worldName = worldName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj instanceof SerializableLocation) {
                SerializableLocation l = (SerializableLocation) obj;
                return l.worldName.equals(worldName) && l.x == x && l.y == y && l.z == z && l.pitch == pitch && l.yaw == yaw;
            } else if (obj instanceof Location) {
                Location l = (Location) obj;
                return new SerializableLocation(l).equals(this);
            } else if (obj instanceof World) {
                return ((World) obj).getName().equals(worldName);
            }
        }
        return false;
    }

    public final float getPitch() {
        return pitch;
    }

    public World getWorld() {
        return Bukkit.getWorld(worldName);
    }

    public final String getWorldName() {
        return worldName;
    }

    public final double getX() {
        return x;
    }

    public final double getY() {
        return y;
    }

    public final float getYaw() {
        return yaw;
    }

    public final double getZ() {
        return z;
    }

    public Location toLocation() {
        return new Location(getWorld(), x, y, z, yaw, pitch);
    }

    @Override
    public String toString() {
        return (new StringBuilder()).append(worldName).append(" ").append(String.valueOf(x)).append(" ").append(String.valueOf(y)).append(" ")
                .append(String.valueOf(z)).append(" ").append(String.valueOf(pitch)).append(" ").append(String.valueOf(yaw)).toString();
    }

}
