package io.github.fukkitmc.legacy.mixins;


import io.github.fukkitmc.legacy.extra.EntityDamageSourceIndirectExtra;
import net.minecraft.server.BiomeBase;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityDamageSourceIndirect;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityDamageSourceIndirect.class)
public class EntityDamageSourceIndirectMixin implements EntityDamageSourceIndirectExtra {
    @Override
    public Entity getProximateDamageSource() {
        return null;
    }
}
