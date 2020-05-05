package io.github.fukkitmc.legacy.mixins;

import io.github.fukkitmc.legacy.extra.EntityArmorStandExtra;
import net.minecraft.server.EntityArmorStand;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityArmorStand.class)
public abstract class ArmorStandMixin extends EntityLiving implements EntityArmorStandExtra{

    public ArmorStandMixin(World world) {
        super(world);
    }

    @Override
    public void n(boolean flag) {
        byte b0 = this.datawatcher.getByte(10);

        if (flag) {
            b0 = (byte) (b0 | 16);
        } else {
            b0 &= -17;
        }

        this.datawatcher.watch(10, b0);
    }
}
