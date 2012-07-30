package net.obnoxint.mcdev.omclib.metrics;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public final class MetricsGraph implements Serializable {

    private static final long serialVersionUID = 148171122271697066L;

    public static final String DEFAULT = "Default";

    private final String name;
    private volatile Set<MetricsPlotter> plotters = new HashSet<>();

    MetricsGraph(final MetricsGraph graph) {
        this.name = graph.name;
        for (final MetricsPlotter plotter : graph.plotters) {
            this.plotters.add(new MetricsPlotter(plotter));
        }
    }

    MetricsGraph(final String name) {
        this.name = name;
    }

    public boolean addPlotter(final MetricsPlotter plotter) {
        return plotters.add(plotter);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj != null) {
            if (obj instanceof String) {
                return ((String) obj).equals(name);
            } else if (obj instanceof MetricsGraph) {
                return ((MetricsGraph) obj).name.equals(name);
            }
        }
        return false;
    }

    public MetricsPlotter getDefaultPlotter() {
        return getPlotter(MetricsPlotter.DEFAULT);
    }

    public String getName() {
        return name;
    }

    public MetricsPlotter getPlotter(String id) {
        if (id != null && !id.trim().isEmpty()) {
            id = id.trim();
            if (plotters.contains(id)) {
                for (final MetricsPlotter plotter : plotters) {
                    if (plotter.equals(id)) {
                        return new MetricsPlotter(plotter);
                    }
                }
            } else {
                final MetricsPlotter plotter = new MetricsPlotter(id);
                plotters.add(plotter);
                return plotter;
            }
        }
        return null;
    }

    public String[] getPlotterIds() {
        final Set<String> ids = new HashSet<>();
        for (final MetricsPlotter plotter : plotters) {
            ids.add(plotter.getId());
        }
        return ids.toArray(new String[ids.size()]);
    }

    public boolean removePlotter(final MetricsPlotter plotter) {
        return plotters.remove(plotter);
    }

    public boolean updatePlotter(final MetricsPlotter plotter) {
        final boolean r = removePlotter(plotter);
        if (r) {
            plotters.add(plotter);
        }
        return r;
    }

}
