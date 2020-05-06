package io.github.fukkitmc.legacy.mixins;

import io.github.fukkitmc.legacy.CursedOptionLoader;
import io.github.fukkitmc.legacy.extra.MinecraftServerExtra;
import joptsimple.OptionSet;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.*;
import org.bukkit.craftbukkit.CraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;
import java.net.Proxy;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin implements MinecraftServerExtra {

    @Shadow public int G;

    @Shadow public UserCache Z;

    @Shadow public PlayerList v;


    @Shadow public CraftServer server;

    @Inject(method = "<init>(Ljava/io/File;Ljava/net/Proxy;Ljava/io/File;)V", at=@At("TAIL"))
    public void cursedConstructor(File file, Proxy proxy, File file2, CallbackInfo ci){
        ((MinecraftServer)(Object)this).options = CursedOptionLoader.bukkitOptions;
    }

    @Inject(method = "O", at=@At("HEAD"), cancellable = true)
    public void o(CallbackInfoReturnable<Boolean> cir){
        cir.setReturnValue(true);
    }

    public String getServerModName() {
        return server.getName();
    }

    /**
     * @author fukkit
     */
    @Environment(EnvType.SERVER)
    @Overwrite
    public static void main(String[] args){
        System.setProperty("java.util.logging.manager", "org.apache.logging.log4j.jul.LogManager");
        OptionSet options = CursedOptionLoader.loadOptions(args);
        DispenserRegistry.c();

        String string2 = ".";

        DedicatedServer dedicatedServer = new DedicatedServer(new File(string2));

//        if (options.has("port")) {
//            int port = (Integer) options.valueOf("port");
//            if (port > 0) {
//                dedicatedserver.setPort(port);
//            }
//        }

        dedicatedServer.D();
        Runtime.getRuntime().addShutdownHook(new Thread(dedicatedServer::t, "Server Shutdown Thread"));

        if (options.has("universe")) {
            dedicatedServer.universe = (File) options.valueOf("universe");
        }

//        if (options.has("world")) {
//            dedicatedserver.setWorld((String) options.valueOf("world"));
//        }

    }

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
        return ((DedicatedServer)(Object)this).propertyManager;
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
//        this.isRunning = false;
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

    @Inject(method = "s", at= @At("TAIL"))
    public void enablePluginsPostWorld(CallbackInfo ci){
        ((MinecraftServer)(Object)this).server.enablePlugins(org.bukkit.plugin.PluginLoadOrder.POSTWORLD);
    }
}
