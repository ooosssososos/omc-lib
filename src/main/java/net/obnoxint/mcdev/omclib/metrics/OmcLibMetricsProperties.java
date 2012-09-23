package net.obnoxint.mcdev.omclib.metrics;

import java.util.Properties;

import net.obnoxint.mcdev.feature.FeatureProperties;

public final class OmcLibMetricsProperties extends FeatureProperties {

    private static final int PROPERTY_MIN_REPORT_INTERVAL_MINUTES = 10;

    private static final String PROPERTY_NAME_ALLOW_REPORT = "allowReport";
    private static final String PROPERTY_NAME_CREATE_REPORT_DUMP = "createReportDump";
    private static final String PROPERTY_NAME_REPORT_INTERVAL_MINUTES = "reportIntervalMinutes";

    private boolean allowReport = true;
    private boolean createReportDump = true;
    private int reportIntervalMinutes = 15;

    OmcLibMetricsProperties(final OmcLibMetricsFeature feature) {
        super(feature);
    }

    public int getReportIntervalMinutes() {
        return reportIntervalMinutes;
    }

    public boolean isAllowReport() {
        return allowReport;
    }

    public boolean isCreateReportDump() {
        return createReportDump;
    }

    public void setAllowReport(final boolean allowReport) {
        if (this.allowReport != allowReport) {
            this.allowReport = allowReport;
            setDirty();
        }
    }

    @Override
    protected void onFileCreated() {
        onStore();
    }

    @Override
    protected void onLoaded() {
        final Properties p = getProperties();

        allowReport = Boolean.valueOf(p.getProperty(PROPERTY_NAME_ALLOW_REPORT));
        createReportDump = Boolean.valueOf(p.getProperty(PROPERTY_NAME_CREATE_REPORT_DUMP));
        reportIntervalMinutes = Integer.valueOf(p.getProperty(PROPERTY_NAME_REPORT_INTERVAL_MINUTES));
    }

    @Override
    protected void onStore() {
        final Properties p = getProperties();

        p.setProperty(PROPERTY_NAME_ALLOW_REPORT, String.valueOf(allowReport));
        p.setProperty(PROPERTY_NAME_CREATE_REPORT_DUMP, String.valueOf(createReportDump));
        p.setProperty(PROPERTY_NAME_REPORT_INTERVAL_MINUTES, String.valueOf(reportIntervalMinutes));
    }

    void setCreateReportDump(final boolean createReportDump) {
        if (this.createReportDump != createReportDump) {
            this.createReportDump = createReportDump;
            setDirty();
        }
    }

    void setReportIntervalMinutes(final int reportIntervalMinutes) {
        if (this.reportIntervalMinutes != reportIntervalMinutes && reportIntervalMinutes >= PROPERTY_MIN_REPORT_INTERVAL_MINUTES) {
            this.reportIntervalMinutes = reportIntervalMinutes;
            setDirty();
        }
    }

}
