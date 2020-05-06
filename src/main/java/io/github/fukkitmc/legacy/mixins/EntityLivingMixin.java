package io.github.fukkitmc.legacy.mixins;


import io.github.fukkitmc.legacy.extra.EntityLivingExtra;
import net.minecraft.server.BiomeBase;
import net.minecraft.server.EntityLiving;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityLiving.class)
public class EntityLivingMixin implements EntityLivingExtra{
    @Override
    public int getExpReward() {
        return 0;
    }
}
