package net.obnoxint.util;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * This class is a simple and abstract organizational unit utilizable for statistical purposes.
 * </p>
 * <p>
 * Its value (balance) is backed by a volatile long field. The balance can be negative.<br>
 * An instance of Stat can also be used to store percentual values. In this case Long.MAX_VALUE represents 100% and 0L represents 0%.
 * </p>
 */
public abstract class Stat implements Serializable {

    /**
     * A simple implementation of Stat.
     */
    public static class SimpleStat extends Stat {

        private static final long serialVersionUID = -3702196029599532933L;

        public SimpleStat(final Stat stat) {
            super(stat);
        }

        public SimpleStat(final String id) {
            super(id);
        }

        public SimpleStat(final String id, final float percentage) {
            super(id, percentage);
        }

        public SimpleStat(final String id, final long balance) {
            super(id, balance);
        }

    }

    /**
     * Classes which implement this interface can be registered with one or more instances of Stat in order to be notified about balance modifications.
     */
    public static interface StatListener {

        /**
         * Called by a Stat if a modifaction of the balance occurs. The new balance can be acquired by calling the {@link Stat#getBalance()} method.
         * 
         * @param stat the calling Stat.
         * @param previousBalance the previous balance of the calling Stat.
         */
        void onBalanceChanged(Stat stat, long previousBalance);

    }

    private static final long serialVersionUID = 280113680395112996L;

    protected static long getPercentageLong(final float percentage) {
        return (long) ((Long.MAX_VALUE / 100f) * ((percentage > 100f) ? 100f : ((percentage < 0f) ? 0f : percentage)));
    }

    private volatile long balance = 0;

    private final String id;
    private final long initialBalance;
    private final transient Set<StatListener> listeners;

    /**
     * Creates a clone of the given Stat.
     * 
     * @param stat the Stat.
     */
    public Stat(final Stat stat) {
        this(stat.id, stat.balance, new HashSet<>(stat.listeners), stat.initialBalance);
    }

    /**
     * Creates a new instance with the initial balance of 0.
     * 
     * @param id the id.
     */
    public Stat(final String id) {
        this(id, 0L);
    }

    /**
     * Creates a new instance with a non-decimal percentual (long) representation of the given percentage.
     * 
     * @param id the id.
     * @param percentage the percentage.
     */
    public Stat(final String id, final float percentage) {
        this(id, getPercentageLong(percentage));
    }

    /**
     * Creates a new instance with the given balance.
     * 
     * @param id the id.
     * @param balance the balance.
     */
    public Stat(final String id, final long balance) {
        this(id, balance, null, balance);
    }

    private Stat(final String id, final long balance, final Set<StatListener> listeners, final long initialBalance) {
        this.id = id;
        this.balance = balance;
        this.listeners = (listeners == null) ? new HashSet<StatListener>() : listeners;
        this.initialBalance = initialBalance;
    }

    /**
     * @return true if <code>
     * ((Stat)obj).getId().equals(getId())
     * </code> or if <code>
     * ((String)obj).equals(getId())
     * </code>.
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Stat) {
            return ((Stat) obj).id.equals(id);
        } else if (obj instanceof String) {
            return ((String) obj).equals(id);
        }
        return false;
    }

    /**
     * @return the balance.
     */
    public final long getBalance() {
        return balance;
    }

    /**
     * @return the id.
     */
    public final String getId() {
        return id;
    }

    /**
     * @return the balance used to initialize this instance.
     */
    public final long getInitialBalance() {
        return initialBalance;
    }

    /**
     * @return a percentual representation of the balance. Always returns 0f if the balance is <= 0L.
     */
    public final float getPercentage() {
        if (balance <= 0L) {
            return 0f;
        } else if (balance == Long.MAX_VALUE) {
            return 100f;
        } else {
            return ((balance / Long.MAX_VALUE) * 100f);
        }
    }

    /**
     * Modifies the balance, preventing over-/underflow.
     * 
     * @param balance the modification value.
     * @return true if an over- or underflow was prevented.
     */
    public boolean modifyBalance(final long balance) {
        long l = this.balance;
        boolean r = false;
        if (balance < 0 && (l - balance > l)) { // underflow-check
            l = Long.MIN_VALUE;
            r = true;
        } else if (balance > 0 && (l + balance < l)) { // overflow-check
            l = Long.MAX_VALUE;
            r = true;
        } else {
            l += balance;
        }
        setBalance(l);
        return r;
    }

    /**
     * Resets the balance to the balance used to initialize this instance.
     */
    public void reset() {
        balance = initialBalance;
    }

    /**
     * @param balance the new balance.
     */
    public void setBalance(final long balance) {
        final long previousBalance = this.balance;
        this.balance = balance;
        for (final StatListener l : getListeners()) {
            l.onBalanceChanged(this, previousBalance);
        }
    }

    /**
     * @param percentage the new percentual balance.
     */
    public void setPercentage(final float percentage) {
        setBalance(getPercentageLong(percentage));

    }

    /**
     * @param listener an instance of {@link StatListener} which will be notfied about balance modifications.
     */
    protected void addListener(final StatListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }

    /**
     * @return a Set of all registered {@link StatListener}s.
     */
    protected Set<? extends StatListener> getListeners() {
        return new HashSet<>(listeners);
    }

    /**
     * Unregisters a {@link StatListener}. It will no longer be notified about balance modifications.
     * 
     * @param listener the {@link StatListener}.
     */
    protected void removeListener(final StatListener listener) {
        listeners.remove(listener);
    }

}
