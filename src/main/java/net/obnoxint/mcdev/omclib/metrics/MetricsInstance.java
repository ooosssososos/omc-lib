package net.obnoxint.mcdev.omclib.metrics;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.plugin.Plugin;

public final class MetricsInstance implements Serializable {

    private static final long serialVersionUID = 9125543150773844346L;

    private final String pluginName;
    private final Map<String, MetricsGraph> graphs = new HashMap<>();

    MetricsInstance(final MetricsInstance instance) {
        this.pluginName = instance.pluginName;
        for (final String name : instance.graphs.keySet()) {
            this.graphs.put(name, new MetricsGraph(instance.graphs.get(name)));
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
            if (graphs.containsKey(name)) {
                return new MetricsGraph(graphs.get(name));
            } else {
                final MetricsGraph graph = new MetricsGraph(name);
                graphs.put(name, graph);
                return graph;
            }
        }
        return null;
    }

    public String[] getGraphNames() {
        final int l = graphs.size();
        return graphs.keySet().toArray(new String[l]);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((graphs == null) ? 0 : graphs.hashCode());
        result = prime * result + ((pluginName == null) ? 0 : pluginName.hashCode());
        return result;
    }

    public boolean removeGraph(final MetricsGraph graph) {
        return graphs.remove(graph.getName()) != null;
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
            graphs.put(graph.getName(), new MetricsGraph(graph));
        }
        return r;
    }

    String getPluginName() {
        return pluginName;
    }

    void putGraphs(final MetricsGraph[] graphs) {
        synchronized (this.graphs) {
            for (final MetricsGraph graph : graphs) {
                final String name = graph.getName();
                if (this.graphs.containsKey(name)) {
                    this.graphs.remove(name);
                }
                this.graphs.put(name, graph);
            }
        }
    }

}
