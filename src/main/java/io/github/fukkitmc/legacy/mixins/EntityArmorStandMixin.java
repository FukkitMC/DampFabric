package io.github.fukkitmc.legacy.mixins;


import io.github.fukkitmc.legacy.extra.EntityArmorStandExtra;
import net.minecraft.server.BiomeBase;
import net.minecraft.server.EntityArmorStand;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityArmorStand.class)
public class EntityArmorStandMixin implements EntityArmorStandExtra {
    @Override
    public void n(boolean flag) {

    }
}
