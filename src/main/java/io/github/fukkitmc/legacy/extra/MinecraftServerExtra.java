package io.github.fukkitmc.legacy.extra;

import joptsimple.OptionSet;
import net.minecraft.server.PropertyManager;
import net.minecraft.server.UserCache;

public interface MinecraftServerExtra {

    int getIdleTimeout();

    void setIdleTimeout(int timeout);

    default PropertyManager getPropertyManager(){
        throw new RuntimeException("No. not yet tm");
    }

    String getVersion();

    boolean getSpawnAnimals();

    boolean getAllowFlight();

    boolean isHardcore();

    void safeShutdown();

    String getMotd();

    UserCache getUserCache();
}
