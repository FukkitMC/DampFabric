package io.github.fukkitmc.legacy.mixins;

import io.github.fukkitmc.legacy.extra.EntityCreeperExtra;
import net.minecraft.server.EntityCreeper;
import net.minecraft.server.EntityMonster;
import net.minecraft.server.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityCreeper.class)
public abstract class EntityCreeperMixin extends EntityMonster implements EntityCreeperExtra{

    public EntityCreeperMixin(World world) {
        super(world);
    }

    public void setPowered(boolean powered) {
        if (!powered) {
            this.datawatcher.watch(17, (byte) 0);
        } else {
            this.datawatcher.watch(17, (byte) 1);
        }
    }

}
