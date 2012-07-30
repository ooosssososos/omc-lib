package net.obnoxint.util;

import java.io.Serializable;

public class VersionNumber implements Comparable<VersionNumber>, Serializable {

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

    public static VersionNumber fromString(String string) {
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
                } catch (NumberFormatException e) {}
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
    public static VersionNumber guessVersion(String v, String sep) {
        if (v != null && !v.trim().isEmpty()) {
            if (sep == null || sep.isEmpty()) {
                sep = VERSION_SEGMENT_SEPARATOR;
            }

            String[] s = v.split(sep);
            int l = s.length;

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
                    } catch (NumberFormatException e) {
                        return new VersionNumber(maj, min, rev, bld, s[0]);
                    }
                } else if (l == 2) {
                    try {
                        maj = Integer.parseInt(s[0]);
                        min = Integer.parseInt(s[1]);
                        return new VersionNumber(maj, min, rev, bld, mu);
                    } catch (NumberFormatException e) {
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
                    } catch (NumberFormatException e) {
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
                    } catch (NumberFormatException e) {
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
                    } catch (NumberFormatException e) {
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

    public VersionNumber(int major, int minor, int revision, int build, String markup) {
        this.major = (major < 0) ? 0 : major;
        this.minor = (minor < 0) ? 0 : minor;
        this.revision = (revision < 0) ? 0 : revision;
        this.build = (build < 0) ? 0 : build;
        this.markup = (markup == null || markup.trim().isEmpty()) ? "" : markup.trim();
    }

    public VersionNumber(VersionNumber v) {
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
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj instanceof VersionNumber) {
                VersionNumber o = (VersionNumber) obj;
                return o.major == major && o.minor == minor && o.revision == revision && o.build == build && o.markup == markup;
            } else if (obj instanceof String) {
                return equals(fromString((String) obj));
            }
        }
        return false;
    }

    public boolean equalsIgnoreMarkup(VersionNumber obj) {
        if (obj != null) {
            return obj.major == major && obj.minor == minor && obj.revision == revision && obj.build == build;
        }
        return false;
    }

    public final int getBuild() {
        return build;
    }

    public final int getMajor() {
        return major;
    }

    public final String getMarkup() {
        return markup;
    }

    public final int getMinor() {
        return minor;
    }

    public final int getRevision() {
        return revision;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder().append(major).append(VERSION_SEGMENT_SEPARATOR).append(minor).append(VERSION_SEGMENT_SEPARATOR).append(revision).append(VERSION_SEGMENT_SEPARATOR).append(build);
        if (!markup.isEmpty()) {
            sb.append(MARKUP_SEGMENT_SEPARATOR).append(markup);
        }
        return sb.toString();
    }

}