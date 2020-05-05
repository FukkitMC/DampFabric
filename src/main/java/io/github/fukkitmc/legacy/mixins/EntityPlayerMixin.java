package io.github.fukkitmc.legacy.mixins;

import io.github.fukkitmc.legacy.extra.EntityPlayerExtra;
import net.minecraft.server.EntityPlayer;
import org.bukkit.WeatherType;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityPlayer.class)
public class EntityPlayerMixin implements EntityPlayerExtra {

    @Override
    public WeatherType getPlayerWeather() {
        return null;
    }

    @Override
    public void setPlayerWeather(WeatherType type, boolean plugin) {

    }

    @Override
    public void resetPlayerWeather() {

    }

    @Override
    public long getPlayerTime() {
        return 0;
    }

}
