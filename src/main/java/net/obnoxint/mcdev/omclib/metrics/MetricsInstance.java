package net.obnoxint.mcdev.omclib.metrics;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.plugin.Plugin;

public final class MetricsInstance implements Serializable {

    private static final long serialVersionUID = 7748827513427590997L;

    private final String pluginName;
    private volatile Set<MetricsGraph> graphs = new HashSet<>();

    MetricsInstance(final MetricsInstance instance) {
        this.pluginName = instance.pluginName;
        for (final MetricsGraph mg : instance.graphs) {
            this.graphs.add(new MetricsGraph(mg));
        }
    }

    MetricsInstance(final Plugin plugin) {
        if (plugin == null) {
            throw new IllegalArgumentException("Null reference not permitted.");
        }
        this.pluginName = plugin.getName();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj != null) {
            if (obj instanceof String) {
                return ((String) obj).equals(pluginName);
            } else if (obj instanceof Plugin) {
                return equals(((Plugin) obj).getName());
            } else if (obj instanceof MetricsInstance) {
                return equals(((MetricsInstance) obj).pluginName);
            }
        }
        return false;
    }

    public MetricsGraph getDefaultGraph() {
        return getGraph(MetricsGraph.DEFAULT);
    }

    public MetricsGraph getGraph(String name) {
        if (name != null && !name.trim().isEmpty()) {
            name = name.trim();
            if (graphs.contains(name)) {
                for (final MetricsGraph graph : graphs) {
                    if (graph.equals(name)) {
                        return new MetricsGraph(graph);
                    }
                }
            } else {
                final MetricsGraph graph = new MetricsGraph(name);
                graphs.add(graph);
                return graph;
            }
        }
        return null;
    }

    public String[] getGraphNames() {
        final Set<String> names = new HashSet<>();
        for (final MetricsGraph graph : graphs) {
            names.add(graph.getName());
        }
        return names.toArray(new String[names.size()]);
    }

    public boolean removeGraph(final MetricsGraph graph) {
        return graphs.remove(graph);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(pluginName + "=");
        final String[] g = getGraphNames();
        for (int i = 0; i < g.length; i++) {
            if (i == 0) {
                sb.append("{");
            }
            sb.append(getGraph(g[i]).toString());
            if (i == g.length - 1) {
                sb.append("}");
            } else {
                sb.append(";");
            }
        }
        return sb.toString();
    }

    public boolean updateGraph(final MetricsGraph graph) {
        final boolean r = removeGraph(graph);
        if (r) {
            graphs.add(graph);
        }
        return r;
    }

    String getPluginName() {
        return pluginName;
    }

    void putGraphs(final MetricsGraph[] graphs) {
        synchronized (this.graphs) {
            for (final MetricsGraph graph : graphs) {
                if (this.graphs.contains(graph)) {
                    this.graphs.remove(graph);
                }
                this.graphs.add(graph);
            }
        }
    }

}
