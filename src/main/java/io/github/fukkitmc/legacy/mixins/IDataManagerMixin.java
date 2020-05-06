package io.github.fukkitmc.legacy.mixins;


import io.github.fukkitmc.legacy.extra.IDataManagerExtra;
import net.minecraft.server.BiomeBase;
import net.minecraft.server.IDataManager;
import org.spongepowered.asm.mixin.Mixin;

import java.util.UUID;

@Mixin(IDataManager.class)
public class IDataManagerMixin implements IDataManagerExtra {
    @Override
    public UUID getUUID() {
        return null;
    }
}
