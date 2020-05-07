package io.github.fukkitmc.legacy.mixins;

import io.github.fukkitmc.legacy.CursedOptionLoader;
import io.github.fukkitmc.legacy.debug.BytecodeAnchor;
import io.github.fukkitmc.legacy.extra.MinecraftServerExtra;
import jline.console.ConsoleReader;
import joptsimple.OptionSet;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.*;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.plugin.PluginLoadOrder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

import static net.minecraft.server.MinecraftServer.*;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin implements MinecraftServerExtra {

    @Shadow public int G;

    @Shadow public UserCache Z;

    @Shadow public PlayerList v;


    @Shadow public CraftServer server;


    @Shadow public ConsoleReader reader;

    @Shadow public List<WorldServer> worlds;

    @Shadow public WorldServer[] worldServer;

    @Shadow public abstract void b(String string);

    @Shadow public abstract boolean X();

    @Shadow public MethodProfiler methodProfiler;

    @Shadow public abstract void s();

    @Shadow public abstract void a_(String string, int i);

    @Shadow public abstract boolean v();

    @Shadow public WorldServer[] d;

    @Inject(method = "<init>(Ljava/io/File;Ljava/net/Proxy;Ljava/io/File;)V", at = @At("TAIL"))
    public void constructor(File file, Proxy proxy, File file2, CallbackInfo ci){
        try{
            worlds = new ArrayList<>();
            ((MinecraftServer)(Object)this).options = CursedOptionLoader.bukkitOptions;
            this.reader = new ConsoleReader(System.in, System.out);
            if (System.console() == null && System.getProperty("jline.terminal") == null) {
                System.setProperty("jline.terminal", "jline.UnsupportedTerminal");
            }
            LOGGER = k;
        }catch (Exception exception){
            throw new RuntimeException("Fukkit died CRAB", exception);
        }
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
        ((MinecraftServer)(Object)this).server.enablePlugins(PluginLoadOrder.POSTWORLD);
    }

    /**
     * @author fukkit
     */
    @Overwrite
    public void a(String s, String s1, long i, WorldType worldtype, String s2) {
        ((MinecraftServer)(Object)this).a(s);
        this.b("menu.loadingLevel");
        this.worldServer = new WorldServer[3];
        int worldCount = 3;

        for (int j = 0; j < worldCount; ++j) {
            WorldServer world;
            byte dimension = 0;

            if (j == 1) {
                if (getAllowNether()) {
                    dimension = -1;
                } else {
                    continue;
                }
            }

            if (j == 2) {
                if (server.getAllowEnd()) {
                    dimension = 1;
                } else {
                    continue;
                }
            }

            String worldType = org.bukkit.World.Environment.getEnvironment(dimension).toString().toLowerCase();
            String name = (dimension == 0) ? s : s + "_" + worldType;

            org.bukkit.generator.ChunkGenerator gen = this.server.getGenerator(name);
            WorldSettings worldsettings = new WorldSettings(i, this.getGamemode(), this.getGenerateStructures(), this.isHardcore(), worldtype);
            worldsettings.setGeneratorSettings(s2);

            if (j == 0) {
                IDataManager idatamanager = new ServerNBTManager(server.getWorldContainer(), s1, true);
                WorldData worlddata = idatamanager.getWorldData();
                if (worlddata == null) {
                    worlddata = new WorldData(worldsettings, s1);
                }
                worlddata.checkName(s1); // CraftBukkit - Migration did not rewrite the level.dat; This forces 1.8 to take the last loaded world as respawn (in this case the end)
                if (this.X()) {
                    world = (WorldServer) (new DemoWorldServer(((MinecraftServer)(Object)this), idatamanager, worlddata, dimension, this.methodProfiler)).b();
                } else {
                    WorldServer sworld = new WorldServer(((MinecraftServer)(Object)this), idatamanager, worlddata, dimension, this.methodProfiler);
                    sworld.bukkitInit(gen, org.bukkit.World.Environment.getEnvironment(dimension));
                    world = (WorldServer) (sworld).b();

                }

                world.a(worldsettings);
                this.server.scoreboardManager = new org.bukkit.craftbukkit.scoreboard.CraftScoreboardManager(((MinecraftServer) (Object) this), world.getScoreboard());
            } else {
                String dim = "DIM" + dimension;

                File newWorld = new File(new File(name), dim);
                File oldWorld = new File(new File(s), dim);

                if ((!newWorld.isDirectory()) && (oldWorld.isDirectory())) {
                    MinecraftServer.LOGGER.info("---- Migration of old " + worldType + " folder required ----");
                    MinecraftServer.LOGGER.info("Unfortunately due to the way that Minecraft implemented multiworld support in 1.6, Bukkit requires that you move your " + worldType + " folder to a new location in order to operate correctly.");
                    MinecraftServer.LOGGER.info("We will move this folder for you, but it will mean that you need to move it back should you wish to stop using Bukkit in the future.");
                    MinecraftServer.LOGGER.info("Attempting to move " + oldWorld + " to " + newWorld + "...");

                    if (newWorld.exists()) {
                        MinecraftServer.LOGGER.warn("A file or folder already exists at " + newWorld + "!");
                        MinecraftServer.LOGGER.info("---- Migration of old " + worldType + " folder failed ----");
                    } else if (newWorld.getParentFile().mkdirs()) {
                        if (oldWorld.renameTo(newWorld)) {
                            MinecraftServer.LOGGER.info("Success! To restore " + worldType + " in the future, simply move " + newWorld + " to " + oldWorld);
                            // Migrate world data too.
                            try {
                                com.google.common.io.Files.copy(new File(new File(s), "level.dat"), new File(new File(name), "level.dat"));
                            } catch (IOException exception) {
                                MinecraftServer.LOGGER.warn("Unable to migrate world data.");
                            }
                            MinecraftServer.LOGGER.info("---- Migration of old " + worldType + " folder complete ----");
                        } else {
                            MinecraftServer.LOGGER.warn("Could not move folder " + oldWorld + " to " + newWorld + "!");
                            MinecraftServer.LOGGER.info("---- Migration of old " + worldType + " folder failed ----");
                        }
                    } else {
                        MinecraftServer.LOGGER.warn("Could not create path for " + newWorld + "!");
                        MinecraftServer.LOGGER.info("---- Migration of old " + worldType + " folder failed ----");
                    }
                }

                IDataManager idatamanager = new ServerNBTManager(server.getWorldContainer(), name, true);
                // world =, b0 to dimension, s1 to name, added Environment and gen
                WorldData worlddata = idatamanager.getWorldData();
                if (worlddata == null) {
                    worlddata = new WorldData(worldsettings, name);
                }
                worlddata.checkName(name); // CraftBukkit - Migration did not rewrite the level.dat; This forces 1.8 to take the last loaded world as respawn (in this case the end)
                WorldServer wsrver = new SecondaryWorldServer(((MinecraftServer)(Object)this), idatamanager, dimension, this.worlds.get(0), this.methodProfiler);
                wsrver.bukkitInit(gen, org.bukkit.World.Environment.getEnvironment(dimension));
                world = (WorldServer)wsrver.b();
            }

            this.server.getPluginManager().callEvent(new org.bukkit.event.world.WorldInitEvent(world.getWorld()));

             world.addIWorldAccess(new WorldManager(((MinecraftServer)(Object)this), world));
            if (!((MinecraftServer)(Object)this).T()) {
                world.getWorldData().setGameType(((MinecraftServer)(Object)this).getGamemode());
            }

            ((MinecraftServer)(Object)this).worlds.add(world);
            getPlayerList().setPlayerFileData(worlds.toArray(new WorldServer[worlds.size()]));
        }

        // CraftBukkit end
        this.a(((MinecraftServer)(Object)this).getDifficulty());
        this.k();
    }

    public void a(EnumDifficulty enumdifficulty) {
        // CraftBukkit start
        for (WorldServer worldserver : this.worlds) {
            // CraftBukkit end

            if (worldserver != null) {
                if (worldserver.getWorldData().isHardcore()) {
                    worldserver.getWorldData().setDifficulty(EnumDifficulty.HARD);
                    worldserver.setSpawnFlags(true, true);
                } else if (((MinecraftServer) (Object) this).T()) {
                    worldserver.getWorldData().setDifficulty(enumdifficulty);
                    worldserver.setSpawnFlags(worldserver.getDifficulty() != EnumDifficulty.PEACEFUL, true);
                } else {
                    worldserver.getWorldData().setDifficulty(enumdifficulty);
                    worldserver.setSpawnFlags(this.getSpawnMonsters(), ((MinecraftServer) (Object) this).spawnAnimals);
                }
            }
        }

    }

    /**
     * @author
     */
    @Overwrite
    public void k() {
        int i = 0;
        this.b("menu.generatingTerrain");
        // CraftBukkit start - fire WorldLoadEvent and handle whether or not to keep the spawn in memory
        for (int m = 0; m < worlds.size(); m++) {
            WorldServer worldserver = this.worlds.get(m);
            LOGGER.info("Preparing start region for level " + m + " (Seed: " + worldserver.getSeed() + ")");

            if (!worldserver.getWorld().getKeepSpawnInMemory()) {
                continue;
            }

            BlockPosition blockposition = worldserver.getSpawn();
            long j = az();
            i = 0;

            for (int k = -192; k <= 192 && this.isRunning(); k += 16) {
                for (int l = -192; l <= 192 && this.isRunning(); l += 16) {
                    long i1 = az();

                    if (i1 - j > 1000L) {
                        this.a_("Preparing spawn area", i * 100 / 625);
                        j = i1;
                    }

                    ++i;
                    worldserver.chunkProviderServer.getChunkAt(blockposition.getX() + k >> 4, blockposition.getZ() + l >> 4);
                }
            }
        }

        for (WorldServer world : this.worlds) {
            this.server.getPluginManager().callEvent(new org.bukkit.event.world.WorldLoadEvent(world.getWorld()));
        }
        // CraftBukkit end
        this.s();
    }

    @Override
    public boolean isRunning() {
        return true;
    }
}
