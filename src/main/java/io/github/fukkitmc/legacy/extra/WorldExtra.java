package io.github.fukkitmc.legacy.extra;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;

public interface WorldExtra {

    CraftWorld getWorld();

    CraftServer getServer();
}
