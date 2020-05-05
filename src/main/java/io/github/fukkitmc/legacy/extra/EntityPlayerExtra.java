package io.github.fukkitmc.legacy.extra;

import org.bukkit.WeatherType;

public interface EntityPlayerExtra {

    WeatherType getPlayerWeather();

    void setPlayerWeather(WeatherType type, boolean plugin);

    void resetPlayerWeather();

    long getPlayerTime();

}
