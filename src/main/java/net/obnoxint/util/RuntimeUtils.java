package net.obnoxint.util;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public final class RuntimeUtils {

    public static final String CLASSPATH_PROPERTY = "java.class.path";

    /**
     * Adds the given element to the JVMs classpath.
     * 
     * @param element the element to add.
     */
    public static void addToClassPath(final String element) {
        if (element != null && !element.trim().isEmpty()) {
            System.setProperty(CLASSPATH_PROPERTY, System.getProperty(CLASSPATH_PROPERTY) + File.pathSeparator + element.trim());
        }
    }

    /**
     * @return an Array of Strings representing the JVMs classpath elements.
     */
    public static String[] getClassPathElements() {
        final List<String> r = new ArrayList<>();
        final String[] a = System.getProperty(CLASSPATH_PROPERTY).split(File.pathSeparator);
        for (final String s : a) {
            if (!s.trim().isEmpty()) {
                r.add(s);
            }
        }
        return r.toArray(new String[r.size()]);
    }

    /**
     * Prints the stack trace of a throwable to a String.
     * 
     * @param throwable the Throwable.
     * @return the String converted stack trace of throwable.
     */
    public static String getStackTraceString(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        pw.flush();
        pw.close();
        return sw.toString();
    }

    /**
     * Gets an Array of Strings representing the elements of a stack trace of an Exception.
     * 
     * @param e the Exception.
     * @return an Array of Strings representing the elements of e's stack trace.
     */
    public static String[] getStackTraceStringArray(final Exception e) {
        return (e == null) ? null : getStackTraceStringArray(e.getStackTrace());
    }

    /**
     * Converts an Array of StackTraceElements to an Array of Strings.
     * 
     * @param stack the StackTraceElements.
     * @return the converted StackTraceElements.
     */
    public static String[] getStackTraceStringArray(final StackTraceElement[] stack) {
        if (stack != null) {
            final String[] r = new String[stack.length];
            for (int i = 0; i < stack.length; i++) {
                r[i] = stack[i].toString();
            }
            return r;
        }
        return null;
    }

    /**
     * Removes an element from the JVM classpath.
     * 
     * @param element the element to remove.
     * @return true if the element was present and has been removed.
     */
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

    /**
     * Overwrites all JVM classpath entries with the given elements.
     * 
     * @param elements the classpath elements.
     * @return an Array containing the previous classpath elements.
     */
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
