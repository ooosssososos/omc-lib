package net.obnoxint.mcdev.omclib.metrics;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public final class MetricsGraph implements Serializable {

    private static final long serialVersionUID = -3445888940253779398L;

    public static final String DEFAULT = "Default";

    private final String name;
    private final Map<String, MetricsPlotter> plotters = new HashMap<>();

    MetricsGraph(final MetricsGraph graph) {
        this.name = graph.name;
        for (final String id : graph.plotters.keySet()) {
            this.plotters.put(id, new MetricsPlotter(graph.plotters.get(id)));
        }
    }

    MetricsGraph(final String name) {
        this.name = name;
    }

    public boolean addPlotter(final MetricsPlotter plotter) {
        final String id = plotter.getId();
        if (!plotters.containsKey(id)) {
            plotters.put(id, plotter);
            return true;
        }
        return false;
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
            if (plotters.containsKey(id)) {
                return new MetricsPlotter(plotters.get(id));
            } else {
                final MetricsPlotter plotter = new MetricsPlotter(id);
                plotters.put(id, plotter);
                return plotter;
            }
        }
        return null;
    }

    public String[] getPlotterIds() {
        final int l = plotters.size();
        return plotters.keySet().toArray(new String[l]);
    }

    public boolean removePlotter(final MetricsPlotter plotter) {
        return plotters.remove(plotter.getId()) != null;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(name + "=");
        final String[] p = getPlotterIds();
        for (int i = 0; i < p.length; i++) {
            if (i == 0) {
                sb.append("[");
            }
            sb.append(getPlotter(p[i]).toString());
            if (i == p.length - 1) {
                sb.append("]");
            } else {
                sb.append(";");
            }
        }
        return sb.toString();
    }

    public boolean updatePlotter(final MetricsPlotter plotter) {
        final boolean r = removePlotter(plotter);
        if (r) {
            plotters.put(plotter.getId(), new MetricsPlotter(plotter));
        }
        return r;
    }
}
