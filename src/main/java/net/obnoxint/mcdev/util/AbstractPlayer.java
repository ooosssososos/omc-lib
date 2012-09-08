package net.obnoxint.mcdev.util;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Achievement;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.InventoryView.Property;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.map.MapView;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

/**
 * <p>
 * Abstract implementation of the org.bukkit.entity.Player interface.
 * </p>
 * <p>
 * This class stores the name of a player and uses it to maintain a reference to the according Player object.<br>
 * Since this reference can become obsolete when a player disconnects from the server, the {@link #update()} method must be called in order to reacquire a valid reference.
 * </p>
 * 
 * @since bukkit-1.3.1-R2.1
 */
public abstract class AbstractPlayer implements Player {

    private final String playerName;
    private Player player;

    /**
     * Creates a new instance and calls the {@link #update()} method. Therefore the passed reference and the reference maintained by this instance may not be the same.
     * 
     * @param player the player. An IllegalArgumentException will be thrown if <i>player</i> is null.
     */
    public AbstractPlayer(final Player player) {
        if (player == null) {
            throw new IllegalArgumentException();
        }
        this.playerName = player.getName();
        update();
    }

    @Override
    public void abandonConversation(final Conversation conversation) {
        player.abandonConversation(conversation);
    }

    @Override
    public void abandonConversation(final Conversation conversation, final ConversationAbandonedEvent details) {
        player.abandonConversation(conversation, details);
    }

    @Override
    public void acceptConversationInput(final String input) {
        player.acceptConversationInput(input);
    }

    @Override
    public PermissionAttachment addAttachment(final Plugin plugin) {
        return player.addAttachment(plugin);
    }

    @Override
    public PermissionAttachment addAttachment(final Plugin plugin, final int ticks) {
        return player.addAttachment(plugin, ticks);
    }

    @Override
    public PermissionAttachment addAttachment(final Plugin plugin, final String name, final boolean value) {
        return player.addAttachment(plugin, name, value);
    }

    @Override
    public PermissionAttachment addAttachment(final Plugin plugin, final String name, final boolean value, final int ticks) {
        return player.addAttachment(plugin, name, value, ticks);
    }

    @Override
    public boolean addPotionEffect(final PotionEffect effect) {
        return player.addPotionEffect(effect);
    }

    @Override
    public boolean addPotionEffect(final PotionEffect effect, final boolean force) {
        return player.addPotionEffect(effect, force);
    }

    @Override
    public boolean addPotionEffects(final Collection<PotionEffect> effects) {
        return player.addPotionEffects(effects);
    }

    @Override
    public void awardAchievement(final Achievement achievement) {
        player.awardAchievement(achievement);
    }

    @Override
    public boolean beginConversation(final Conversation conversation) {
        return player.beginConversation(conversation);
    }

    @Override
    public boolean canSee(final Player player) {
        return player.canSee(player);
    }

    @Override
    public void chat(final String msg) {
        player.chat(msg);
    }

    @Override
    public void closeInventory() {
        player.closeInventory();
    }

    @Override
    public void damage(final int amount) {
        player.damage(amount);
    }

    @Override
    public void damage(final int amount, final Entity source) {
        player.damage(amount, source);
    }

    @Override
    public boolean eject() {
        return player.eject();
    }

