package io.github.fukkitmc.legacy.mixins;


import io.github.fukkitmc.legacy.extra.EntityHorseExtra;
import net.minecraft.server.BiomeBase;
import net.minecraft.server.EntityHorse;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityHorse.class)
public class EntityHorseMixin implements EntityHorseExtra {
}
