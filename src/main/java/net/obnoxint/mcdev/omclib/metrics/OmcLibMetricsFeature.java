package net.obnoxint.mcdev.omclib.metrics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import net.obnoxint.mcdev.feature.Feature;
import net.obnoxint.mcdev.omclib.ImplementedFeature;
import net.obnoxint.mcdev.omclib.OmcLibPlugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public final class OmcLibMetricsFeature implements Feature {

    private static final String FEATURE_FOLDER_NAME = "metrics";
    private static final String INSTANCE_FILE_EXTENSION = ".dat";
    private static final long TASK_DELAY = 0;
    private static final long TASK_PERIOD = 1200;

    private final MetricsAdapter adapter;
    private final OmcLibMetricsProperties properties;
    private final Map<String, MetricsInstance> metricsInstances = new HashMap<>();

    private boolean active = false;
    private int taskId = -1;

    private File featureFolder = null;

    public OmcLibMetricsFeature() {
        this.adapter = new MetricsAdapter(this);
        this.properties = new OmcLibMetricsProperties(this);
    }

    @Override
    public String getFeatureName() {
        return ImplementedFeature.METRICS.getName();
    }

    @Override
    public Plugin getFeaturePlugin() {
        return OmcLibPlugin.getInstance();
    }

    @Override
    public OmcLibMetricsProperties getFeatureProperties() {
        return properties;
    }

    public String getGuid() {
        return adapter.getGuid();
    }

    public long getLastReportDate() {
        return adapter.getLastReportDate();
    }

    public synchronized MetricsInstance getMetricsInstance(final Plugin plugin) {
        final String n = plugin.getName();
        if (!metricsInstances.containsKey(n)) {
            loadInstanceFile(plugin);
        }
        return metricsInstances.get(n);
    }

    @Override
    public boolean isFeatureActive() {
        return active;
    }

    @Override
    public void setFeatureActive(final boolean active) {
        if (this.active != active) {
            if (active) {
                properties.load();
                taskId = Bukkit.getScheduler().scheduleAsyncRepeatingTask(getFeaturePlugin(), adapter, TASK_DELAY, TASK_PERIOD);
            } else {
                Bukkit.getScheduler().cancelTask(taskId);
                try {
                    adapter.report(true);
                } catch (final Exception e) {
                    OmcLibPlugin.getInstance().getLogger().warning(getFeatureName() + ": An exception was thrown while sending a report: " + e.getMessage());
                }
                properties.store();
                storeAllInstanceFiles();
            }
            this.active = active;
        }
    }

    File getFeatureFolder() {
        if (featureFolder == null) {
            featureFolder = new File(getFeaturePlugin().getDataFolder(), FEATURE_FOLDER_NAME);
            if (!featureFolder.exists()) {
                featureFolder.mkdirs();
            }
        }
        return featureFolder;
    }

    synchronized Map<String, MetricsInstance> getMetricsInstances() {
        return new HashMap<>(metricsInstances);
    }

    private void loadInstanceFile(final Plugin plugin) {
        final String n = plugin.getName();
        final File f = new File(getFeatureFolder(), n + INSTANCE_FILE_EXTENSION);
        if (f.exists()) {
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(new GZIPInputStream(new FileInputStream(f)));
                metricsInstances.put(n, (MetricsInstance) ois.readObject());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                ois.close();
            } catch (NullPointerException | IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                f.createNewFile();
            } catch (final IOException e) {
                e.printStackTrace();
            }
            metricsInstances.put(n, new MetricsInstance(plugin));
            storeInstanceFile(n);
        }
    }

    private void storeAllInstanceFiles() {
        for (final String n : metricsInstances.keySet()) {
            storeInstanceFile(n);
        }
    }

    private void storeInstanceFile(final String n) {
        final File f = new File(getFeatureFolder(), n + INSTANCE_FILE_EXTENSION);
        final MetricsInstance mi = new MetricsInstance(metricsInstances.get(n));
        final String[] graphs = mi.getGraphNames();
        for (final String gn : graphs) {
            final MetricsGraph graph = mi.getGraph(gn);
            final String[] plotters = graph.getPlotterIds();
            for (final String pid : plotters) {
                final MetricsPlotter plotter = graph.getPlotter(pid);
                if (!plotter.isPersistent()) {
                    graph.removePlotter(plotter);
                }
            }
            mi.updateGraph(graph);
        }
        try (final ObjectOutputStream ous = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(f)))) {
            ous.writeObject(mi);
            ous.flush();
            ous.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

}
