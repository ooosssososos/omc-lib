package net.obnoxint.util;

import java.io.Serializable;
import java.util.UUID;

public final class DelayMeasure implements Serializable {

    private static final long serialVersionUID = 4180765053110711632L;

    private boolean beganMeasuring = false;
    private long beginDate;
    private long beginNanos;
    private boolean endedMeasuring = false;
    private long endDate;
    private long endNanos;

    private final String id;

    public DelayMeasure() {
        this(UUID.randomUUID().toString());
    }

    public DelayMeasure(final DelayMeasure delayMeasure) {
        this(delayMeasure, delayMeasure.id);
    }

    public DelayMeasure(final DelayMeasure delayMeasure, final String newId) {
        beganMeasuring = delayMeasure.beganMeasuring;
        beginDate = delayMeasure.beginDate;
        beginNanos = delayMeasure.beginNanos;
        endDate = delayMeasure.endDate;
        endedMeasuring = delayMeasure.endedMeasuring;
        endNanos = delayMeasure.endNanos;
        id = (newId == null || newId.isEmpty()) ? UUID.randomUUID().toString() : newId;
    }

    public DelayMeasure(final String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.id = id;
    }

    public boolean beganMeasuring() {
        return beganMeasuring;
    }

    public void begin() {
        if (!beganMeasuring()) {
            setBeginNanos();
            setBeganMeasuring();
            setBeginDate();
        }
    }

    public void end() {
        if (beganMeasuring() && !endedMeasuring()) {
            setEndDate();
            setEndedMeasuring();
            setEndNanos();
        }
    }

    public boolean endedMeasuring() {
        return endedMeasuring;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj != null) {
            if (obj instanceof String) {
                return ((String) obj).equals(id);
            } else if (obj instanceof DelayMeasure) {
                return ((DelayMeasure) obj).id.equals(id);
            }
        }
        return false;
    }

    public long getBeginDate() {
        return beginDate;
    }

    public long getBeginNanos() {
        return beginNanos;
    }

    public long getDelay() {
        return getEndDate() - getBeginDate();
    }

    public long getDelayNanos() {
        return getEndNanos() - getBeginNanos();
    }

    public long getEndDate() {
        return endDate;
    }

    public long getEndNanos() {
        return endNanos;
    }

    public String getId() {
        return id;
    }

    private void setBeganMeasuring() {
        beganMeasuring = true;
    }

    private void setBeginDate() {
        beginDate = System.currentTimeMillis();
    }

    private void setBeginNanos() {
        beginNanos = System.nanoTime();
    }

    private void setEndDate() {
        endDate = System.currentTimeMillis();
    }

    private void setEndedMeasuring() {
        endedMeasuring = true;
    }

    private void setEndNanos() {
        endNanos = System.nanoTime();
    }

}