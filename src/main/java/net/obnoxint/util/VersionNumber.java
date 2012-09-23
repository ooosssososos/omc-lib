package net.obnoxint.util;

import java.io.Serializable;

/**
 * <p>
 * Instances of this class represent a version number in the format of [Mayor version].[Minor version].[Revision].[Build].<br>
 * A markup (e.g -RC1) can be attached and will be ignored by the {@link #compareTo(VersionNumber)} method but not by the {@link #equals(Object)} method.
 * </p>
 * A textual representation (or return value of the toString() method) of this class could look like this: <i>1.0.10.500-RC1</i>
 */
public class VersionNumber implements Comparable<VersionNumber>, Serializable {

    /**
     * This is interface can be used in order to signalize that a class is utilizing a single static instance of the VersionNumber class.
     */
    public static interface Versioned {

        /**
         * Gets the {@link VersionNumber} of a class implementing {@link Versioned}.
         * 
         * @return the VersionNumber.
         */
        public VersionNumber getVersionNumber();

    }

    public static final VersionNumber NULL = new VersionNumber(0, 0, 0, 0, null);

    /**
     * The version segment separator.
     */
    public static final String VERSION_SEGMENT_SEPARATOR = ".";

    /**
     * The markup segment separator.
     */
    public static final String MARKUP_SEGMENT_SEPARATOR = "-";

    private static final long serialVersionUID = 9142373700216765155L;

    /**
     * <p>
     * Creates an instance of VersionNumber based on a String.
     * </p>
     * <p>
     * The general contract of this method is<br>
     * <code>
     * VersionNumber vn = new VersionNumber(0, 0, 1, 0, null);<br>
     * boolean b = vn.equals(vn.fromString(vn.toString()));<br>
     * </code> where b is <i>true</i>.
     * <p>
     * 
     * @param string the String representing a version number.
     * @return the VersionNumber or null if an instance of VersionNumber can not be build from the given String.
     */
    public static VersionNumber fromString(final String string) {
        if (string != null) {
            int maj, min, rev, bld;
            String mu = "";
            String[] split1, split2;

            split1 = string.split(VERSION_SEGMENT_SEPARATOR);
            if (split1.length == 4) {
                split2 = split1[3].split(MARKUP_SEGMENT_SEPARATOR);
                try {
                    maj = Integer.parseInt(split1[0]);
                    min = Integer.parseInt(split1[1]);
                    rev = Integer.parseInt(split1[2]);
                    bld = Integer.parseInt(split2[0]);
                    if (split2.length > 1) {
                        for (int i = 1; i < split2.length; i++) {
                            mu += split2[i];
                            if (i < split2.length - 1) {
                                mu += MARKUP_SEGMENT_SEPARATOR;
                            }
                        }
                    }
                    return new VersionNumber(maj, min, rev, bld, mu);
                } catch (final NumberFormatException e) {}
            }
        }
        return null;
    }

    /**
     * Tries to guess a VersionNumber based on a String. The returned value might be inaccurate.
     * 
     * @param v the string.
     * @param sep the separator.
     * @return a new instance of VersionNumber or null if no guessing was possible.
     */
    public static VersionNumber guessVersion(final String v, String sep) {
        if (v != null && !v.trim().isEmpty()) {
            if (sep == null || sep.isEmpty()) {
                sep = VERSION_SEGMENT_SEPARATOR;
            }

            final String[] s = v.split(sep);
            final int l = s.length;

            if (l > 0) {

                int maj = -1;
                int min = -1;
                int rev = -1;
                int bld = -1;
                String mu = "";

                if (l == 1) {
                    try {
                        maj = Integer.parseInt(s[0]);
                        return new VersionNumber(maj, min, rev, bld, mu);
                    } catch (final NumberFormatException e) {
                        return new VersionNumber(maj, min, rev, bld, s[0]);
                    }
                } else if (l == 2) {
                    try {
                        maj = Integer.parseInt(s[0]);
                        min = Integer.parseInt(s[1]);
                        return new VersionNumber(maj, min, rev, bld, mu);
                    } catch (final NumberFormatException e) {
                        if (maj == -1) {
                            return new VersionNumber(maj, min, rev, bld, s[0] + sep + s[1]);
                        }
                        if (min == -1) {
                            return new VersionNumber(maj, min, rev, bld, s[1]);
                        }
                        return null;
                    }
                } else if (l == 3) {
                    try {
                        maj = Integer.parseInt(s[0]);
                        min = Integer.parseInt(s[1]);
                        rev = Integer.parseInt(s[2]);
                        return new VersionNumber(maj, min, rev, bld, mu);
                    } catch (final NumberFormatException e) {
                        if (maj == -1) {
                            return new VersionNumber(maj, min, rev, bld, s[0] + sep + s[1] + sep + s[2]);
                        }
                        if (min == -1) {
                            return new VersionNumber(maj, min, rev, bld, s[1] + sep + s[2]);
                        }
                        if (bld == -1) {
                            return new VersionNumber(maj, min, rev, bld, s[2]);
                        }
                        return null;
                    }
                } else if (l == 4) {
                    try {
                        maj = Integer.parseInt(s[0]);
                        min = Integer.parseInt(s[1]);
                        rev = Integer.parseInt(s[2]);
                        bld = Integer.parseInt(s[3]);
                        return new VersionNumber(maj, min, rev, bld, mu);
                    } catch (final NumberFormatException e) {
                        if (maj == -1) {
                            return new VersionNumber(maj, min, rev, bld, s[0] + sep + s[1] + sep + s[2] + sep + s[3]);
                        }
                        if (min == -1) {
                            return new VersionNumber(maj, min, rev, bld, s[1] + sep + s[2] + sep + s[3]);
                        }
                        if (bld == -1) {
                            return new VersionNumber(maj, min, rev, bld, s[2] + sep + s[3]);
                        }
                        if (rev == -1) {
                            return new VersionNumber(maj, min, rev, bld, s[3]);
                        }
                        return null;
                    }
                } else if (l == 5) {
                    try {
                        maj = Integer.parseInt(s[0]);
                        min = Integer.parseInt(s[1]);
                        rev = Integer.parseInt(s[2]);
                        bld = Integer.parseInt(s[3]);
                        mu = s[4];
                        return new VersionNumber(maj, min, rev, bld, mu);
                    } catch (final NumberFormatException e) {
                        if (maj == -1) {
                            return new VersionNumber(maj, min, rev, bld, s[0] + sep + s[1] + sep + s[2] + sep + s[3] + sep + s[4]);
                        }
                        if (min == -1) {
                            return new VersionNumber(maj, min, rev, bld, s[1] + sep + s[2] + sep + s[3] + sep + s[4]);
                        }
                        if (bld == -1) {
                            return new VersionNumber(maj, min, rev, bld, s[2] + sep + s[3] + sep + s[4]);
                        }
                        if (rev == -1) {
                            return new VersionNumber(maj, min, rev, bld, s[3] + sep + s[4]);
                        }
                        return null;
                    }
                }
            }
        }
        return null;
    }

