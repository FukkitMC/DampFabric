package io.github.fukkitmc.legacy.mixins;


import io.github.fukkitmc.legacy.extra.PlayerListExtra;
import net.minecraft.server.*;
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
        minecraftServer.console = org.bukkit.craftbukkit.command.ColouredConsoleSender.getInstance();
        minecraftServer.reader.addCompleter(new org.bukkit.craftbukkit.command.ConsoleCommandCompleter(minecraftServer.server));
    }

    @Override
    public EntityPlayer moveToWorld(EntityPlayer entityplayer, int i, boolean flag, Location location, boolean avoidSuffocation) {
        return null;
    }

    @Override
    public void setPlayerFileData(WorldServer[] aworldserver) {

    }
}
