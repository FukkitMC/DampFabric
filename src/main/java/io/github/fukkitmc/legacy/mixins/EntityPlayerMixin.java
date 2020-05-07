package io.github.fukkitmc.legacy.mixins;

import com.mojang.authlib.GameProfile;
import io.github.fukkitmc.legacy.extra.EntityPlayerExtra;
import net.minecraft.server.*;
import org.bukkit.WeatherType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayer.class)
public abstract class EntityPlayerMixin extends EntityHuman implements EntityPlayerExtra {

    public EntityPlayerMixin(World world, GameProfile gameProfile) {
        super(world, gameProfile);
    }

    @Shadow public abstract void sendMessage(IChatBaseComponent iChatBaseComponent);

    @Shadow public String displayName;

    @Shadow public double maxHealthCache;

    @Override
    public WeatherType getPlayerWeather() {
        return null;
    }

    @Override
    public void setPlayerWeather(WeatherType type, boolean plugin) {

    }

    @Override
    public void resetPlayerWeather() {

    }

    @Inject(method = "<init>", at=@At("TAIL"))
    public void constructor(MinecraftServer minecraftServer, WorldServer worldServer, GameProfile gameProfile, PlayerInteractManager playerInteractManager, CallbackInfo ci){
        // CraftBukkit start
        this.displayName = this.getName();
        // this.canPickUpLoot = true; TODO
        this.maxHealthCache = this.getMaxHealth();
        // CraftBukkit end
    }

    @Override
    public long getPlayerTime() {
        return 0;
    }

    @Override
    public void sendMessages(IChatBaseComponent[] ichatbasecomponent) {
        for (IChatBaseComponent component : ichatbasecomponent) {
            this.sendMessage(component);
        }
    }

}
