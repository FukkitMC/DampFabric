package io.github.fukkitmc.legacy.mixins;

import io.github.fukkitmc.legacy.extra.EntityExtra;
import net.minecraft.server.Entity;
import net.minecraft.server.World;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public class EntityMixin implements EntityExtra {
    @Shadow public World world;

    @Shadow public CraftEntity bukkitEntity;

    @Override
    public CraftEntity getBukkitEntity() {
        if (bukkitEntity == null) {
            bukkitEntity = CraftEntity.getEntity(world.getServer(), ((Entity) (Object) this));
        }
        return bukkitEntity;
    }
}
