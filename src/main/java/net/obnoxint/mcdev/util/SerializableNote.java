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
    public static SerializableNote fromRaw(final byte instrument, final byte note) {
        final SerializableNote r = new SerializableNote(instrument, note);
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
    public static SerializableNote fromRaw(final byte[] data) throws IllegalArgumentException {
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
    public static SerializableNote fromString(final String string) {
        final SerializableNote r = null;
        if (string != null) {
            try {
                final String split[] = string.trim().split(DELIMITER);
                if (split.length == 2) {
                    split[0] = split[0].trim();
                    split[1] = split[1].trim();
                    if (!split[0].isEmpty() && !split[1].isEmpty()) {
                        final Instrument instrument = Instrument.valueOf(split[0].toUpperCase());
                        if (instrument != null) {
                            final boolean sharped = split[1].startsWith(SHARP);
                            if (sharped) {
                                split[1] = split[1].substring(1);
                            }
                            if (split[1].length() == 2) {
                                final Tone tone = Tone.valueOf(split[1].substring(0, 1).toUpperCase());
                                final int octave = Integer.valueOf(split[1].substring(1));
                                if (tone != null) {
                                    return new SerializableNote(instrument, new Note(octave, tone, sharped));
                                }
                            }

                        }
                    }
                }
            } catch (final Exception e) {}
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
    public SerializableNote(final Instrument instrument, final Note note) {
        if (instrument == null || note == null) {
            throw new IllegalArgumentException("Null reference not permitted.");
        }
        this.instrument = instrument.getType();
        this.note = note.getId();
    }

    private SerializableNote(final byte instrument, final byte note) {
        this.instrument = instrument;
        this.note = note;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj != null) {
            if (obj instanceof SerializableNote) {
                final SerializableNote o = (SerializableNote) obj;
                return (o.instrument == instrument) && (o.note == note);
            } else if (obj instanceof byte[]) {
                final byte[] o = (byte[]) obj;
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
    public void play(final Player player) {
        play(player, player.getLocation());
    }

    /**
     * Plays this SerializableNote to the given player at the given location.
     * 
     * @param player the Player.
     * @param location the Location.
     */
    public void play(final Player player, final Location location) {
        player.playNote(location, instrument, note);
    }

    /**
     * Gets a byte array with the length of 2 representing this SerializableNote.
     * 
     * @return a byte array with the length of 2. The first byte represents the type of the Instrument, the second byte represents the id of the Note.
     */
    public byte[] toRaw() {
        final byte[] r = new byte[2];
        r[0] = instrument;
        r[1] = note;
        return r;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        try {
            final String instrument = getInstrument().name();
            final boolean sharp = getNote().isSharped();
            final String tone = getNote().getTone().name();
            final int octave = getNote().getOctave();

            sb.append(instrument).append(DELIMITER);
            if (sharp) {
                sb.append(SHARP);
            }
            sb.append(tone).append(octave);
        } catch (final Exception e) {}

        return sb.toString();
    }

    private boolean validate() {
        return fromString(toString()) != null;
    }

}
