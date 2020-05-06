package io.github.fukkitmc.legacy.mixins;


import io.github.fukkitmc.legacy.extra.PlayerListExtra;
import net.minecraft.server.BiomeBase;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerList;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerList.class)
public class PlayerListMixin implements PlayerListExtra {

    @Inject(method = "<init>", at = @At("TAIL"))
    public void constructor(MinecraftServer minecraftServer, CallbackInfo ci){
        ((PlayerList)(Object)this).cserver = minecraftServer.server = new CraftServer(minecraftServer, ((PlayerList)(Object)this));
    }

    @Override
    public EntityPlayer moveToWorld(EntityPlayer entityplayer, int i, boolean flag, Location location, boolean avoidSuffocation) {
        return null;
    }
}