    /**
     * @return true if the given object (String or Player) is equal to this instance.
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj != null) {
            if (obj instanceof Player) {
                return playerName.equals(((Player) obj).getName());
            } else if (obj instanceof String) {
                return playerName.equals((obj));
            }
        }
        return false;
    }

    @Override
    public Collection<PotionEffect> getActivePotionEffects() {
        return player.getActivePotionEffects();
    }

    @Override
    public InetSocketAddress getAddress() {
        return player.getAddress();
    }

    @Override
    public boolean getAllowFlight() {
        return player.getAllowFlight();
    }

    @Override
    public Location getBedSpawnLocation() {
        return player.getBedSpawnLocation();
    }

    @Override
    public Location getCompassTarget() {
        return player.getCompassTarget();
    }

    @Override
    public String getDisplayName() {
        return player.getDisplayName();
    }

    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return player.getEffectivePermissions();
    }

    @Override
    public Inventory getEnderChest() {
        return player.getEnderChest();
    }

    @Override
    public int getEntityId() {
        return getEntityId();
    }

    @Override
    public float getExhaustion() {
        return player.getExhaustion();
    }

    @Override
    public float getExp() {
        return player.getExp();
    }

    @Override
    public int getExpToLevel() {
        return player.getExpToLevel();
    }

    @Override
    public double getEyeHeight() {
        return player.getEyeHeight();
    }

    @Override
    public double getEyeHeight(final boolean ignoreSneaking) {
        return player.getEyeHeight(ignoreSneaking);
    }

    @Override
    public Location getEyeLocation() {
        return player.getEyeLocation();
    }

    @Override
    public float getFallDistance() {
        return player.getFallDistance();
    }

    @Override
    public int getFireTicks() {
        return player.getFireTicks();
    }

    @Override
    public long getFirstPlayed() {
        return player.getFirstPlayed();
    }

    @Override
    public float getFlySpeed() {
        return player.getFlySpeed();
    }

    @Override
    public int getFoodLevel() {
        return player.getFoodLevel();
    }

    @Override
    public GameMode getGameMode() {
        return player.getGameMode();
    }

    @Override
    public int getHealth() {
        return player.getHealth();
    }

    @Override
    public PlayerInventory getInventory() {
        return player.getInventory();
    }

    @Override
    public ItemStack getItemInHand() {
        return player.getItemInHand();
    }

    @Override
    public ItemStack getItemOnCursor() {
        return player.getItemOnCursor();
    }

    @Override
    public Player getKiller() {
        return player.getKiller();
    }

    @Override
    public int getLastDamage() {
        return player.getLastDamage();
    }

    @Override
    public EntityDamageEvent getLastDamageCause() {
        return player.getLastDamageCause();
    }

    @Override
    public long getLastPlayed() {
        return player.getLastPlayed();
    }

    @Override
    public List<Block> getLastTwoTargetBlocks(final HashSet<Byte> transparent, final int maxDistance) {
        return player.getLastTwoTargetBlocks(transparent, maxDistance);
    }

    @Override
    public int getLevel() {
        return player.getLevel();
    }

    @Override
    public List<Block> getLineOfSight(final HashSet<Byte> transparent, final int maxDistance) {
        return player.getLineOfSight(transparent, maxDistance);
    }

    @Override
    public Set<String> getListeningPluginChannels() {
        return player.getListeningPluginChannels();
    }

    @Override
    public Location getLocation() {
        return player.getLocation();
    }

    @Override
    public int getMaxFireTicks() {
        return player.getMaxFireTicks();
    }

    @Override
    public int getMaxHealth() {
        return player.getMaxHealth();
    }

    @Override
    public int getMaximumAir() {
        return player.getMaximumAir();
    }

    @Override
    public int getMaximumNoDamageTicks() {
        return player.getMaximumNoDamageTicks();
    }

    @Override
    public List<MetadataValue> getMetadata(final String metadataKey) {
        return player.getMetadata(metadataKey);
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public List<Entity> getNearbyEntities(final double x, final double y, final double z) {
        return player.getNearbyEntities(x, y, z);
    }

    @Override
    public int getNoDamageTicks() {
        return player.getNoDamageTicks();
    }

    @Override
    public InventoryView getOpenInventory() {
        return player.getOpenInventory();
    }

    @Override
    public Entity getPassenger() {
        return player.getPassenger();
    }

    /**
     * @return the according instance of org.bukkit.entity.Player.
     */
    @Override
    public final Player getPlayer() {
        return player;
    }

    @Override
    public String getPlayerListName() {
        return player.getPlayerListName();
    }

    /**
     * @return the name of the Player.
     */
    public final String getPlayerName() {
        return playerName;
    }

    @Override
    public long getPlayerTime() {
        return player.getPlayerTime();
    }

    @Override
    public long getPlayerTimeOffset() {
        return player.getPlayerTimeOffset();
    }

    @Override
    public int getRemainingAir() {
        return player.getRemainingAir();
    }

    @Override
    public float getSaturation() {
        return player.getSaturation();
    }

    @Override
    public Server getServer() {
        return player.getServer();
    }

