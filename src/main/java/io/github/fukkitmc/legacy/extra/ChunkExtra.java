package io.github.fukkitmc.legacy.extra;

import net.minecraft.server.ChunkSection;

public interface ChunkExtra {

    ChunkSection[] getSections();

    void setNeighborLoaded(int i, int j);

}
