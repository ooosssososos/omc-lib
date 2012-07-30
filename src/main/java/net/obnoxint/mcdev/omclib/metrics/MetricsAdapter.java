package net.obnoxint.mcdev.omclib.metrics;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import net.obnoxint.mcdev.omclib.OmcLibPlugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

final class MetricsAdapter implements Runnable {

    private static final int REVISION = 5;
    private static final String REPORT_URL = "http://mcstats.org/report/";
    private static final String CUSTOM_DATA_SEPARATOR = "~~";
    private static final String URL_ENCODING = "UTF-8";

    private static final String SEP = "=";
    private static final String VAL_SEP = "&";

    private final OmcLibMetricsFeature feature;
    private final String guid;
    private final String serverVersion;

    private final Set<String> alreadyReported = new HashSet<>();

    private long lastReport = Long.MIN_VALUE;
    private Logger dumpLogger = null;

    MetricsAdapter(final OmcLibMetricsFeature feature) {
        this.feature = feature;
        this.guid = OmcLibPlugin.getInstance().getUidProvider().getUID(feature, true).toString();
        this.serverVersion = Bukkit.getVersion();
    }

    @Override
    public void run() {
        try {
            report(false);
        } catch (final Exception e) {
            feature.getFeaturePlugin().getLogger().severe(e.getMessage());
        }
    }

    String getGuid() {
        return guid;
    }

    long getLastReportDate() {
        return lastReport;
    }

    void report(final boolean force) throws Exception {
        if (!feature.isFeatureActive()
                || !feature.getFeatureProperties().isAllowReport()
                || (!force
                        && lastReport > Long.MIN_VALUE
                        && ((System.currentTimeMillis() - lastReport) / 60000) < feature.getFeatureProperties().getReportIntervalMinutes())) {
            return;
        }
        final Set<MetricsInstance> instances = new HashSet<>();
        synchronized (feature) {
            final Map<String, MetricsInstance> m = feature.getMetricsInstances();
            for (final String s : m.keySet()) {
                instances.add(new MetricsInstance(m.get(s)));
            }
        }
        for (final MetricsInstance mi : instances) {
            final String n = mi.getPluginName();
            final Plugin p = Bukkit.getPluginManager().getPlugin(n);
            if (p != null && p.isEnabled()) { // don't let the adapter send data for disabled plugins

                // declare necessary fields
                final URL url;
                final URLConnection urlConnection;
                final OutputStreamWriter osw;
                final BufferedReader br;
                final String response;

                // declare the StringBuilder which contains the URL data and fill it with the basic data
                final StringBuilder urlData = new StringBuilder(toUtf8("guid")).append(SEP).append(toUtf8(guid))
                        .append(VAL_SEP).append(toUtf8("version")).append(SEP).append(toUtf8(p.getDescription().getVersion()))
                        .append(VAL_SEP).append(toUtf8("server")).append(SEP).append(toUtf8(serverVersion))
                        .append(VAL_SEP).append(toUtf8("players")).append(SEP).append(toUtf8(Integer.toString(Bukkit.getServer().getOnlinePlayers().length)))
                        .append(VAL_SEP).append(toUtf8("revision")).append(SEP).append(toUtf8(Integer.toString(REVISION)));

                // imitate Hidendras Metrics ping behaviour
                if (alreadyReported.contains(n)) {
                    urlData.append(VAL_SEP).append(toUtf8("ping")).append(SEP).append(toUtf8("true"));
                } else {
                    alreadyReported.add(n);
                }

                // add the plotter data to the data
                for (final String gn : mi.getGraphNames()) {
                    for (final String pid : mi.getGraph(gn).getPlotterIds()) {
                        final String key = String.format("C%s%s%s%s", CUSTOM_DATA_SEPARATOR, gn, CUSTOM_DATA_SEPARATOR, pid);
                        final String value = Long.toString(mi.getGraph(gn).getPlotter(pid).getBalance()); // TODO: WARNING! Hidendras Metrics might not support Long values!
                        urlData.append(VAL_SEP).append(toUtf8(key)).append(SEP).append(toUtf8(value));
                    }
                }

                // get the stuff kicked out in the world
                url = new URL(REPORT_URL + toUtf8(n));
                urlConnection = getConnection(url);
                urlConnection.setDoOutput(true);
                osw = new OutputStreamWriter(urlConnection.getOutputStream());
                osw.write(urlData.toString());
                osw.flush();
                osw.close();

                // what does the world say about it?
                br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                response = br.readLine();
                br.close();
                if (response == null || response.startsWith("ERR")) {
                    throw new IOException(response);
                } else { // reset the plotters and put them back in the instance
                    final Set<MetricsGraph> graphs = new HashSet<>();
                    for (final String gn : mi.getGraphNames()) {
                        final MetricsGraph graph = new MetricsGraph(mi.getGraph(gn));
                        for (final String pid : graph.getPlotterIds()) {
                            final MetricsPlotter plotter = new MetricsPlotter(graph.getPlotter(pid));
                            if (plotter.isAutoReset()) {
                                plotter.reset();
                                graph.updatePlotter(plotter);
                            }
                        }
                        graphs.add(graph);
                    }
                    feature.getMetricsInstance(p).putGraphs(graphs.toArray(new MetricsGraph[graphs.size()]));
                }
                if (feature.getFeatureProperties().isCreateReportDump()) { // create a dump log if the user wants to
                    getDumpLogger().info(urlData.toString());
                }
            }
        }
        // we're done!
        lastReport = System.currentTimeMillis();
    }

    private URLConnection getConnection(final URL url) throws IOException {
        try {
            Class.forName("mineshafter.MineServer");
            return url.openConnection(Proxy.NO_PROXY);
        } catch (final Exception e) {
            return url.openConnection();
        }
    }

    private Logger getDumpLogger() {
        if (dumpLogger == null) {
            dumpLogger = Logger.getAnonymousLogger();
            try {
                dumpLogger.addHandler(new FileHandler(new File(feature.getFeatureFolder(), "report.txt").getAbsolutePath()));
            } catch (SecurityException | IOException e) {
                e.printStackTrace();
            }
        }
        return dumpLogger;
    }

    private String toUtf8(final String string) throws UnsupportedEncodingException {
        return URLEncoder.encode(string, URL_ENCODING);
    }

}
