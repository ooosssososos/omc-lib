package net.obnoxint.mcdev.util;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.BlockChangeDelegate;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Difficulty;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

/**
 * Abstract implementation of the org.bukkit.World interface.
 * 
 * @author obnoxint
 * @since bukkit-1.3.1-R2.1
 */
@SuppressWarnings("deprecation")
public abstract class AbstractWorld implements World {

    private final String worldName;
    private World world;

    public AbstractWorld(final World world) {
        if (world == null) {
            throw new IllegalArgumentException();
        }
        this.worldName = world.getName();
        update();
    }

    @Override
    public boolean canGenerateStructures() {
        return world.canGenerateStructures();
    }

    @Override
    public boolean createExplosion(final double x, final double y, final double z, final float power) {
        return world.createExplosion(x, y, z, power);
    }

    @Override
    public boolean createExplosion(final double x, final double y, final double z, final float power, final boolean setFire) {
        return world.createExplosion(x, y, z, power, setFire);
    }

    @Override
    public boolean createExplosion(final Location loc, final float power) {
        return world.createExplosion(loc, power);
    }

    @Override
    public boolean createExplosion(final Location loc, final float power, final boolean setFire) {
        return world.createExplosion(loc, power, setFire);
    }

    @Override
    public Item dropItem(final Location location, final ItemStack item) {
        return world.dropItem(location, item);
    }

    @Override
    public Item dropItemNaturally(final Location location, final ItemStack item) {
        return world.dropItemNaturally(location, item);
    }

    @Override
    public boolean generateTree(final Location location, final TreeType type) {
        return world.generateTree(location, type);
    }

    @Override
    public boolean generateTree(final Location loc, final TreeType type, final BlockChangeDelegate delegate) {
        return world.generateTree(loc, type, delegate);
    }

    @Override
    public boolean getAllowAnimals() {
        return world.getAllowAnimals();
    }

    @Override
    public boolean getAllowMonsters() {
        return world.getAllowMonsters();
    }

    @Override
    public int getAnimalSpawnLimit() {
        return world.getAnimalSpawnLimit();
    }

    @Override
    public Biome getBiome(final int x, final int z) {
        return world.getBiome(x, z);
    }

    @Override
    public Block getBlockAt(final int x, final int y, final int z) {
        return world.getBlockAt(x, y, z);
    }

    @Override
    public Block getBlockAt(final Location location) {
        return world.getBlockAt(location);
    }

    @Override
    public int getBlockTypeIdAt(final int x, final int y, final int z) {
        return world.getBlockTypeIdAt(x, y, z);
    }

    @Override
    public int getBlockTypeIdAt(final Location location) {
        return world.getBlockTypeIdAt(location);
    }

    @Override
    public Chunk getChunkAt(final Block block) {
        return world.getChunkAt(block);
    }

    @Override
    public Chunk getChunkAt(final int x, final int z) {
        return world.getChunkAt(x, z);
    }

    @Override
    public Chunk getChunkAt(final Location location) {
        return world.getChunkAt(location);
    }

    @Override
    public Difficulty getDifficulty() {
        return world.getDifficulty();
    }

    @Override
    public ChunkSnapshot getEmptyChunkSnapshot(final int x, final int z, final boolean includeBiome, final boolean includeBiomeTempRain) {
        return world.getEmptyChunkSnapshot(x, z, includeBiome, includeBiomeTempRain);
    }

    @Override
    public List<Entity> getEntities() {
        return world.getEntities();
    }

    @SuppressWarnings("unchecked")
    @Override
    @Deprecated
    public <T extends Entity> Collection<T> getEntitiesByClass(final Class<T>... classes) {
        return world.getEntitiesByClass(classes);
    }

    @Override
    public <T extends Entity> Collection<T> getEntitiesByClass(final Class<T> cls) {
        return world.getEntitiesByClass(cls);
    }

    @Override
    public Collection<Entity> getEntitiesByClasses(final Class<?>... classes) {
        return world.getEntitiesByClasses(classes);
    }

    @Override
    public Environment getEnvironment() {
        return world.getEnvironment();
    }

    @Override
    public long getFullTime() {
        return world.getFullTime();
    }

    @Override
    public ChunkGenerator getGenerator() {
        return world.getGenerator();
    }

    @Override
    public Block getHighestBlockAt(final int x, final int z) {
        return world.getHighestBlockAt(x, z);
    }

    @Override
    public Block getHighestBlockAt(final Location location) {
        return world.getHighestBlockAt(location);
    }

    @Override
    public int getHighestBlockYAt(final int x, final int z) {
        return world.getHighestBlockYAt(x, z);
    }

    @Override
    public int getHighestBlockYAt(final Location location) {
        return world.getHighestBlockYAt(location);
    }

    @Override
    public double getHumidity(final int x, final int z) {
        return world.getHumidity(x, z);
    }

