package io.github.fukkitmc.legacy.mixins;

import io.github.fukkitmc.legacy.extra.MinecraftServerExtra;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.UserCache;
import org.bukkit.craftbukkit.OptionsParser;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin implements MinecraftServerExtra {

    @Shadow public int G;

    @Shadow public UserCache Z;

    @Override
    public int getIdleTimeout() {
        return this.G;
    }

    @Override
    public void setIdleTimeout(int timeout) {
        this.G = timeout;
    }

    @Override
    public String getVersion() {
        return "1.8.9";
    }

    //TODO: properly add these later

    @Override
    public boolean getSpawnAnimals() {
        return true;
    }

    @Override
    public boolean getAllowFlight() {
        return true;
    }

    @Override
    public boolean isHardcore() {
        return false;
    }

    @Override
    public void safeShutdown() {
        //this.isRunning = false;
    }

    @Override
    public String getMotd() {
        return "MOTD IS NOT SETUP YET";
    }

    @Override
    public UserCache getUserCache() {
        return this.Z;
    }

    /**
     * @author hydos
     * @reason bukkit
     */
    @Environment(EnvType.SERVER)
    @Overwrite
    public static void main(String[] args){
        OptionsParser.main(args);
    }
}
