package io.github.fukkitmc.legacy.mixins;

import io.github.fukkitmc.legacy.extra.MinecraftServerExtra;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerList;
import net.minecraft.server.UserCache;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin implements MinecraftServerExtra {

    @Shadow public int G;

    @Shadow public UserCache Z;

    @Shadow public PlayerList v;

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

    @Override
    public void stop() {

    }

    @Override
    public PlayerList getPlayerList() {
        return this.v;
    }

    @Override
    public boolean getOnlineMode() {
        return false;
    }

    @Override
    public void setOnlineMode(boolean flag) {

    }

    @Override
    public void setPVP(boolean flag) {

    }

    @Override
    public boolean getPVP() {
        return false;
    }

    @Override
    public void setSpawnAnimals(boolean flag) {

    }

    @Override
    public void setAllowFlight(boolean flag) {

    }

    @Override
    public void setMotd(String s) {

    }
}
