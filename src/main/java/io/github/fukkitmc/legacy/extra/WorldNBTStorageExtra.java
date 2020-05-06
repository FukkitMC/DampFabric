package io.github.fukkitmc.legacy.extra;

import net.minecraft.server.NBTTagCompound;

import java.io.File;

public interface WorldNBTStorageExtra {

    NBTTagCompound getPlayerData(String s);

    File getPlayerDir();

}
