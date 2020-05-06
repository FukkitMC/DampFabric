package io.github.fukkitmc.legacy.mixins;

import io.github.fukkitmc.legacy.extra.WorldExtra;
import net.minecraft.server.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(World.class)
public abstract class WorldMixin implements WorldExtra {


    @Shadow public CraftWorld world;

    @Shadow @Final public boolean isClientSide;

    @Shadow public abstract void everyoneSleeping();

    @Shadow public abstract boolean addEntity(Entity entity);

    @Override
    public CraftWorld getWorld() {
        return this.world;
    }

    @Override
    public CraftServer getServer() {
        return (CraftServer) Bukkit.getServer();
    }

    @Override
    public void checkSleepStatus() {
        if (!this.isClientSide) {
            this.everyoneSleeping();
        }
    }

    @Override
    public boolean addEntity(Entity entity, CreatureSpawnEvent.SpawnReason spawnReason) { // Changed signature, added SpawnReason
        return this.addEntity(entity);
    }
}
