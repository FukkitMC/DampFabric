package io.github.fukkitmc.legacy.mixins;


import io.github.fukkitmc.legacy.extra.PortalTravelAgentExtra;
import net.minecraft.server.BiomeBase;
import net.minecraft.server.PortalTravelAgent;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PortalTravelAgent.class)
public class PortalTravelAgentMixin implements PortalTravelAgentExtra {
}
