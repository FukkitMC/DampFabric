package io.github.fukkitmc.legacy.mixins.craftbukkit;

import net.minecraft.server.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.bukkit.craftbukkit.LoggerOutputStream;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Proxy;
import java.util.List;
import java.util.Random;

@Mixin(DedicatedServer.class)
public abstract class DedicatedServerMixin extends MinecraftServer implements IMinecraftServer {

    @Shadow public PropertyManager propertyManager;

    public DedicatedServerMixin(Proxy proxy, File file) {
        super(file, proxy, file);
    }

    @Shadow public abstract boolean aB();

    @Shadow public List<ServerCommand> l;

    @Shadow public EULA p;

    @Shadow public boolean generateStructures;

    @Shadow public WorldSettings.EnumGamemode r;

    @Shadow public abstract boolean aR();

    @Shadow public RemoteStatusListener m;

    @Shadow public RemoteControlListener n;

    @Shadow public abstract long aS();

    public void issueCommand(String s, ICommandListener icommandlistener) {
        this.l.add(new ServerCommand(s, icommandlistener));
    }

    protected boolean init() throws IOException {
        // CraftBukkit start - TODO: handle command-line logging arguments
        java.util.logging.Logger global = java.util.logging.Logger.getLogger("");
        global.setUseParentHandlers(false);
        for (java.util.logging.Handler handler : global.getHandlers()) {
            global.removeHandler(handler);
        }
        global.addHandler(new org.bukkit.craftbukkit.util.ForwardLogHandler());

        final org.apache.logging.log4j.core.Logger logger = ((org.apache.logging.log4j.core.Logger) LogManager.getRootLogger());
        for (org.apache.logging.log4j.core.Appender appender : logger.getAppenders().values()) {
            if (appender instanceof org.apache.logging.log4j.core.appender.ConsoleAppender) {
                logger.removeAppender(appender);
            }
        }

        new Thread(new org.bukkit.craftbukkit.util.TerminalConsoleWriterThread(System.out, this.reader)).start();

        System.setOut(new PrintStream(new LoggerOutputStream(logger, Level.INFO), true));
        System.setErr(new PrintStream(new LoggerOutputStream(logger, Level.WARN), true));
        // CraftBukkit end

        DedicatedServer.LOGGER.info("Starting minecraft server version 1.8.8");
        if (Runtime.getRuntime().maxMemory() / 1024L / 1024L < 512L) {
            DedicatedServer.LOGGER.warn("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar minecraft_server.jar\"");
        }

        DedicatedServer.LOGGER.info("Loading properties");
        this.propertyManager = new PropertyManager(new File("server.properties")); //Fukkit: stuff craftbukkit cli support
        this.p = new EULA(new File("eula.txt"));
        if (!this.p.a()) {
            DedicatedServer.LOGGER.info("You need to agree to the EULA in order to run the server. Go to eula.txt for more info.");
            this.p.b();
            return false;
        } else {
            if (this.T()) {
                this.c("127.0.0.1");
            } else {
                this.setOnlineMode(this.propertyManager.getBoolean("online-mode", true));
                this.c(this.propertyManager.getString("server-ip", ""));
            }

            this.setSpawnAnimals(this.propertyManager.getBoolean("spawn-animals", true));
            this.setPVP(this.propertyManager.getBoolean("pvp", true));
            this.setAllowFlight(this.propertyManager.getBoolean("allow-flight", false));
            this.setMotd(this.propertyManager.getString("motd", "A Minecraft Server"));
            this.setIdleTimeout(this.propertyManager.getInt("player-idle-timeout", 0));
            if (this.propertyManager.getInt("difficulty", 1) < 0) {
                this.propertyManager.setProperty("difficulty", 0);
            } else if (this.propertyManager.getInt("difficulty", 1) > 3) {
                this.propertyManager.setProperty("difficulty", 3);
            }

            this.generateStructures = this.propertyManager.getBoolean("generate-structures", true);
            int i = this.propertyManager.getInt("gamemode", WorldSettings.EnumGamemode.SURVIVAL.getId());

            this.r = WorldSettings.a(i);
            DedicatedServer.LOGGER.info("Default game type: " + this.r);
            InetAddress inetaddress = null;

            if (this.getServerIp().length() > 0) {
                inetaddress = InetAddress.getByName(this.getServerIp());
            }

            if (this.R() < 0) {
                this.b(this.propertyManager.getInt("server-port", 25565));
            }

            DedicatedServer.LOGGER.info("Generating keypair");
            this.a(MinecraftEncryption.b());
            DedicatedServer.LOGGER.info("Starting Minecraft server on " + (this.getServerIp().length() == 0 ? "*" : this.getServerIp()) + ":" + this.R());

            try {
                this.aq().a(inetaddress, this.R());
            } catch (IOException ioexception) {
                DedicatedServer.LOGGER.warn("**** FAILED TO BIND TO PORT!");
                DedicatedServer.LOGGER.warn("The exception was: {}", ioexception.toString());
                DedicatedServer.LOGGER.warn("Perhaps a server is already running on that port?");
                return false;
            }

            this.a((PlayerList) (new DedicatedPlayerList(((DedicatedServer)(Object)this)))); // CraftBukkit

            if (!this.getOnlineMode()) {
                DedicatedServer.LOGGER.warn("**** SERVER IS RUNNING IN OFFLINE/INSECURE MODE!");
                DedicatedServer.LOGGER.warn("The server will make no attempt to authenticate usernames. Beware.");
                DedicatedServer.LOGGER.warn("While this makes the game possible to play without internet access, it also opens up the ability for hackers to connect with any username they choose.");
                DedicatedServer.LOGGER.warn("To change this, set \"online-mode\" to \"true\" in the server.properties file.");
            }

            if (this.aR()) {
                this.getUserCache().c();
            }

            if (!NameReferencingFileConverter.a(this.propertyManager)) {
                return false;
            } else {
//                TODO: fukkit convertable
//                this.convertable = new WorldLoaderServer(server.getWorldContainer()); // CraftBukkit - moved from MinecraftServer constructor
                long j = System.nanoTime();

                if (this.U() == null) {
                    this.j(this.propertyManager.getString("level-name", "world"));
                }

                String s = this.propertyManager.getString("level-seed", "");
                String s1 = this.propertyManager.getString("level-type", "DEFAULT");
                String s2 = this.propertyManager.getString("generator-settings", "");
                long k = (new Random()).nextLong();

                if (s.length() > 0) {
                    try {
                        long l = Long.parseLong(s);

                        if (l != 0L) {
                            k = l;
                        }
                    } catch (NumberFormatException numberformatexception) {
                        k = (long) s.hashCode();
                    }
                }

                WorldType worldtype = WorldType.getType(s1);

                if (worldtype == null) {
                    worldtype = WorldType.NORMAL;
                }

                this.aB();
                this.p();
                this.aK();
                this.c(this.propertyManager.getInt("max-build-height", 256));
                this.c((this.an() + 8) / 16 * 16);
                this.c(MathHelper.clamp(this.an(), 64, 256));
                this.propertyManager.setProperty("max-build-height", this.an());
                DedicatedServer.LOGGER.info("Preparing level \"" + this.U() + "\"");
                this.a(this.U(), this.U(), k, worldtype, s2);
                long i1 = System.nanoTime() - j;
                String s3 = String.format("%.3fs", (double) i1 / 1.0E9D);

                DedicatedServer.LOGGER.info("Done (" + s3 + ")! For help, type \"help\" or \"?\"");
                if (this.propertyManager.getBoolean("enable-query", false)) {
                    DedicatedServer.LOGGER.info("Starting GS4 status listener");
                    this.m = new RemoteStatusListener(((DedicatedServer)(Object)this));
                    this.m.a();
                }

                if (this.propertyManager.getBoolean("enable-rcon", false)) {
                    DedicatedServer.LOGGER.info("Starting remote control listener");
                    this.n = new RemoteControlListener(((DedicatedServer)(Object)this));
                    this.n.a();
                    //TODO: fukkit craft commands in console
//                    this.remoteConsole = new org.bukkit.craftbukkit.command.CraftRemoteConsoleCommandSender(); // CraftBukkit
                }

                // CraftBukkit start
                if (this.server.getBukkitSpawnRadius() > -1) {
                    DedicatedServer.LOGGER.info("'settings.spawn-radius' in bukkit.yml has been moved to 'spawn-protection' in server.properties. I will move your config for you.");
                    this.propertyManager.properties.remove("spawn-protection");
                    this.propertyManager.getInt("spawn-protection", this.server.getBukkitSpawnRadius());
                    this.server.removeBukkitSpawnRadius();
                    this.propertyManager.savePropertiesFile();
                }
                // CraftBukkit end

                if (this.aS() > 0L) {
                    Thread thread1 = new Thread(new ThreadWatchdog(((DedicatedServer)(Object)this)));

                    thread1.setName("Server Watchdog");
                    thread1.setDaemon(true);
                    thread1.start();
                }

                return true;
            }
        }
    }

}
