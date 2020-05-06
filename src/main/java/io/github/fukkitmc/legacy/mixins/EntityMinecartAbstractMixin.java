package io.github.fukkitmc.legacy.mixins;


import io.github.fukkitmc.legacy.extra.EntityMinecartAbstractExtra;
import net.minecraft.server.BiomeBase;
import net.minecraft.server.EntityMinecartAbstract;
import org.bukkit.util.Vector;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityMinecartAbstract.class)
public class EntityMinecartAbstractMixin implements EntityMinecartAbstractExtra {
    @Override
    public Vector getFlyingVelocityMod() {
        return null;
    }

    @Override
    public void setFlyingVelocityMod(Vector flying) {

    }

    @Override
    public Vector getDerailedVelocityMod() {
        return null;
    }

    @Override
    public void setDerailedVelocityMod(Vector derailed) {

    }
}
