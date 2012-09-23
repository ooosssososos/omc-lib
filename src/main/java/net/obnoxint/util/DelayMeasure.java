package net.obnoxint.util;

import java.io.Serializable;
import java.util.UUID;

/**
 * <p>
 * The DelayMeasure class is useful for recording the delay between the beginning and the finishing of many similar processes or when the execution environment must be stopped
 * during the process.
 * </p>
 * <p>
 * When beginning a process the {@link #begin()} method has to be called. After calling the {@link #end()} method the {@link #getDelay()} and {@link #getDelayNanos()} methods can
 * be used to acquire the delay between the beginning and the finishing of the process.
 * </p>
 * <p>
 * An instance of this class can not be used again.
 * </p>
 */
public final class DelayMeasure implements Serializable {

    private static final long serialVersionUID = 4180765053110711632L;

    private boolean beganMeasuring = false;
    private long beginDate;
    private long beginNanos;
    private boolean endedMeasuring = false;
    private long endDate;
    private long endNanos;

    private final String id;

    /**
     * Creates a new instance and sets the Id to a random UUID. 
     */
    public DelayMeasure() {
        this(UUID.randomUUID().toString());
    }

    /**
     * Creates a clone of another instance.
     * 
     * @param delayMeasure the instance to clone.
     */
    public DelayMeasure(final DelayMeasure delayMeasure) {
        this(delayMeasure, delayMeasure.id);
    }

    /**
     * Creates a new instance based on an existing instance by copying its contents and assigning a new Id.
     * 
     * @param delayMeasure the instance to copy.
     * @param newId the new Id.
     */
    public DelayMeasure(final DelayMeasure delayMeasure, final String newId) {
        beganMeasuring = delayMeasure.beganMeasuring;
        beginDate = delayMeasure.beginDate;
        beginNanos = delayMeasure.beginNanos;
        endDate = delayMeasure.endDate;
        endedMeasuring = delayMeasure.endedMeasuring;
        endNanos = delayMeasure.endNanos;
        id = (newId == null || newId.isEmpty()) ? UUID.randomUUID().toString() : newId;
    }

    /**
     * Creates a new instance with the given Id. An IllegalArgumentException will be thrown if <i>id</i> is null.
     * 
     * @param id the Id.
     */
    public DelayMeasure(final String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.id = id;
    }

    /**
     * @return true if the {@link #begin()} was called before.
     */
    public boolean beganMeasuring() {
        return beganMeasuring;
    }

    /**
     * Begins measuring.
     */
    public void begin() {
        if (!beganMeasuring()) {
            setBeginNanos();
            setBeganMeasuring();
            setBeginDate();
        }
    }

    /**
     * Ends measuring.
     */
    public void end() {
        if (beganMeasuring() && !endedMeasuring()) {
            setEndDate();
            setEndedMeasuring();
            setEndNanos();
        }
    }

    /**
     * @return true if the {@link #end()} was called before.
     */
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

    /**
     * @return the date when the {@link #begin()} method was called.
     */
    public long getBeginDate() {
        return beginDate;
    }

    /**
     * @return the nanos when the {@link #begin()} method was called.
     */
    public long getBeginNanos() {
        return beginNanos;
    }

    /**
     * @return the delay between calling the {@link #begin()} and the {@link #end()} methods.
     */
    public long getDelay() {
        return getEndDate() - getBeginDate();
    }

    /**
     * @return the delay in nano seconds between calling the {@link #begin()} and the {@link #end()} methods.
     */
    public long getDelayNanos() {
        return getEndNanos() - getBeginNanos();
    }

    /**
     * @return the date when the {@link #end()} method was called.
     */
    public long getEndDate() {
        return endDate;
    }

    /**
     * @return the nanos when the {@link #end()} method was called.
     */
    public long getEndNanos() {
        return endNanos;
    }

    /**
     * @return the Id.
     */
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