    @Override
    public int getSleepTicks() {
        return player.getSleepTicks();
    }

    @Override
    public Block getTargetBlock(final HashSet<Byte> transparent, final int maxDistance) {
        return player.getTargetBlock(transparent, maxDistance);
    }

    @Override
    public int getTicksLived() {
        return player.getTicksLived();
    }

    @Override
    public int getTotalExperience() {
        return player.getTotalExperience();
    }

    @Override
    public EntityType getType() {
        return player.getType();
    }

    @Override
    public UUID getUniqueId() {
        return player.getUniqueId();
    }

    @Override
    public Entity getVehicle() {
        return player.getVehicle();
    }

    @Override
    public Vector getVelocity() {
        return player.getVelocity();
    }

    @Override
    public float getWalkSpeed() {
        return player.getWalkSpeed();
    }

    @Override
    public World getWorld() {
        return player.getWorld();
    }

    @Override
    public void giveExp(final int amount) {
        player.giveExp(amount);
    }

    @Override
    public boolean hasLineOfSight(final Entity other) {
        return player.hasLineOfSight(other);
    }

    @Override
    public boolean hasMetadata(final String metadataKey) {
        return player.hasMetadata(metadataKey);
    }

    @Override
    public boolean hasPermission(final Permission perm) {
        return player.hasPermission(perm);
    }

    @Override
    public boolean hasPermission(final String name) {
        return player.hasPermission(name);
    }

    @Override
    public boolean hasPlayedBefore() {
        return player.hasPlayedBefore();
    }

    @Override
    public boolean hasPotionEffect(final PotionEffectType type) {
        return player.hasPotionEffect(type);
    }

    @Override
    public void hidePlayer(final Player player) {
        player.hidePlayer(player);
    }

    @Override
    public void incrementStatistic(final Statistic statistic) {
        player.incrementStatistic(statistic);
    }

    @Override
    public void incrementStatistic(final Statistic statistic, final int amount) {
        player.incrementStatistic(statistic, amount);
    }

    @Override
    public void incrementStatistic(final Statistic statistic, final Material material) {
        player.incrementStatistic(statistic, material);
    }

    @Override
    public void incrementStatistic(final Statistic statistic, final Material material, final int amount) {
        player.incrementStatistic(statistic, material, amount);
    }

    @Override
    public boolean isBanned() {
        return player.isBanned();
    }

    @Override
    public boolean isBlocking() {
        return player.isBlocking();
    }

    @Override
    public boolean isConversing() {
        return player.isConversing();
    }

    @Override
    public boolean isDead() {
        return player.isDead();
    }

    @Override
    public boolean isEmpty() {
        return player.isEmpty();
    }

    @Override
    public boolean isFlying() {
        return player.isFlying();
    }

    @Override
    public boolean isInsideVehicle() {
        return player.isInsideVehicle();
    }

    @Override
    public boolean isOnline() {
        return player.isOnline();
    }

    @Override
    public boolean isOp() {
        return player.isOp();
    }

    @Override
    public boolean isPermissionSet(final Permission perm) {
        return player.isPermissionSet(perm);
    }

    @Override
    public boolean isPermissionSet(final String name) {
        return player.isPermissionSet(name);
    }

    @Override
    public boolean isPlayerTimeRelative() {
        return player.isPlayerTimeRelative();
    }

    @Override
    public boolean isSleeping() {
        return player.isSleeping();
    }

    @Override
    public boolean isSleepingIgnored() {
        return player.isSleepingIgnored();
    }

    @Override
    public boolean isSneaking() {
        return player.isSneaking();
    }

    @Override
    public boolean isSprinting() {
        return player.isSprinting();
    }

    @Override
    public boolean isValid() {
        return player.isValid();
    }

    @Override
    public boolean isWhitelisted() {
        return player.isWhitelisted();
    }

    @Override
    public void kickPlayer(final String message) {
        player.kickPlayer(message);
    }

    @Override
    public <T extends Projectile> T launchProjectile(final Class<? extends T> projectile) {
        return player.launchProjectile(projectile);
    }

    @Override
    public boolean leaveVehicle() {
        return player.leaveVehicle();
    }

    @Override
    public void loadData() {
        player.loadData();
    }