    @Override
    public boolean getKeepSpawnInMemory() {
        return world.getKeepSpawnInMemory();
    }

    @Override
    public Set<String> getListeningPluginChannels() {
        return world.getListeningPluginChannels();
    }

    @Override
    public List<LivingEntity> getLivingEntities() {
        return world.getLivingEntities();
    }

    @Override
    public Chunk[] getLoadedChunks() {
        return world.getLoadedChunks();
    }

    @Override
    public int getMaxHeight() {
        return world.getMaxHeight();
    }

    @Override
    public List<MetadataValue> getMetadata(final String metadataKey) {
        return world.getMetadata(metadataKey);
    }

    @Override
    public int getMonsterSpawnLimit() {
        return world.getMonsterSpawnLimit();
    }

    @Override
    public String getName() {
        return world.getName();
    }

    @Override
    public List<Player> getPlayers() {
        return world.getPlayers();
    }

    @Override
    public List<BlockPopulator> getPopulators() {
        return world.getPopulators();
    }

    @Override
    public boolean getPVP() {
        return world.getPVP();
    }

    @Override
    public int getSeaLevel() {
        return world.getSeaLevel();
    }

    @Override
    public long getSeed() {
        return world.getSeed();
    }

    @Override
    public Location getSpawnLocation() {
        return world.getSpawnLocation();
    }

    @Override
    public double getTemperature(final int x, final int z) {
        return world.getTemperature(x, z);
    }

    @Override
    public int getThunderDuration() {
        return world.getThunderDuration();
    }

    @Override
    public long getTicksPerAnimalSpawns() {
        return world.getTicksPerAnimalSpawns();
    }

    @Override
    public long getTicksPerMonsterSpawns() {
        return world.getTicksPerMonsterSpawns();
    }

    @Override
    public long getTime() {
        return world.getTime();
    }

    @Override
    public UUID getUID() {
        return world.getUID();
    }

    @Override
    public int getWaterAnimalSpawnLimit() {
        return world.getWaterAnimalSpawnLimit();
    }

    @Override
    public int getWeatherDuration() {
        return world.getWeatherDuration();
    }

    /**
     * @return the according instance of org.bukkit.World.
     */
    public final World getWorld() {
        return world;
    }

    @Override
    public File getWorldFolder() {
        return world.getWorldFolder();
    }

    @Override
    public WorldType getWorldType() {
        return world.getWorldType();
    }

    @Override
    public boolean hasMetadata(final String metadataKey) {
        return world.hasMetadata(metadataKey);
    }

    @Override
    public boolean hasStorm() {
        return world.hasStorm();
    }

    @Override
    public boolean isAutoSave() {
        return world.isAutoSave();
    }

    @Override
    public boolean isChunkInUse(final int x, final int z) {
        return world.isChunkInUse(x, z);
    }

    @Override
    public boolean isChunkLoaded(final Chunk chunk) {
        return world.isChunkLoaded(chunk);
    }

    @Override
    public boolean isChunkLoaded(final int x, final int z) {
        return world.isChunkLoaded(x, z);
    }

    @Override
    public boolean isThundering() {
        return world.isThundering();
    }

    @Override
    public void loadChunk(final Chunk chunk) {
        world.loadChunk(chunk);
    }

    @Override
    public void loadChunk(final int x, final int z) {
        world.loadChunk(x, z);
    }

    @Override
    public boolean loadChunk(final int x, final int z, final boolean generate) {
        return world.loadChunk(x, z, generate);
    }

    @Override
    public void playEffect(final Location location, final Effect effect, final int data) {
        world.playEffect(location, effect, data);
    }

    @Override
    public void playEffect(final Location location, final Effect effect, final int data, final int radius) {
        world.playEffect(location, effect, data, radius);
    }

    @Override
    public <T> void playEffect(final Location location, final Effect effect, final T data) {
        world.playEffect(location, effect, data);
    }

    @Override
    public <T> void playEffect(final Location location, final Effect effect, final T data, final int radius) {
        world.playEffect(location, effect, data, radius);
    }

    @Override
    public void playSound(final Location loc, final Sound sound, final float volume, final float pitch) {
        world.playSound(loc, sound, volume, pitch);
    }

    @Override
    public boolean refreshChunk(final int x, final int z) {
        return world.refreshChunk(x, z);
    }

    @Override
    public boolean regenerateChunk(final int x, final int z) {
        return world.regenerateChunk(x, z);
    }

    @Override
    public void removeMetadata(final String metadataKey, final Plugin owningPlugin) {
        world.removeMetadata(metadataKey, owningPlugin);
    }

    @Override
    public void save() {
        world.save();
    }

    @Override
    public void sendPluginMessage(final Plugin source, final String channel, final byte[] message) {
        world.sendPluginMessage(source, channel, message);
    }

    @Override
    public void setAnimalSpawnLimit(final int limit) {
        world.setAnimalSpawnLimit(limit);
    }

