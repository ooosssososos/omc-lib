package net.obnoxint.mcdev.omclib;

import java.util.Map;
import java.util.TreeMap;

import net.obnoxint.mcdev.omclib.command.DebugCommandHandler;
import net.obnoxint.mcdev.omclib.command.FeatureCommandHandler;
import net.obnoxint.mcdev.omclib.command.OmcCommandHandler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

final class OmcCommandExecutor implements CommandExecutor {

    private enum DefaultHandler {
        DEBUG(DebugCommandHandler.class),
        FEATURE(FeatureCommandHandler.class);

        private final Class<? extends OmcCommandHandler> clazz;

        private DefaultHandler(final Class<? extends OmcCommandHandler> clazz) {
            this.clazz = clazz;
        }
    }

    private final Map<String, OmcCommandHandler> handlers = new TreeMap<>();

    OmcCommandExecutor() {
        for (final DefaultHandler h : DefaultHandler.values()) {
            try {
                addHandler(h.clazz.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {}
        }
    }

    public void addHandler(final OmcCommandHandler handler) {
        final String id = handler.getId();
        if (!handlers.containsKey(id)) {
            handlers.put(id, handler);
        }
    }

    public Map<String, OmcCommandHandler> getHandlers() {
        return new TreeMap<>(handlers);
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        final Permission perm = OmcLibPermission.COMMAND.getPermission();
        if (sender.hasPermission(perm)) {
            if (args.length == 0) {
                final StringBuilder sb = new StringBuilder("Available commands:");
                for (final String s : handlers.keySet()) {
                    sb.append(" " + s);
                }
                sender.sendMessage(sb.toString());
            } else {
                final String id = args[0].toLowerCase();
                if (handlers.containsKey(id)) {
                    final String[] args2 = new String[args.length - 1];
                    if (args2.length > 0) {
                        System.arraycopy(args, 1, args2, 0, args2.length);
                    }
                    final OmcCommandHandler h = handlers.get(id);
                    final boolean r = h.handleCommand(sender, args2);
                    if (!r) {
                        sender.sendMessage(id + " " + h.getUsage());
                    }
                } else {
                    sender.sendMessage("Handler not available: " + id);
                }
            }
        } else {
            sender.sendMessage("Permission required: " + perm.getName());
        }
        return true;
    }

}
