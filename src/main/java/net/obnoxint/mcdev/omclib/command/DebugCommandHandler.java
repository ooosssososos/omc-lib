package net.obnoxint.mcdev.omclib.command;

import net.obnoxint.mcdev.omclib.OmcLibFeatureProperties;
import net.obnoxint.mcdev.omclib.OmcLibPermission;
import net.obnoxint.mcdev.omclib.OmcLibPlugin;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

public final class DebugCommandHandler extends OmcCommandHandler {

    private static final String ID = "debug";
    private static final String DESCRIPTION = "Toggles debug-mode.";
    private static final String USAGE = "[[off|on]|[0|1]] or no argument for status";

    public DebugCommandHandler() {
        super(ID, DESCRIPTION, USAGE);
    }

    @Override
    public boolean handleCommand(final CommandSender sender, final String[] args) {
        final Permission perm = OmcLibPermission.COMMAND_DEBUG.getPermission();
        if (sender.hasPermission(perm)) {
            final OmcLibFeatureProperties prop = OmcLibPlugin.getInstance().getFeatureManager().getFeatureProperties();
            if (args.length > 0) {
                final String value = args[0].toLowerCase();
                boolean status;
                if (value.equals("off") || value.equals("0")) {
                    status = false;
                } else if (value.equals("on") || value.equals("1")) {
                    status = true;
                } else {
                    return false;
                }
                prop.setDebugging(status);
            }
            sender.sendMessage("Debugging: " + String.valueOf(prop.isDebugging()));
        } else {
            sender.sendMessage("Permission required: " + perm.getName());
        }
        return true;
    }

}