    private final int major;
    private final int minor;
    private final int revision;
    private final int build;
    private final String markup;

    /**
     * <p>
     * Creates a new instance of VersionNumber.
     * </p>
     * <p>
     * Negative numbers are not allowed. If a negative number is given, it will be replaced by 0.
     * </p>
     * 
     * @param major the major version.
     * @param minor the minor version.
     * @param revision the revision.
     * @param build the build number.
     * @param markup an optional markup or null.
     */
    public VersionNumber(final int major, final int minor, final int revision, final int build, final String markup) {
        this.major = (major < 0) ? 0 : major;
        this.minor = (minor < 0) ? 0 : minor;
        this.revision = (revision < 0) ? 0 : revision;
        this.build = (build < 0) ? 0 : build;
        this.markup = (markup == null || markup.trim().isEmpty()) ? "" : markup.trim();
    }

    /**
     * Creates a clone of an instance of VersionNumber.
     * 
     * @param v the VersionNumber.
     */
    public VersionNumber(final VersionNumber v) {
        this(v.major, v.minor, v.revision, v.build, v.markup);
    }

    @Override
    public int compareTo(VersionNumber o) {
        o = new VersionNumber(o.major, o.minor, o.revision, o.build, null);
        if (o.major == major && o.minor == minor && o.build == build && o.revision == revision) {
            return 0;
        } else {
            if (o.major > major) {
                return 1;
            } else if (o.major < major) {
                return -1;
            } else {
                if (o.minor > minor) {
                    return 1;
                } else if (o.minor < minor) {
                    return -1;
                } else {
                    if (o.revision > revision) {
                        return 1;
                    } else if (o.revision < revision) {
                        return -1;
                    } else {
                        if (o.build > build) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj != null) {
            if (obj instanceof VersionNumber) {
                final VersionNumber o = (VersionNumber) obj;
                return o.major == major && o.minor == minor && o.revision == revision && o.build == build && o.markup == markup;
            } else if (obj instanceof String) {
                return equals(fromString((String) obj));
            }
        }
        return false;
    }

    public boolean equalsIgnoreMarkup(final VersionNumber obj) {
        if (obj != null) {
            return obj.major == major && obj.minor == minor && obj.revision == revision && obj.build == build;
        }
        return false;
    }

    /**
     * @return the build number.
     */
    public final int getBuild() {
        return build;
    }

    /**
     * @return the major version.
     */
    public final int getMajor() {
        return major;
    }

    /**
     * @return the markup or an empty String if no markup has been defined.
     */
    public final String getMarkup() {
        return markup;
    }

    /**
     * @return the minor version.
     */
    public final int getMinor() {
        return minor;
    }

    /**
     * @return the revision.
     */
    public final int getRevision() {
        return revision;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder().append(major).append(VERSION_SEGMENT_SEPARATOR).append(minor).append(VERSION_SEGMENT_SEPARATOR).append(revision).append(VERSION_SEGMENT_SEPARATOR).append(build);
        if (!markup.isEmpty()) {
            sb.append(MARKUP_SEGMENT_SEPARATOR).append(markup);
        }
        return sb.toString();
    }

}