    @Override
    public InventoryView openEnchanting(final Location location, final boolean force) {
        return player.openEnchanting(location, force);
    }

    @Override
    public InventoryView openInventory(final Inventory inventory) {
        return player.openInventory(inventory);
    }

    @Override
    public void openInventory(final InventoryView inventory) {
        player.openInventory(inventory);
    }

    @Override
    public InventoryView openWorkbench(final Location location, final boolean force) {
        return player.openWorkbench(location, force);
    }

    @Override
    public boolean performCommand(final String command) {
        return player.performCommand(command);
    }

    @Override
    public void playEffect(final EntityEffect type) {
        player.playEffect(type);
    }

    @Override
    public void playEffect(final Location loc, final Effect effect, final int data) {
        player.playEffect(loc, effect, data);
    }

    @Override
    public <T> void playEffect(final Location loc, final Effect effect, final T data) {
        player.playEffect(loc, effect, data);
    }

    @Override
    public void playNote(final Location loc, final byte instrument, final byte note) {
        player.playNote(loc, instrument, note);
    }

    @Override
    public void playNote(final Location loc, final Instrument instrument, final Note note) {
        player.playNote(loc, instrument, note);
    }

    @Override
    public void playSound(final Location location, final Sound sound, final float volume, final float pitch) {
        player.playSound(location, sound, volume, pitch);
    }

    @Override
    public void recalculatePermissions() {
        player.recalculatePermissions();
    }

    @Override
    public void remove() {
        player.remove();
    }

    @Override
    public void removeAttachment(final PermissionAttachment attachment) {
        player.removeAttachment(attachment);
    }

    @Override
    public void removeMetadata(final String metadataKey, final Plugin owningPlugin) {
        player.removeMetadata(metadataKey, owningPlugin);
    }

    @Override
    public void removePotionEffect(final PotionEffectType type) {
        player.removePotionEffect(type);
    }

    @Override
    public void resetPlayerTime() {
        player.resetPlayerTime();
    }

    @Override
    public void saveData() {
        player.saveData();
    }

    @Override
    public void sendBlockChange(final Location loc, final int material, final byte data) {
        player.sendBlockChange(loc, material, data);
    }

    @Override
    public void sendBlockChange(final Location loc, final Material material, final byte data) {
        player.sendBlockChange(loc, material, data);
    }

    @Override
    public boolean sendChunkChange(final Location loc, final int sx, final int sy, final int sz, final byte[] data) {
        return player.sendChunkChange(loc, sx, sy, sz, data);
    }

    @Override
    public void sendMap(final MapView map) {
        player.sendMap(map);
    }

    @Override
    public void sendMessage(final String message) {
        player.sendMessage(message);
    }

    @Override
    public void sendMessage(final String[] messages) {
        player.sendMessage(messages);
    }

    @Override
    public void sendPluginMessage(final Plugin source, final String channel, final byte[] message) {
        player.sendPluginMessage(source, channel, message);
    }

    @Override
    public void sendRawMessage(final String message) {
        player.sendRawMessage(message);
    }

    @Override
    public Map<String, Object> serialize() {
        return player.serialize();
    }

    @Override
    public void setAllowFlight(final boolean flight) {
        player.setAllowFlight(flight);
    }

    @Override
    public void setBanned(final boolean banned) {
        player.setBanned(banned);
    }

    @Override
    public void setBedSpawnLocation(final Location location) {
        player.setBedSpawnLocation(location);
    }

    @Override
    public void setCompassTarget(final Location loc) {
        player.setCompassTarget(loc);
    }

    @Override
    public void setDisplayName(final String name) {
        player.setDisplayName(name);
    }

    @Override
    public void setExhaustion(final float value) {
        player.setExhaustion(value);
    }

    @Override
    public void setExp(final float exp) {
        player.setExp(exp);
    }

    @Override
    public void setFallDistance(final float distance) {
        player.setFallDistance(distance);
    }

    @Override
    public void setFireTicks(final int ticks) {
        player.setFireTicks(ticks);
    }

    @Override
    public void setFlying(final boolean value) {
        player.setFlying(value);
    }

    @Override
    public void setFlySpeed(final float value) throws IllegalArgumentException {
        player.setFlySpeed(value);
    }

