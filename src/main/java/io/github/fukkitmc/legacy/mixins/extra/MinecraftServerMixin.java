package io.github.fukkitmc.legacy.mixins.extra;

import io.github.fukkitmc.legacy.extra.MinecraftServerExtra;
import jline.console.ConsoleReader;
import net.minecraft.server.*;
import org.bukkit.craftbukkit.CraftServer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.FutureTask;

@Mixin(value = MinecraftServer.class, remap = false)
public abstract class MinecraftServerMixin implements MinecraftServerExtra {

    @Shadow
    public int G;

    @Shadow
    public UserCache Z;

    @Shadow
    public PlayerList v;

    @Shadow
    public CraftServer server;

    @Shadow
    public ConsoleReader reader;

    @Shadow
    public List<WorldServer> worlds;
    @Shadow
    public MethodProfiler methodProfiler;
    @Shadow
    public long ab;
    @Shadow
    public ServerPing r;
    @Shadow
    public boolean w;
    @Shadow
    public long R;
    @Shadow
    public boolean Q;
    @Shadow
    public boolean x;
    @Shadow
    public String E;
    @Shadow
    @Final
    public MethodProfiler c;
    @Shadow
    public long[][] i;
    @Shadow
    public int y;
    @Shadow
    public WorldServer[] d;
    @Shadow
    public Queue<FutureTask<?>> j;
    @Shadow
    public List<IUpdatePlayerListBox> p;
    @Shadow
    public Queue<Runnable> processQueue;
    @Shadow
    public String O;
    @Shadow
    public String P;
    @Shadow
    public MojangStatisticsGenerator n;
    @Shadow
    public boolean N;

    @Shadow
    public abstract void b(String string);

    @Shadow
    public abstract boolean X();

    @Shadow
    public abstract void s();

    @Shadow
    public abstract void a_(String string, int i);

    @Shadow
    public abstract boolean v();

    @Shadow
    public abstract boolean i() throws IOException;

    @Shadow
    public abstract void a(ServerPing serverPing);

    @Shadow
    public abstract void A();

    @Shadow
    public abstract void a(CrashReport crashReport);

    @Shadow
    public abstract CrashReport b(CrashReport crashReport);

    @Shadow
    public abstract File y();

    @Shadow
    public abstract void z();

    @Shadow
    public abstract boolean C();

    @Shadow
    public abstract ServerConnection aq();

    @Shadow
    public abstract void a(boolean bl);

    @Shadow
    public abstract void t();

    @Override
    public int getIdleTimeout() {
        return this.G;
    }

    @Override
    public void setIdleTimeout(int timeout) {
        this.G = timeout;
    }

    @Override
    public PropertyManager getPropertyManager() {
        return ((DedicatedServer) (Object) this).propertyManager;
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
    public void setSpawnAnimals(boolean flag) {

    }

    @Override
    public boolean getAllowFlight() {
        return true;
    }

    @Override
    public void setAllowFlight(boolean flag) {

    }

    @Override
    public boolean isHardcore() {
        return false;
    }

    @Override
    public void safeShutdown() {
//        this.isRunning = false;
    }

    @Override
    public String getMotd() {
        return this.E;
    }

    @Override
    public void setMotd(String s) {

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
    public boolean getPVP() {
        return false;
    }

    @Override
    public void setPVP(boolean flag) {

    }

    @Override
    public boolean isRunning() {
        return true;
    }

    @Override
    public String getResourcePack() {
        return this.O;
    }

    @Override
    public String getResourcePackHash() {
        return this.P;
    }

    @Override
    public WorldServer getWorldServer(int i) {
        // CraftBukkit start
        for (WorldServer world : worlds) {
            if (world.dimension == i) {
                return world;
            }
        }
        return worlds.get(0);
        // CraftBukkit end
    }
}
