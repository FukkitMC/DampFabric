package io.github.fukkitmc.legacy.extra;

import org.bukkit.Location;

public interface PlayerConnectionExtra {

    boolean isDisconnected();

    void teleport(Location dest);

    void chat(String s, boolean async);

}
