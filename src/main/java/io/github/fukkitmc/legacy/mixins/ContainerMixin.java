package io.github.fukkitmc.legacy.mixins;

import io.github.fukkitmc.legacy.extra.ContainerExtra;
import net.minecraft.server.Container;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Container.class)
public class ContainerMixin implements ContainerExtra {
}
