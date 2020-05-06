package io.github.fukkitmc.legacy.mixins;


import io.github.fukkitmc.legacy.extra.PlayerListExtra;
import net.minecraft.server.BiomeBase;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.PlayerList;
import org.bukkit.Location;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PlayerList.class)
public class PlayerListMixin implements PlayerListExtra {
    @Override
    public EntityPlayer moveToWorld(EntityPlayer entityplayer, int i, boolean flag, Location location, boolean avoidSuffocation) {
        return null;
    }
}
