package io.github.fukkitmc.legacy.mixins.fukkit;

import net.minecraft.server.DedicatedPlayerList;
import net.minecraft.server.DedicatedServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DedicatedPlayerList.class)
public abstract class DedicatedPlayerListMixin extends PlayerList {

    public DedicatedPlayerListMixin(MinecraftServer minecraftServer) {
        super(minecraftServer);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void constructor(DedicatedServer dedicatedServer, CallbackInfo ci){
    }

}