    @Override
    public void setAutoSave(final boolean value) {
        world.setAutoSave(value);
    }

    @Override
    public void setBiome(final int x, final int z, final Biome bio) {
        world.setBiome(x, z, bio);
    }

    @Override
    public void setDifficulty(final Difficulty difficulty) {
        world.setDifficulty(difficulty);
    }

    @Override
    public void setFullTime(final long time) {
        world.setFullTime(time);
    }

    @Override
    public void setKeepSpawnInMemory(final boolean keepLoaded) {
        world.setKeepSpawnInMemory(keepLoaded);
    }

    @Override
    public void setMetadata(final String metadataKey, final MetadataValue newMetadataValue) {
        world.setMetadata(metadataKey, newMetadataValue);
    }

    @Override
    public void setMonsterSpawnLimit(final int limit) {
        world.setMonsterSpawnLimit(limit);
    }

    @Override
    public void setPVP(final boolean pvp) {
        world.setPVP(pvp);
    }

    @Override
    public void setSpawnFlags(final boolean allowMonsters, final boolean allowAnimals) {
        world.setSpawnFlags(allowMonsters, allowAnimals);
    }

    @Override
    public boolean setSpawnLocation(final int x, final int y, final int z) {
        return world.setSpawnLocation(x, y, z);
    }

    @Override
    public void setStorm(final boolean hasStorm) {
        world.setStorm(hasStorm);
    }

    @Override
    public void setThunderDuration(final int duration) {
        world.setThunderDuration(duration);
    }

    @Override
    public void setThundering(final boolean thundering) {
        world.setThundering(thundering);
    }

    @Override
    public void setTicksPerAnimalSpawns(final int ticksPerAnimalSpawns) {
        world.setTicksPerAnimalSpawns(ticksPerAnimalSpawns);
    }

    @Override
    public void setTicksPerMonsterSpawns(final int ticksPerMonsterSpawns) {
        world.setTicksPerMonsterSpawns(ticksPerMonsterSpawns);
    }

    @Override
    public void setTime(final long time) {
        world.setTime(time);
    }

    @Override
    public void setWaterAnimalSpawnLimit(final int limit) {
        world.setWaterAnimalSpawnLimit(limit);
    }

    @Override
    public void setWeatherDuration(final int duration) {
        world.setWeatherDuration(duration);
    }

    @Override
    public <T extends Entity> T spawn(final Location location, final Class<T> clazz) throws IllegalArgumentException {
        return world.spawn(location, clazz);
    }

    @Override
    public Arrow spawnArrow(final Location location, final Vector velocity, final float speed, final float spread) {
        return world.spawnArrow(location, velocity, speed, spread);
    }

    @Override
    @Deprecated
    public LivingEntity spawnCreature(final Location loc, final CreatureType type) {
        return world.spawnCreature(loc, type);
    }

    @Override
    @Deprecated
    public LivingEntity spawnCreature(final Location loc, final EntityType type) {
        return world.spawnCreature(loc, type);
    }

    @Override
    public Entity spawnEntity(final Location loc, final EntityType type) {
        return world.spawnEntity(loc, type);
    }

    @Override
    public FallingBlock spawnFallingBlock(final Location location, final int blockId, final byte blockData) throws IllegalArgumentException {
        return world.spawnFallingBlock(location, blockId, blockData);
    }

    @Override
    public FallingBlock spawnFallingBlock(final Location location, final Material material, final byte data) throws IllegalArgumentException {
        return world.spawnFallingBlock(location, material, data);
    }

    @Override
    public LightningStrike strikeLightning(final Location loc) {
        return world.strikeLightning(loc);
    }

    @Override
    public LightningStrike strikeLightningEffect(final Location loc) {
        return world.strikeLightningEffect(loc);
    }

    @Override
    public boolean unloadChunk(final Chunk chunk) {
        return world.unloadChunk(chunk);
    }

    @Override
    public boolean unloadChunk(final int x, final int z) {
        return world.unloadChunk(x, z);
    }

    @Override
    public boolean unloadChunk(final int x, final int z, final boolean save) {
        return world.unloadChunk(x, z, save);
    }

    @Override
    public boolean unloadChunk(final int x, final int z, final boolean save, final boolean safe) {
        return world.unloadChunk(x, z, save, safe);
    }

    @Override
    public boolean unloadChunkRequest(final int x, final int z) {
        return world.unloadChunkRequest(x, z);
    }

    @Override
    public boolean unloadChunkRequest(final int x, final int z, final boolean safe) {
        return world.unloadChunkRequest(x, z, safe);
    }

    /**
     * @throws IllegalStateException
     */
    public final void update() throws IllegalStateException {
        final World w = Bukkit.getWorld(worldName);
        if (w == null) {
            throw new IllegalStateException();
        }
        this.world = w;
    }

}
