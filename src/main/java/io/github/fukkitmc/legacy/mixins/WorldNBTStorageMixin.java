package io.github.fukkitmc.legacy.mixins;


import io.github.fukkitmc.legacy.extra.WorldNBTStorageExtra;
import net.minecraft.server.BiomeBase;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.WorldNBTStorage;
import org.spongepowered.asm.mixin.Mixin;

import java.io.File;

@Mixin(WorldNBTStorage.class)
public class WorldNBTStorageMixin implements WorldNBTStorageExtra {
    @Override
    public NBTTagCompound getPlayerData(String s) {
        return null;
    }

    @Override
    public File getPlayerDir() {
        return null;
    }
}
