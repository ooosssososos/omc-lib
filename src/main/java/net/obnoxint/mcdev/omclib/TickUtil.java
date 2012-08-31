package net.obnoxint.mcdev.omclib;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

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

    private final class ServerTickTask implements Runnable {

        @Override
        public void run() {
            Bukkit.getPluginManager().callEvent(new ServerTickEvent(tick));
        }

    }

    private final class TickTask implements Runnable {

        @Override
        public void run() {
            tick++;
        }

    }

    private final OmcLibPlugin plugin;
    private final TickTask tickTask;
    private final long startedDate;

    private ServerTickTask serverTickTask;
    private int serverTickTaskId = -1;
    private long tick = -1;

    TickUtil() {
        this.plugin = OmcLibPlugin.getInstance();
        this.startedDate = System.currentTimeMillis();
        this.tickTask = new TickTask();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, tickTask, 0, 1);
        registerServerTickTask();
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
            serverTickTaskId = -1;
        }
    }

    private void registerServerTickTask() {
        if (serverTickTaskId == -1) {
            serverTickTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, serverTickTask, 0, 1);
        }
    }

}
