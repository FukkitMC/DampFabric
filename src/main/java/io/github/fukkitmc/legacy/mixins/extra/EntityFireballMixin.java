package io.github.fukkitmc.legacy.mixins.extra;


import io.github.fukkitmc.legacy.extra.EntityFireballExtra;
import net.minecraft.server.BiomeBase;
import net.minecraft.server.EntityFireball;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityFireball.class)
public class EntityFireballMixin implements EntityFireballExtra {
    @Override
    public void setDirection(double d0, double d1, double d2) {

    }
}
