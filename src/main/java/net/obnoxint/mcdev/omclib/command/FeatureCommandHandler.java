package net.obnoxint.mcdev.omclib.command;

import net.obnoxint.mcdev.feature.Feature;
import net.obnoxint.mcdev.omclib.OmcLibPermission;
import net.obnoxint.mcdev.omclib.OmcLibPlugin;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

public final class FeatureCommandHandler extends OmcCommandHandler {

    private static final String ID = "~";
    private static final String DESCRIPTION = "Manage features";
    private static final String USAGE = "[feature] [activate|deactivate|loadproperties|storeproperties] or no arguments for summary";

    public FeatureCommandHandler() {
        super(ID, DESCRIPTION, USAGE);
    }

    @Override
    public boolean handleCommand(final CommandSender sender, final String[] args) {
        final Permission perm = OmcLibPermission.COMMAND_FEATURE.getPermission();
        if (sender.hasPermission(perm)) {
            if (args.length == 0) {
                sender.sendMessage(getSummary());
                return true;
            } else if (args.length == 1) {
                sender.sendMessage(getFeatureSummary(args[0]));
                return true;
            } else if (args.length == 2) {
                final Feature f = OmcLibPlugin.getInstance().getFeatureManager().getFeature(args[0]);
                if (f != null) {
                    switch (args[1]) {
                    case "activate":
                        if (!f.isFeatureActive()) {
                            f.setFeatureActive(true);
                            sender.sendMessage("Feature activated.");
                        } else {
                            sender.sendMessage("Feature is already active.");
                        }
                    break;
                    case "deactivate":
                        if (f.isFeatureActive()) {
                            f.setFeatureActive(false);
                            sender.sendMessage("Feature deactivated.");
                        } else {
                            sender.sendMessage("Feature is already inactive.");
                        }
                    break;
                    case "loadproperties":
                        if (f.getFeatureProperties().isDirty()) {
                            sender.sendMessage("Warning: changes in memory will be overwritten.");
                        }
                        f.getFeatureProperties().loadProperties();
                        sender.sendMessage("Properties loaded from properties file.");
                    break;
                    case "storeproperties":
                        f.getFeatureProperties().storeProperties();
                        sender.sendMessage("Properties stored to properties file.");
                    break;
                    default:
                        return false;
                    }
                    return true;
                } else {
                    sender.sendMessage("Feature not available: " + args[0]);
                }
            }
        }
        return false;
    }

    private String getFeatureSummary(final String name) {
        String r;
        final Feature f = OmcLibPlugin.getInstance().getFeatureManager().getFeature(name);
        if (f == null) {
            r = "Feature not available: " + name;
        } else {
            r = f.getFeatureName() + ": " + ((f.isFeatureActive()) ? "active" : "inactive")
                    + " Owner: " + f.getFeaturePlugin().getName()
                    + " Properties: " + ((f.getFeatureProperties().isDirty()) ? "dirty" : "clean");
        }
        return r;
    }

    private String[] getSummary() {
        final String[] names = OmcLibPlugin.getInstance().getFeatureManager().getFeatureNames();
        final String[] r = new String[names.length];
        for (int i = 0; i < names.length; i++) {
            r[i] = String.valueOf(i + 1) + ". " + getFeatureSummary(names[i]);
        }
        return r;
    }

}
