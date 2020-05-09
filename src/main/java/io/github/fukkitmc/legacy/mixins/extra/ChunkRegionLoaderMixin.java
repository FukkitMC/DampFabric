package io.github.fukkitmc.legacy.mixins.extra;

import io.github.fukkitmc.legacy.extra.ChunkRegionLoaderExtra;
import net.minecraft.server.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@Mixin(ChunkRegionLoader.class)
public abstract class ChunkRegionLoaderMixin implements ChunkRegionLoaderExtra {


    @Shadow public Map<ChunkCoordIntPair, NBTTagCompound> b;

    @Shadow public File d;

    @Shadow public abstract Chunk a(World world, int i, int j, NBTTagCompound nBTTagCompound);

    public Object[] loadChunk(World world, int i, int j) throws IOException {
        ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(i, j);
        NBTTagCompound nbttagcompound = this.b.get(chunkcoordintpair);

        if (nbttagcompound == null) {
            DataInputStream datainputstream = RegionFileCache.c(this.d, i, j);

            if (datainputstream == null) {
                return null;
            }

            nbttagcompound = NBTCompressedStreamTools.a(datainputstream);
        }

        //TODO: fix
        return null;
//        return this.a(world, i, j, nbttagcompound);
    }

}
