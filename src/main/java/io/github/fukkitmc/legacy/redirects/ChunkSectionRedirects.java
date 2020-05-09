package io.github.fukkitmc.legacy.redirects;

import net.minecraft.server.ChunkSection;

public class ChunkSectionRedirects {

    public static ChunkSection createChunkSection(int y, boolean flag, char[] blockIds){
        return new ChunkSection(y, flag);
    }


}
