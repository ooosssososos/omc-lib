package net.obnoxint.util;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public final class RuntimeUtils {

    public static final String CLASSPATH_PROPERTY = "java.class.path";

    public static void addToClassPath(final String element) {
        if (element != null && !element.trim().isEmpty()) {
            System.setProperty(CLASSPATH_PROPERTY, System.getProperty(CLASSPATH_PROPERTY) + File.pathSeparator + element.trim());
        }
    }

    public static String[] getClassPathElements() {
        final List<String> r = new ArrayList<>();
        String[] a = System.getProperty(CLASSPATH_PROPERTY).split(File.pathSeparator);
        for (final String s : a) {
            if (!s.trim().isEmpty()) {
                r.add(s);
            }
        }
        return r.toArray(new String[r.size()]);
    }

    public static String getStackTraceString(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        pw.flush();
        pw.close();
        return sw.toString();
    }

    public static String[] getStackTraceStringArray(Exception e) {
        return (e == null) ? null : getStackTraceStringArray(e.getStackTrace());
    }

    public static String[] getStackTraceStringArray(StackTraceElement[] stack) {
        if (stack != null) {
            String[] r = new String[stack.length];
            for (int i = 0; i < stack.length; i++) {
                r[i] = stack[i].toString();
            }
            return r;
        }
        return null;
    }

    public static boolean removeFromClassPath(final String element) {
        boolean r = false;
        if (element != null) {
            final String e = element.trim();
            if (!e.isEmpty()) {
                final List<String> l = new ArrayList<>();
                for (final String s : getClassPathElements()) {
                    if (!s.equals(e)) {
                        l.add(s);
                    } else {
                        r = true;
                    }
                }
                if (r) {
                    setClassPath(l.toArray(new String[l.size()]));
                }
            }
        }
        return r;
    }

    public static String[] setClassPath(final String[] elements) {
        String[] r = null;
        if (elements.length > 0) {
            r = getClassPathElements();
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < elements.length; i++) {
                sb.append(elements[i]);
                if (i != elements.length - 1) {
                    sb.append(File.pathSeparator);
                }
            }
            System.setProperty(CLASSPATH_PROPERTY, sb.toString());
        }
        return r;
    }

    private RuntimeUtils() {}
}