    @Override
    public void setFoodLevel(final int value) {
        player.setFoodLevel(value);
    }

    @Override
    public void setGameMode(final GameMode mode) {
        player.setGameMode(mode);
    }

    @Override
    public void setHealth(final int health) {
        player.setHealth(health);
    }

    @Override
    public void setItemInHand(final ItemStack item) {
        player.setItemInHand(item);
    }

    @Override
    public void setItemOnCursor(final ItemStack item) {
        player.setItemOnCursor(item);
    }

    @Override
    public void setLastDamage(final int damage) {
        player.setLastDamage(damage);
    }

    @Override
    public void setLastDamageCause(final EntityDamageEvent event) {
        player.setLastDamageCause(event);
    }

    @Override
    public void setLevel(final int level) {
        player.setLevel(level);
    }

    @Override
    public void setMaximumAir(final int ticks) {
        player.setMaximumAir(ticks);
    }

    @Override
    public void setMaximumNoDamageTicks(final int ticks) {
        player.setMaximumNoDamageTicks(ticks);
    }

    @Override
    public void setMetadata(final String metadataKey, final MetadataValue newMetadataValue) {
        player.setMetadata(metadataKey, newMetadataValue);
    }

    @Override
    public void setNoDamageTicks(final int ticks) {
        player.setNoDamageTicks(ticks);
    }

    @Override
    public void setOp(final boolean value) {
        player.setOp(value);
    }

    @Override
    public boolean setPassenger(final Entity passenger) {
        return player.setPassenger(passenger);
    }

    @Override
    public void setPlayerListName(final String name) {
        player.setPlayerListName(name);
    }

    @Override
    public void setPlayerTime(final long time, final boolean relative) {
        player.setPlayerTime(time, relative);
    }

    @Override
    public void setRemainingAir(final int ticks) {
        player.setRemainingAir(ticks);
    }

    @Override
    public void setSaturation(final float value) {
        player.setSaturation(value);
    }

    @Override
    public void setSleepingIgnored(final boolean isSleeping) {
        player.setSleepingIgnored(isSleeping);
    }

    @Override
    public void setSneaking(final boolean sneak) {
        player.setSneaking(sneak);
    }

    @Override
    public void setSprinting(final boolean sprinting) {
        player.setSprinting(sprinting);
    }

    @Override
    public void setTicksLived(final int value) {
        player.setTicksLived(value);
    }

    @Override
    public void setTotalExperience(final int exp) {
        player.setTotalExperience(exp);
    }

    @Override
    public void setVelocity(final Vector velocity) {
        player.setVelocity(velocity);
    }

    @Override
    public void setWalkSpeed(final float value) throws IllegalArgumentException {
        player.setWalkSpeed(value);
    }

    @Override
    public void setWhitelisted(final boolean value) {
        player.setWhitelisted(value);
    }

    @Override
    public boolean setWindowProperty(final Property prop, final int value) {
        return player.setWindowProperty(prop, value);
    }

    @Override
    @Deprecated
    public Arrow shootArrow() {
        return player.shootArrow();
    }

    @Override
    public void showPlayer(final Player player) {
        player.showPlayer(player);
    }

    @Override
    public boolean teleport(final Entity destination) {
        return player.teleport(destination);
    }

    @Override
    public boolean teleport(final Entity destination, final TeleportCause cause) {
        return player.teleport(destination, cause);
    }

    @Override
    public boolean teleport(final Location location) {
        return player.teleport(location);
    }

    @Override
    public boolean teleport(final Location location, final TeleportCause cause) {
        return player.teleport(location, cause);
    }

    @Override
    @Deprecated
    public Egg throwEgg() {
        return player.throwEgg();
    }

    @Override
    @Deprecated
    public Snowball throwSnowball() {
        return player.throwSnowball();
    }

    /**
     * Updates the reference to the Player object.
     * 
     * @throws IllegalStateException if the reference could not be acquired.
     */
    public final void update() throws IllegalStateException {
        final Player p = Bukkit.getPlayerExact(playerName);
        if (p == null) {
            throw new IllegalStateException();
        }
        this.player = p;
    }

    @Override
    @Deprecated
    public void updateInventory() {
        player.updateInventory();
    }

}
