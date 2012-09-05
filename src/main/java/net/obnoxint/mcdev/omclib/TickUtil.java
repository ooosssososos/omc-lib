package net.obnoxint.mcdev.omclib;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public final class TickUtil {

    public static final class ServerTickEvent extends Event {

        private static final HandlerList handlers = new HandlerList();

        public static HandlerList getHandlerList() {
            return handlers;
        }

        private final long date;
        private final long tick;

        public ServerTickEvent(final long tick) {
            this.tick = tick;
            this.date = System.currentTimeMillis();
        }

        public long getDate() {
            return date;
        }

        @Override
        public HandlerList getHandlers() {
            return handlers;
        }

        public long getTick() {
            return tick;
        }

    }

    public final class TPSMonitor implements Listener {

        private static final int TPSHISTORY_QUEUE_SIZE = 24000; // 20 minutes at perfect tick rate

        private final Queue<Float> tpsHistory = new LinkedList<>();

        private TPSMonitor() {
            Bukkit.getPluginManager().registerEvents(this, plugin);
        }

        public float getAverage() {
            return getAverage(tpsHistory.size());
        }

        public float getAverage(final int entries) {
            if (entries < 1) {
                throw new IllegalArgumentException();
            }
            float r = 0f;
            final int e = (entries > TPSHISTORY_QUEUE_SIZE || entries > tpsHistory.size()) ? tpsHistory.size() : entries;
            final Iterator<Float> it = tpsHistory.iterator();
            for (int i = 0; i < e; i++) {
                r += it.next();
            }
            return r / e;
        }

        @EventHandler(priority = EventPriority.LOWEST)
        private void onTick(final ServerTickEvent event) {
            if (lastTick == -1) { // ignore first tick
                return;
            }
            if (tick == 0) { // assume perfect tick rate on second tick
                tpsHistory.add(100f);
            } else {
                tpsHistory.add((float) (5000 / (System.currentTimeMillis() - lastTick)));
            }
            if (tpsHistory.size() > TPSHISTORY_QUEUE_SIZE) { // cut the queue if it gets too big
                tpsHistory.poll();
            }
        }

    }

    private final class ServerTickTask implements Runnable {

        @Override
        public void run() {
            tick++;
            Bukkit.getPluginManager().callEvent(new ServerTickEvent(tick));
            lastTick = System.currentTimeMillis();
        }

    }

    private final OmcLibPlugin plugin;
    private final long startedDate;

    private final TPSMonitor monitor;
    private ServerTickTask serverTickTask;
    private int serverTickTaskId = -1;
    private long tick = -1;
    private long lastTick = -1;

    TickUtil() {
        this.plugin = OmcLibPlugin.getInstance();
        this.startedDate = System.currentTimeMillis();
        this.monitor = new TPSMonitor();
        registerServerTickTask();
    }

    public long getLastTick() {
        return lastTick;
    }

    public TPSMonitor getMonitor() {
        return monitor;
    }

    public long getStartedDate() {
        return startedDate;
    }

    public long getTick() {
        return tick;
    }

    void unregisterServerTickTask() {
        if (serverTickTaskId != -1) {
            Bukkit.getScheduler().cancelTask(serverTickTaskId);
            serverTickTask = null;
            serverTickTaskId = -1;
        }
    }

    private void registerServerTickTask() {
        if (serverTickTaskId == -1) {
            serverTickTask = new ServerTickTask();
            serverTickTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, serverTickTask, 0, 1);
        }
    }
}
