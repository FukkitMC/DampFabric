package io.github.fukkitmc.legacy.mixins;

import io.github.fukkitmc.legacy.extra.ChunkExtra;
import net.minecraft.server.Chunk;
import net.minecraft.server.ChunkSection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Chunk.class)
public class ChunkMixin implements ChunkExtra {


    @Shadow
    public ChunkSection[] sections;

    @Override
    public ChunkSection[] getSections() {
        return this.sections;
    }

    @Override
    public void setNeighborLoaded(int i, int j) {

    }
}
