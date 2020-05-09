package io.github.fukkitmc.legacy.extra;

import net.minecraft.server.Chunk;

public interface ChunkProviderServerExtra {

    Chunk originalGetChunkAt(int i, int j);

    Chunk getChunkIfLoaded(int x, int z);

}
