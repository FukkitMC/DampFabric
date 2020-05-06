package io.github.fukkitmc.legacy.mixins;

import io.github.fukkitmc.legacy.extra.EntityExtra;
import net.minecraft.server.DataWatcher;
import net.minecraft.server.Entity;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public class EntityMixin implements EntityExtra {

    @Shadow
    public DataWatcher datawatcher;

    @Override
    public CraftEntity getBukkitEntity() {
        if (((Entity) (Object) this).bukkitEntity == null) {
            ((Entity) (Object) this).bukkitEntity = CraftEntity.getEntity(((Entity) (Object) this).world.getServer(), ((Entity) (Object) this));
        }
        return ((Entity) (Object) this).bukkitEntity;
    }
}
