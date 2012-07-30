package net.obnoxint.mcdev.omclib.metrics;

import java.io.Serializable;

import net.obnoxint.util.Stat;

public class MetricsPlotter extends Stat implements Serializable {

    private static final long serialVersionUID = -2498039472730050281L;

    public static final String DEFAULT = "Default";

    private transient boolean persistent = true;

    private boolean autoReset = true;

    protected MetricsPlotter(final String id) {
        super(id);
    }

    MetricsPlotter(final MetricsPlotter plotter) {
        super(plotter.getId(), plotter.getBalance());
    }

    public boolean isAutoReset() {
        return autoReset;
    }

    public boolean isPersistent() {
        return persistent;
    }

    public void setAutoReset(final boolean autoReset) {
        this.autoReset = autoReset;
    }

    public void setPersistent(final boolean persistent) {
        this.persistent = persistent;
    }

}
