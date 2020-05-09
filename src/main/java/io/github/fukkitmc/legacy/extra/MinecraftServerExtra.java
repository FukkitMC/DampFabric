package io.github.fukkitmc.legacy.extra;

import net.minecraft.server.*;

public interface MinecraftServerExtra {

    int getIdleTimeout();

    void setIdleTimeout(int timeout);

    PropertyManager getPropertyManager();

    String getVersion();

    boolean getSpawnAnimals();

    boolean getAllowFlight();

    boolean isHardcore();

    void safeShutdown();

    String getMotd();

    UserCache getUserCache();

    void stop();

    PlayerList getPlayerList();

    boolean getOnlineMode();

    void setOnlineMode(boolean flag);

    void setPVP(boolean flag);

    boolean getPVP();

    void setSpawnAnimals(boolean flag);

    void setAllowFlight(boolean flag);

    void setMotd(String s);

    default boolean getAllowNether(){
        return true;
    }

    default WorldSettings.EnumGamemode getGamemode(){
        return WorldSettings.EnumGamemode.CREATIVE;
    }

    default boolean getGenerateStructures(){
        return true;
    }

    default EnumDifficulty getDifficulty(){
        return EnumDifficulty.EASY;
    }

    void a(EnumDifficulty enumdifficulty);

    default boolean getSpawnMonsters(){
        return true;
    }

    boolean isRunning();

    String getResourcePack();

    String getResourcePackHash();

    WorldServer getWorldServer(int i);

    String getServerIp();
}