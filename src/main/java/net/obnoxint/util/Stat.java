package net.obnoxint.util;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public abstract class Stat implements Serializable {

    public static class SimpleStat extends Stat {

        private static final long serialVersionUID = -3702196029599532933L;

        public SimpleStat(Stat stat) {
            super(stat);
        }

        public SimpleStat(String id) {
            super(id);
        }

        public SimpleStat(String id, float percentage) {
            super(id, percentage);
        }

        public SimpleStat(String id, long balance) {
            super(id, balance);
        }

    }

    public static interface StatListener {

        void onBalanceChanged(Stat stat, long previousBalance);

    }

    private static final long serialVersionUID = 280113680395112996L;

    protected static long getPercentageLong(float percentage) {
        return (long) ((Long.MAX_VALUE / 100f) * ((percentage > 100f) ? 100f : ((percentage < 0f) ? 0f : percentage)));
    }

    private volatile long balance = 0;

    private final String id;

    private final long initialBalance;

    private final transient Set<StatListener> listeners;

    public Stat(final Stat stat) {
        this(stat.id, stat.balance, new HashSet<>(stat.listeners), stat.initialBalance);
    }

    public Stat(final String id) {
        this(id, 0L);
    }

    public Stat(final String id, final float percentage) {
        this(id, getPercentageLong(percentage));
    }

    public Stat(final String id, final long balance) {
        this(id, balance, null, balance);
    }

    private Stat(final String id, final long balance, final Set<StatListener> listeners, final long initialBalance) {
        this.id = id;
        this.balance = balance;
        this.listeners = (listeners == null) ? new HashSet<StatListener>() : listeners;
        this.initialBalance = initialBalance;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Stat) {
            return ((Stat) obj).id.equals(id);
        } else if (obj instanceof String) {
            return ((String) obj).equals(id);
        }
        return false;
    }

    public final long getBalance() {
        return balance;
    }

    public final String getId() {
        return id;
    }

    public final long getInitialBalance() {
        return initialBalance;
    }

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

    public void reset() {
        balance = initialBalance;
    }

    public void setBalance(final long balance) {
        long previousBalance = this.balance;
        this.balance = balance;
        for (StatListener l : getListeners()) {
            l.onBalanceChanged(this, previousBalance);
        }
    }

    public void setPercentage(final float percentage) {
        setBalance(getPercentageLong(percentage));

    }

    protected void addListener(final StatListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }

    protected Set<? extends StatListener> getListeners() {
        return new HashSet<>(listeners);
    }

    protected void removeListener(final StatListener listener) {
        listeners.remove(listener);
    }

}
