package io.github.fukkitmc.legacy.mixins.extra;

import com.mojang.authlib.GameProfile;
import io.github.fukkitmc.legacy.extra.EntityPlayerExtra;
import net.minecraft.server.*;
import org.bukkit.WeatherType;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
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

    @Shadow public PlayerConnection playerConnection;

    @Shadow public WeatherType weather;

    @Shadow @Final public PlayerInteractManager playerInteractManager;

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

    public void updateWeather(float oldRain, float newRain, float oldThunder, float newThunder){
        if (this.weather == null) {
            // Vanilla
            if (oldRain != newRain) {
                this.playerConnection.sendPacket(new PacketPlayOutGameStateChange(7, newRain));
            }
        } else {
            // Plugin
            //TODO: fukkit implement plugin weather
//            if (pluginRainPositionPrevious != pluginRainPosition) {
//                this.playerConnection.sendPacket(new PacketPlayOutGameStateChange(7, pluginRainPosition));
//            }
        }

        if (oldThunder != newThunder) {
            if (weather == WeatherType.DOWNFALL || weather == null) {
                this.playerConnection.sendPacket(new PacketPlayOutGameStateChange(8, newThunder));
            } else {
                this.playerConnection.sendPacket(new PacketPlayOutGameStateChange(8, 0));
            }
        }
    }

    /**
     * @author
     */
    @Overwrite
    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setInt("playerGameType", this.playerInteractManager.getGameMode().getId());

        ((CraftPlayer)this.getBukkitEntity()).setExtraData(nbttagcompound); // CraftBukkit
    }

    @Override
    public String getSpawnWorld() {
        return super.spawnWorld;
    }

    @Override
    public void setSpawnWorld(String s){
        super.spawnWorld = s;
    }

}
