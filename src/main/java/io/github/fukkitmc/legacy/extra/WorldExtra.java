package io.github.fukkitmc.legacy.extra;

import net.minecraft.server.Block;
import net.minecraft.server.BlockPosition;
import net.minecraft.server.Chunk;
import net.minecraft.server.Entity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent;

public interface WorldExtra {

    CraftWorld getWorld();

    CraftServer getServer();

    void checkSleepStatus();

    void notifyAndUpdatePhysics(BlockPosition blockposition, Chunk chunk, Block oldBlock, Block newBLock, int flag);

    boolean addEntity(Entity entity, CreatureSpawnEvent.SpawnReason spawnReason);

}
