package io.github.fukkitmc.legacy.misc;

import net.minecraft.server.IWorldBorderListener;
import net.minecraft.server.PacketPlayOutWorldBorder;
import net.minecraft.server.PlayerList;
import net.minecraft.server.WorldBorder;

public class PlayerListWorldBorderListener implements IWorldBorderListener {

    PlayerList playerList;

    public PlayerListWorldBorderListener(PlayerList list){
        this.playerList = list;
    }

    public void a(WorldBorder worldborder, double d0) {
        playerList.sendAll(new PacketPlayOutWorldBorder(worldborder, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_SIZE));
    }

    public void a(WorldBorder worldborder, double d0, double d1, long i) {
        playerList.sendAll(new PacketPlayOutWorldBorder(worldborder, PacketPlayOutWorldBorder.EnumWorldBorderAction.LERP_SIZE));
    }

    public void a(WorldBorder worldborder, double d0, double d1) {
        playerList.sendAll(new PacketPlayOutWorldBorder(worldborder, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_CENTER));
    }

    public void a(WorldBorder worldborder, int i) {
        playerList.sendAll(new PacketPlayOutWorldBorder(worldborder, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_WARNING_TIME));
    }

    public void b(WorldBorder worldborder, int i) {
        playerList.sendAll(new PacketPlayOutWorldBorder(worldborder, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_WARNING_BLOCKS));
    }

    public void b(WorldBorder worldborder, double d0) {}

    public void c(WorldBorder worldborder, double d0) {}
}
