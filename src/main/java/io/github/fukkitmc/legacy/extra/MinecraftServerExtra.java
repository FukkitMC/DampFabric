package io.github.fukkitmc.legacy.extra;

import net.minecraft.server.PropertyManager;

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
}
