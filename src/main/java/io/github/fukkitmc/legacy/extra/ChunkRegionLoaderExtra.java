package io.github.fukkitmc.legacy.extra;

import net.minecraft.server.Chunk;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.World;

import java.io.IOException;

public interface ChunkRegionLoaderExtra {

    Object[] loadChunk(World world, int i, int j) throws IOException;

    void loadEntities(Chunk chunk, NBTTagCompound nbttagcompound, World world);


}
