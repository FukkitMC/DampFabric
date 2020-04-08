package me.hydos.dampfabric.fields;

import jline.console.ConsoleReader;
import joptsimple.OptionSet;

public class MinecraftServerFields {

//    public List<WorldServer> worlds = new ArrayList<WorldServer>(); TODO: map world server
    public org.bukkit.craftbukkit.CraftServer server;
    public OptionSet options;
    public org.bukkit.command.ConsoleCommandSender console;
    public org.bukkit.command.RemoteConsoleCommandSender remoteConsole;
    public ConsoleReader reader;
    public static int currentTick = (int) (System.currentTimeMillis() / 50);
    public Thread primaryThread;
    public java.util.Queue<Runnable> processQueue = new java.util.concurrent.ConcurrentLinkedQueue<Runnable>();

}
