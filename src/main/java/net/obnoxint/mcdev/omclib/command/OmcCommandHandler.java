package net.obnoxint.mcdev.omclib.command;

import org.bukkit.command.CommandSender;

public abstract class OmcCommandHandler {

    private final String id;
    private final String description;
    private final String usage;

    protected OmcCommandHandler(final String id, final String description, final String usage) {
        this.id = id;
        this.description = description;
        this.usage = usage;
    }

    public final String getDescription() {
        return description;
    }

    public final String getId() {
        return id;
    }

    public final String getUsage() {
        return usage;
    }

    public abstract boolean handleCommand(CommandSender sender, String[] args);

}
