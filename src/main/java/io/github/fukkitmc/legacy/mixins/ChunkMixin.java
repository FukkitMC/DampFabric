package io.github.fukkitmc.legacy.mixins;

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

@Mixin(Chunk.class)
public class ChunkMixin implements ChunkExtra {

    @Inject(method = "<init>(Lnet/minecraft/server/World;II)V", at = @At("TAIL"))
    public void constructor(World world, int i, int j, CallbackInfo ci){
        // CraftBukkit start
        if (!((Object)this instanceof EmptyChunk)) {
            ((Chunk)(Object)this).bukkitChunk = new org.bukkit.craftbukkit.CraftChunk(((Chunk)(Object)this));
        }
    }

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
