package io.github.fukkitmc.legacy.mixins.extra;


import io.github.fukkitmc.legacy.extra.WorldDataExtra;
import net.minecraft.server.BiomeBase;
import net.minecraft.server.WorldData;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(WorldData.class)
public class WorldDataMixin implements WorldDataExtra {
    @Override
    public void checkName(String name) {

    }
}
