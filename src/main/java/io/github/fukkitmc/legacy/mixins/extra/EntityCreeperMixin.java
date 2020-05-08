package io.github.fukkitmc.legacy.mixins.extra;

import io.github.fukkitmc.legacy.extra.EntityCreeperExtra;
import net.minecraft.server.*;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityCreeper.class)
public class EntityCreeperMixin implements EntityCreeperExtra{

    public void setPowered(boolean powered) {
        DataWatcher dataWatcher = ((Entity) (Object) this).datawatcher;
        if (!powered) {
            dataWatcher.watch(17, (byte) 0);
        } else {
            dataWatcher.watch(17, (byte) 1);
        }
    }
}
