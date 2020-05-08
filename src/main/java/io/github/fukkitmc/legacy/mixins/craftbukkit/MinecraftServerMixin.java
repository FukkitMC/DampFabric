package io.github.fukkitmc.legacy.mixins.craftbukkit;

import io.github.fukkitmc.legacy.CursedOptionLoader;
import jline.console.ConsoleReader;
import joptsimple.OptionSet;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.*;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.plugin.PluginLoadOrder;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.FutureTask;

import static net.minecraft.server.MinecraftServer.*;

@Mixin(value = MinecraftServer.class, remap = false)
public abstract class MinecraftServerMixin {

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

    /**
     * @author fukkit
     */
    @Environment(EnvType.SERVER)
    @Overwrite
    public static void main(String[] args) {
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

        assert options != null;
        if (options.has("universe")) {
            dedicatedServer.universe = (File) options.valueOf("universe");
        }

//        if (options.has("world")) {
//            dedicatedserver.setWorld((String) options.valueOf("world"));
//        }

    }

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

    @Inject(method = "<init>(Ljava/io/File;Ljava/net/Proxy;Ljava/io/File;)V", at = @At("TAIL"))
    public void constructor(File file, Proxy proxy, File file2, CallbackInfo ci) {
        try {
            methodProfiler = new MethodProfiler();
            processQueue = new java.util.concurrent.ConcurrentLinkedQueue<>();
            ((MinecraftServer)(Object)this).worlds = new ArrayList<>();
            ((MinecraftServer) (Object) this).options = CursedOptionLoader.bukkitOptions;
            this.reader = new ConsoleReader(System.in, System.out);
            if (System.console() == null && System.getProperty("jline.terminal") == null) {
                System.setProperty("jline.terminal", "jline.UnsupportedTerminal");
            }
            LOGGER = k;
        } catch (Exception exception) {
            throw new RuntimeException("Fukkit died CRAB", exception);
        }
    }

    @Inject(method = "O", at = @At("HEAD"), cancellable = true)
    public void o(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }

    /**
     * @author fukkit
     */
    @Overwrite
    public String getServerModName() {
        return server.getName();
    }

    /**
     * @author fukkit
     */
    @Overwrite
    public WorldServer a(int i) {
        if (i == -1) {
            return ((MinecraftServer)(Object)this).worlds.get(1);
        } else {
            return i == 1 ? ((MinecraftServer)(Object)this).worlds.get(2) : ((MinecraftServer)(Object)this).worlds.get(0);
        }
    }

    /**
     * @author fukkit
     */
    @Overwrite(remap = false)
    public void B() {
        this.methodProfiler.a("jobs");
        synchronized (j) {
            while (!this.j.isEmpty()) {
                SystemUtils.a((FutureTask) this.j.poll(), MinecraftServer.LOGGER);
            }
        }

        this.methodProfiler.c("levels");

        // CraftBukkit start
        this.server.getScheduler().mainThreadHeartbeat(((MinecraftServer) (Object) this).ticks);

        // Run tasks that are waiting on processing
        while (!processQueue.isEmpty()) {
            processQueue.remove().run();
        }

        org.bukkit.craftbukkit.chunkio.ChunkIOExecutor.tick();

        // Send time updates to everyone, it will get the right time from the world the player is in.
        if (((MinecraftServer) (Object) this).ticks % 20 == 0) {
            for (int i = 0; i < ((MinecraftServer) (Object) this).getPlayerList().players.size(); ++i) {
                EntityPlayer entityplayer = (EntityPlayer) ((MinecraftServer) (Object) this).getPlayerList().players.get(i);
                entityplayer.playerConnection.sendPacket(new PacketPlayOutUpdateTime(entityplayer.world.getTime(), entityplayer.getPlayerTime(), entityplayer.world.getGameRules().getBoolean("doDaylightCycle"))); // Add support for per player time
            }
        }

        int i;

        for (i = 0; i < ((MinecraftServer)(Object)this).worlds.size(); ++i) {
            long j = System.nanoTime();

            // if (i == 0 || this.getAllowNether()) {
            WorldServer worldserver = ((MinecraftServer)(Object)this).worlds.get(i);

            this.methodProfiler.a(worldserver.getWorldData().getName());
                /* Drop global time updates
                if (this.ticks % 20 == 0) {
                    this.methodProfiler.a("timeSync");
                    this.v.a(new PacketPlayOutUpdateTime(worldserver.getTime(), worldserver.getDayTime(), worldserver.getGameRules().getBoolean("doDaylightCycle")), worldserver.worldProvider.getDimension());
                    this.methodProfiler.b();
                }
                // CraftBukkit end */

            this.methodProfiler.a("tick");

            CrashReport crashreport;

            try {
                worldserver.doTick();
            } catch (Throwable throwable) {
                crashreport = CrashReport.a(throwable, "Exception ticking world");
                worldserver.a(crashreport);
                throw new ReportedException(crashreport);
            }

            try {
                worldserver.tickEntities();
            } catch (Throwable throwable1) {
                crashreport = CrashReport.a(throwable1, "Exception ticking world entities");
                worldserver.a(crashreport);
                throw new ReportedException(crashreport);
            }

            this.methodProfiler.b();
            this.methodProfiler.a("tracker");
            worldserver.getTracker().updatePlayers();
            this.methodProfiler.b();
            this.methodProfiler.b();
            // } // CraftBukkit

            // this.i[i][this.ticks % 100] = System.nanoTime() - j; // CraftBukkit
        }

        this.methodProfiler.c("connection");
        this.aq().c();
        this.methodProfiler.c("players");
        this.v.tick();
        this.methodProfiler.c("tickables");

        for (i = 0; i < this.p.size(); ++i) {
            ((IUpdatePlayerListBox) this.p.get(i)).c();
        }

        this.methodProfiler.b();
    }

