package io.github.fukkitmc.legacy.mixins.extra;

import io.github.fukkitmc.legacy.extra.WorldServerExtra;
import net.minecraft.server.WorldServer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(WorldServer.class)
public class WorldServerMixin implements WorldServerExtra {

}
