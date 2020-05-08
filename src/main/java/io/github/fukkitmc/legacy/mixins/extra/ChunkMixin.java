package io.github.fukkitmc.legacy.mixins.extra;

import io.github.fukkitmc.legacy.extra.ChunkExtra;
import net.minecraft.server.Chunk;
import net.minecraft.server.ChunkSection;
import net.minecraft.server.EmptyChunk;
import net.minecraft.server.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Chunk.class, remap = false)
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
