package io.github.fukkitmc.legacy.mixins;


import io.github.fukkitmc.legacy.extra.BiomeBaseExtra;
import net.minecraft.server.BiomeBase;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BiomeBase.class)
public class BiomeBaseMixin implements BiomeBaseExtra{
}