    /**
     * @author fukkit
     */
    @Overwrite
    public void t() {
        if (!this.N) {
            k.info("Stopping server");
            if (this.aq() != null) {
                this.aq().b();
            }

            if (this.v != null) {
                k.info("Saving players");
                this.v.savePlayers();
                this.v.u();
            }

            if (this.d != null) {
                k.info("Saving worlds");
                this.a(false);

                for (WorldServer worldServer : ((MinecraftServer)(Object)this).worlds) {
                    worldServer.saveLevel();
                }
            }

            if (this.n.d()) {
                this.n.e();
            }

        }
    }

    /**
     * @author fukkit
     */
    @Overwrite
    public void run() {
        try {
            if (this.i()) {
                this.ab = az();
                long l = 0L;
                this.r.setMOTD(new ChatComponentText(this.E));
                this.r.setServerInfo(new ServerPing.ServerData("1.8.9", 47));
                this.a(this.r);

                while (this.w) {
                    long m = az();
                    long n = m - this.ab;
                    if (n > 2000L && this.ab - this.R >= 15000L) {
                        k.warn("Can't keep up! Did the system time change, or is the server overloaded? Running {}ms behind, skipping {} tick(s)", new Object[]{n, n / 50L});
                        n = 2000L;
                        this.R = this.ab;
                    }

                    if (n < 0L) {
                        k.warn("Time ran backwards! Did the system time change?");
                        n = 0L;
                    }

                    l += n;
                    this.ab = m;
                    if (((MinecraftServer) (Object) this).worlds.get(0).everyoneDeeplySleeping()) {
                        this.A();
                        l = 0L;
                    } else {
                        while (l > 50L) {
                            l -= 50L;
                            this.A();
                        }
                    }

                    Thread.sleep(Math.max(1L, 50L - l));
                    this.Q = true;
                }
            } else {
                this.a((CrashReport) null);
            }
        } catch (Throwable var46) {
            k.error("Encountered an unexpected exception", var46);
            CrashReport crashReport = null;
            if (var46 instanceof ReportedException) {
                crashReport = this.b(((ReportedException) var46).a());
            } else {
                crashReport = this.b(new CrashReport("Exception in server tick loop", var46));
            }

            File file = new File(new File(this.y(), "crash-reports"), "crash-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + "-server.txt");
            if (crashReport.a(file)) {
                k.error("This crash report has been saved to: " + file.getAbsolutePath());
            } else {
                k.error("We were unable to save this crash report to disk.");
            }

            this.a(crashReport);
        } finally {
            try {
                this.x = true;
                this.t();
            } catch (Throwable var44) {
                k.error("Exception stopping the server", var44);
            } finally {
                this.z();
            }

        }

    }


    @Inject(method = "s", at = @At("TAIL"))
    public void enablePluginsPostWorld(CallbackInfo ci) {
        ((MinecraftServer) (Object) this).server.enablePlugins(PluginLoadOrder.POSTWORLD);
    }

    /**
     * @author fukkit
     */
    @Overwrite(remap = false)
    public void a(String s, String s1, long i, WorldType worldtype, String s2) {
        ((MinecraftServer) (Object) this).a(s);
        this.b("menu.loadingLevel");
        this.d = new WorldServer[3];
        int worldCount = 3;

        for (int j = 0; j < worldCount; ++j) {
            WorldServer world;
            byte dimension = 0;

            if (j == 1) {
                if (((MinecraftServer) (Object) this).getAllowNether()) {
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
            WorldSettings worldsettings = new WorldSettings(i, ((MinecraftServer) (Object) this).getGamemode(), ((MinecraftServer) (Object) this).getGenerateStructures(), ((MinecraftServer) (Object) this).isHardcore(), worldtype);
            worldsettings.setGeneratorSettings(s2);

            if (j == 0) {
                IDataManager idatamanager = new ServerNBTManager(server.getWorldContainer(), s1, true);
                WorldData worlddata = idatamanager.getWorldData();
                if (worlddata == null) {
                    worlddata = new WorldData(worldsettings, s1);
                }
                worlddata.checkName(s1); // CraftBukkit - Migration did not rewrite the level.dat; This forces 1.8 to take the last loaded world as respawn (in this case the end)
                if (this.X()) {
                    world = (WorldServer) (new DemoWorldServer(((MinecraftServer) (Object) this), idatamanager, worlddata, dimension, this.methodProfiler)).b();
                } else {
                    WorldServer sworld = new WorldServer(((MinecraftServer) (Object) this), idatamanager, worlddata, dimension, this.methodProfiler);
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
                WorldServer wsrver = new SecondaryWorldServer(((MinecraftServer) (Object) this), idatamanager, dimension, ((MinecraftServer)(Object)this).worlds.get(0), this.methodProfiler);
                wsrver.bukkitInit(gen, org.bukkit.World.Environment.getEnvironment(dimension));
                world = (WorldServer) wsrver.b();
            }

            this.server.getPluginManager().callEvent(new org.bukkit.event.world.WorldInitEvent(world.getWorld()));

            world.addIWorldAccess(new WorldManager(((MinecraftServer) (Object) this), world));
            if (!((MinecraftServer) (Object) this).T()) {
                world.getWorldData().setGameType(((MinecraftServer) (Object) this).getGamemode());
            }

            ((MinecraftServer) (Object) this).worlds.add(world);
            ((MinecraftServer) (Object) this).getPlayerList().setPlayerFileData(((MinecraftServer)(Object)this).worlds.toArray(new WorldServer[((MinecraftServer)(Object)this).worlds.size()]));
        }

        // CraftBukkit end
        this.a(((MinecraftServer) (Object) this).getDifficulty());
        this.k();
    }

    /**
     * @author fukkit
     */
    @Overwrite(remap = false)
    public void a(EnumDifficulty enumdifficulty) {
        // CraftBukkit start
        for (WorldServer worldserver : ((MinecraftServer)(Object)this).worlds) {
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
                    worldserver.setSpawnFlags(((MinecraftServer) (Object) this).getSpawnMonsters(), ((MinecraftServer) (Object) this).spawnAnimals);
                }
            }
        }

    }

    /**
     * @author fukkit
     */
    @Overwrite(remap = false)
    public void k() {
        int i = 0;
        this.b("menu.generatingTerrain");
        // CraftBukkit start - fire WorldLoadEvent and handle whether or not to keep the spawn in memory
        for (int m = 0; m < ((MinecraftServer)(Object)this).worlds.size(); m++) {
            WorldServer worldserver = ((MinecraftServer)(Object)this).worlds.get(m);
            LOGGER.info("Preparing start region for level " + m + " (Seed: " + worldserver.getSeed() + ")");

            if (!worldserver.getWorld().getKeepSpawnInMemory()) {
                continue;
            }

            BlockPosition blockposition = worldserver.getSpawn();
            long j = az();
            i = 0;

            for (int k = -192; k <= 192 && ((MinecraftServer) (Object) this).isRunning(); k += 16) {
                for (int l = -192; l <= 192 && ((MinecraftServer) (Object) this).isRunning(); l += 16) {
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

        for (WorldServer world : ((MinecraftServer)(Object)this).worlds) {
            this.server.getPluginManager().callEvent(new org.bukkit.event.world.WorldLoadEvent(world.getWorld()));
        }
        // CraftBukkit end
        this.s();
    }

    /**
     * @author fukkit
     * @funcName tabCompleteCommand
     */
    @Overwrite
    public List<String> a(ICommandListener icommandlistener, String s, BlockPosition blockposition) {
        return server.tabComplete(icommandlistener, s);
        // CraftBukkit end
    }

}
