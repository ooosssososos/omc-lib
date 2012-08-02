package net.obnoxint.mcdev.omclib;

import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public enum OmcLibPermission {

    COMMAND(".command", PermissionDefault.OP),
    COMMAND_DEBUG(".command.debug", PermissionDefault.OP, COMMAND),
    COMPENDIUM(".compendium", PermissionDefault.OP),
    COMPENDIUM_READ_TOPIC(".compendium.topic", PermissionDefault.OP, COMPENDIUM);

    private static final String TOP_NODE_NAME = "omc-lib";

    private static final Permission TOP_NODE = new Permission(TOP_NODE_NAME);

    static {
        Bukkit.getPluginManager().addPermission(TOP_NODE);
    }

    public static Permission getTopNode() {
        return TOP_NODE;
    }

    private final String name;
    private final PermissionDefault permissionDefault;
    private final Permission parent;
    private final Permission permission;

    private OmcLibPermission(final String name, final PermissionDefault permissionDefault) {
        this(name, permissionDefault, null);
    }

    private OmcLibPermission(final String name, final PermissionDefault permissionDefault, final OmcLibPermission parent) {
        final Permission perm = new Permission(name, permissionDefault);
        final Permission par = (parent == null) ? getTopNode() : parent.permission;
        perm.addParent(par, true);
        Bukkit.getPluginManager().addPermission(perm);
        this.name = name;
        this.permission = perm;
        this.permissionDefault = permissionDefault;
        this.parent = par;
    }

    public String getName() {
        return name;
    }

    public Permission getParent() {
        return parent;
    }

    public Permission getPermission() {
        return permission;
    }

    public PermissionDefault getPermissionDefault() {
        return permissionDefault;
    }

}
