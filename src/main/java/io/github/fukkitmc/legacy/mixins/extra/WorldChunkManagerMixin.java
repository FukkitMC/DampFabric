package io.github.fukkitmc.legacy.mixins.extra;


import io.github.fukkitmc.legacy.extra.WorldChunkManagerExtra;
import net.minecraft.server.BiomeBase;
import net.minecraft.server.WorldChunkManager;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(WorldChunkManager.class)
public class WorldChunkManagerMixin implements WorldChunkManagerExtra {
    @Override
    public BiomeBase[] getBiomeBlock(BiomeBase[] var1, int var2, int var3, int var4, int var5) {
        return new BiomeBase[0];
    }
}
