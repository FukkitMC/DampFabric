package me.hydos.dampfabric.fields;

import jline.console.ConsoleReader;
import joptsimple.OptionSet;
import net.minecraft.server.world.ServerWorld;

import java.util.ArrayList;
import java.util.List;

public class MinecraftServerFields {

    public List<ServerWorld> worlds = new ArrayList<ServerWorld>();
    public org.bukkit.craftbukkit.CraftServer server;
    public OptionSet options;
    public org.bukkit.command.ConsoleCommandSender console;
    public org.bukkit.command.RemoteConsoleCommandSender remoteConsole;
    public ConsoleReader reader;
    public static int currentTick = (int) (System.currentTimeMillis() / 50);
    public Thread primaryThread;
    public java.util.Queue<Runnable> processQueue = new java.util.concurrent.ConcurrentLinkedQueue<Runnable>();

}
