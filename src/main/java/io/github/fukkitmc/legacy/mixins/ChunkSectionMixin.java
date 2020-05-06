package io.github.fukkitmc.legacy.mixins;


import io.github.fukkitmc.legacy.extra.ChunkSectionExtra;
import net.minecraft.server.BiomeBase;
import net.minecraft.server.ChunkSection;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ChunkSection.class)
public class ChunkSectionMixin implements ChunkSectionExtra {
}
