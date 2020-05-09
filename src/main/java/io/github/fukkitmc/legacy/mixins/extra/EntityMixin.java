package io.github.fukkitmc.legacy.mixins.extra;

import io.github.fukkitmc.legacy.extra.EntityExtra;
import io.github.fukkitmc.legacy.extra.WorldExtra;
import net.minecraft.server.*;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.UUID;

@Mixin(value = Entity.class, remap = false)
public abstract class EntityMixin implements EntityExtra {

    @Shadow
    public DataWatcher datawatcher;

    @Shadow
    public int fireTicks;

    @Shadow
    public float yaw;

    @Shadow
    public float pitch;
    @Shadow
    public CommandObjectiveExecutor au;
    @Shadow
    public double R;
    @Shadow
    public World world;
    @Shadow
    public boolean invulnerable;
    @Shadow
    public int dimension;

    @Shadow
    public abstract NBTTagList a(double... ds);

    @Shadow
    public abstract boolean R();

    @Shadow
    public abstract UUID getUniqueID();

    @Shadow
    public abstract NBTTagList a(float... fs);

    @Shadow
    public abstract void b(NBTTagCompound nBTTagCompound);

    @Override
    public CraftEntity getBukkitEntity() {
        if (((Entity) (Object) this).bukkitEntity == null) {
            ((Entity) (Object) this).bukkitEntity = CraftEntity.getEntity(((WorldExtra)((Entity) (Object) this).world).getServer(), ((Entity) (Object) this));
        }
        return ((Entity) (Object) this).bukkitEntity;
    }
}
