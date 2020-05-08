package io.github.fukkitmc.legacy.mixins.extra;

import io.github.fukkitmc.legacy.extra.IDataManagerExtra;
import net.minecraft.server.BiomeBase;
import net.minecraft.server.IDataManager;
import org.spongepowered.asm.mixin.Mixin;

import java.util.UUID;

@Mixin(IDataManager.class)
public interface IDataManagerMixin extends IDataManagerExtra {
    @Override
    default UUID getUUID() {
        return null;
    }
}
