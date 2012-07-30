package net.obnoxint.mcdev.util;

import java.io.Serializable;

import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Note;
import org.bukkit.Note.Tone;
import org.bukkit.entity.Player;

/**
 * A serializable and immutable wrapper-class, combining org.bukkit.Instrument and org.bukkit.Note.<br>
 * 
 * @author obnoxint
 */
public class SerializableNote implements Serializable {

    private static final long serialVersionUID = 6300854456299424547L;

    protected static final String DELIMITER = "-";
    protected static final String SHARP = "#";

    /**
     * Gets a SerializableNote from a pair of bytes.<br>
     * Also checks if the pair results in a legal SerializableNote whose corresponding Instrument-Note-pair can be used by CraftBukkit without causing an
     * IllegalArgumentException.
     * 
     * @param instrument the type of the instrument.
     * @param note the id of the note.
     * @return the SerializableNote.
     */
    public static SerializableNote fromRaw(byte instrument, byte note) {
        SerializableNote r = new SerializableNote(instrument, note);
        return r.validate() ? r : null;
    }

    /**
     * Gets a SerializableNote from an array of bytes.<br>
     * The general contract of this method is that the method equals() in the following example returns true:<br>
     * <code>
     * SerializableNote sn = new SerializableNote(instrument, note)<br>
     * return sn.equals(SerializableNote.fromRaw(sn.toRaw()));
     * </code>
     * 
     * @param data a byte-array with the length of 2.
     * @return the SerializableNote or null if the given data would not lead to a valid SerializableNote (see fromRaw(byte, byte)).
     * @throws IllegalArgumentException if data is null or has not the length of 2.
     */
    public static SerializableNote fromRaw(byte[] data) throws IllegalArgumentException {
        if (data != null && data.length == 2) {
            return fromRaw(data[0], data[1]);
        }
        throw new IllegalArgumentException();
    }

    /**
     * Gets a SerializableNote from a String.<br>
     * The general contract of this method is that the method equals() in the following example returns true:<br>
     * <code>
     * SerializableNote sn = new SerializableNote(instrument, note)<br>
     * return sn.equals(SerializableNote.fromString(sn.toString()));
     * </code>
     * 
     * @param string the String.
     * @return the SerializableNote.
     */
    public static SerializableNote fromString(String string) {
        SerializableNote r = null;
        if (string != null) {
            try {
                String split[] = string.trim().split(DELIMITER);
                if (split.length == 2) {
                    split[0] = split[0].trim();
                    split[1] = split[1].trim();
                    if (!split[0].isEmpty() && !split[1].isEmpty()) {
                        Instrument instrument = Instrument.valueOf(split[0].toUpperCase());
                        if (instrument != null) {
                            boolean sharped = split[1].startsWith(SHARP);
                            if (sharped) {
                                split[1] = split[1].substring(1);
                            }
                            if (split[1].length() == 2) {
                                Tone tone = Tone.valueOf(split[1].substring(0, 1).toUpperCase());
                                int octave = Integer.valueOf(split[1].substring(1));
                                if (tone != null) {
                                    return new SerializableNote(instrument, new Note(octave, tone, sharped));
                                }
                            }

                        }
                    }
                }
            } catch (Exception e) {}
        }
        return r;
    }

    private final byte instrument;
    private final byte note;

    /**
     * Constructs a new SerializableNote.
     * 
     * @param instrument the Instrument.
     * @param note the Note.
     */
    public SerializableNote(Instrument instrument, Note note) {
        if (instrument == null || note == null) {
            throw new IllegalArgumentException("Null reference not permitted.");
        }
        this.instrument = instrument.getType();
        this.note = note.getId();
    }

    private SerializableNote(byte instrument, byte note) {
        this.instrument = instrument;
        this.note = note;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj instanceof SerializableNote) {
                SerializableNote o = (SerializableNote) obj;
                return (o.instrument == instrument) && (o.note == note);
            } else if (obj instanceof byte[]) {
                byte[] o = (byte[]) obj;
                return (o.length == 2) && (o[0] == instrument) && (o[1] == note);
            } else if (obj instanceof Instrument) {
                return ((Instrument) obj).getType() == instrument;
            } else if (obj instanceof Note) {
                return ((Note) obj).getId() == note;
            }
        }
        return false;
    }

    /**
     * Gets the wrapped org.bukkit.Instrument.
     * 
     * @return the Instrument.
     */
    public Instrument getInstrument() {
        return Instrument.getByType(instrument);
    }

    /**
     * Gets the wrapped org.bukkit.Note.
     * 
     * @return the Note.
     */
    public Note getNote() {
        return new Note(note);
    }

    /**
     * Plays this SerializableNote to the given player at the players location current location.
     * 
     * @param player the Player.
     */
    public void play(Player player) {
        play(player, player.getLocation());
    }

    /**
     * Plays this SerializableNote to the given player at the given location.
     * 
     * @param player the Player.
     * @param location the Location.
     */
    public void play(Player player, Location location) {
        player.playNote(location, instrument, note);
    }

    /**
     * Gets a byte array with the length of 2 representing this SerializableNote.
     * 
     * @return a byte array with the length of 2. The first byte represents the type of the Instrument, the second byte represents the id of the Note.
     */
    public byte[] toRaw() {
        byte[] r = new byte[2];
        r[0] = instrument;
        r[1] = note;
        return r;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        try {
            String instrument = getInstrument().name();
            boolean sharp = getNote().isSharped();
            String tone = getNote().getTone().name();
            int octave = getNote().getOctave();

            sb.append(instrument).append(DELIMITER);
            if (sharp) {
                sb.append(SHARP);
            }
            sb.append(tone).append(octave);
        } catch (Exception e) {}

        return sb.toString();
    }

    private boolean validate() {
        return fromString(toString()) != null;
    }

}
