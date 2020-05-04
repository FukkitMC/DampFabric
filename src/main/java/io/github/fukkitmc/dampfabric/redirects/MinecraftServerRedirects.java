package io.github.fukkitmc.dampfabric.redirects;

import net.minecraft.server.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.CraftServer;

public class MinecraftServerRedirects {

    public static MinecraftServer getServer(){
        return ((CraftServer) Bukkit.getServer()).getServer();
    }

}
