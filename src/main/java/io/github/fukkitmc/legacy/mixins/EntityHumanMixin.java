package io.github.fukkitmc.legacy.mixins;

import com.mojang.authlib.GameProfile;
import io.github.fukkitmc.legacy.extra.EntityHumanExtra;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityHuman.class)
public abstract class EntityHumanMixin extends EntityLiving implements EntityHumanExtra {

    @Shadow public String spawnWorld;

    public EntityHumanMixin(World world) {
        super(world);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void constructor(World world, GameProfile gameProfile, CallbackInfo ci){
        spawnWorld = "";
    }

    @Inject(method = "b(Lnet/minecraft/server/NBTTagCompound;)V", at = @At("TAIL"))
    public void loadNbtData(NBTTagCompound nBTTagCompound, CallbackInfo ci){
        spawnWorld = nBTTagCompound.getString("SpawnWorld");
        if ("".equals(spawnWorld)) {
            this.spawnWorld = world.getServer().getWorlds().get(0).getName();
        }
    }

    @Override
    public String getSpawnWorld() {
        return this.spawnWorld;
    }

    @Override
    public void setSpawnWorld(String s){
        this.spawnWorld = s;
    }

}
