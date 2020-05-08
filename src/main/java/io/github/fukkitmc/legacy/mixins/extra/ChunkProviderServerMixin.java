package io.github.fukkitmc.legacy.mixins.extra;


import io.github.fukkitmc.legacy.extra.ChunkProviderServerExtra;
import net.minecraft.server.BiomeBase;
import net.minecraft.server.Chunk;
import net.minecraft.server.ChunkProviderServer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ChunkProviderServer.class)
public class ChunkProviderServerMixin implements ChunkProviderServerExtra {
    @Override
    public Chunk originalGetChunkAt(int i, int j) {
        return null;
    }

    @Override
    public Chunk getChunkIfLoaded(int x, int z) {
        return null;
    }
}
