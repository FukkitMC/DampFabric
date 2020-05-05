package io.github.fukkitmc.legacy.mixins;

import io.github.fukkitmc.legacy.extra.MinecraftServerExtra;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PropertyManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin implements MinecraftServerExtra {

    @Shadow private int G;

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
        return "Fuck forge";
    }
}
