package io.github.fukkitmc.legacy.extra;

import net.minecraft.server.EntityPlayer;
import org.bukkit.Location;

public interface PlayerListExtra {

    EntityPlayer moveToWorld(EntityPlayer entityplayer, int i, boolean flag, Location location, boolean avoidSuffocation);

}