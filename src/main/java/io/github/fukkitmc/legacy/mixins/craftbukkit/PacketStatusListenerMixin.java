package io.github.fukkitmc.legacy.mixins.craftbukkit;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.*;
import org.bukkit.craftbukkit.util.CraftIconCache;
import org.bukkit.entity.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.net.InetSocketAddress;
import java.util.Iterator;

@Mixin(PacketStatusListener.class)
public class PacketStatusListenerMixin {

    @Shadow public NetworkManager networkManager;

    @Shadow public MinecraftServer minecraftServer;

    @Shadow public boolean d;

    /**
     * @author Fukkit
     */
    @Overwrite
    public void a(PacketStatusInStart packetstatusinstart) {
        if (this.d) {
            this.networkManager.close(PacketStatusListener.a);
            // CraftBukkit start - fire ping event
            return;
        }
        this.d = true;
        // this.networkManager.handle(new PacketStatusOutServerInfo(this.minecraftServer.aG()));
        final Object[] players = minecraftServer.getPlayerList().players.toArray();
        class ServerListPingEvent extends org.bukkit.event.server.ServerListPingEvent {
            CraftIconCache icon = minecraftServer.server.getServerIcon();

            ServerListPingEvent() {
                super(((InetSocketAddress) networkManager.getSocketAddress()).getAddress(), minecraftServer.getMotd(), minecraftServer.getPlayerList().getMaxPlayers());
            }

            @Override
            public void setServerIcon(org.bukkit.util.CachedServerIcon icon) {
                if (!(icon instanceof CraftIconCache)) {
                    throw new IllegalArgumentException(icon + " was not created by " + org.bukkit.craftbukkit.CraftServer.class);
                }
                this.icon = (CraftIconCache) icon;
            }

            @Override
            public Iterator<Player> iterator() throws UnsupportedOperationException {
                return new Iterator<Player>() {
                    int i;
                    int ret = Integer.MIN_VALUE;
                    EntityPlayer player;

                    @Override
                    public boolean hasNext() {
                        if (player != null) {
                            return true;
                        }
                        for (int length = players.length, i = this.i; i < length; i++) {
                            final EntityPlayer player = (EntityPlayer) players[i];
                            if (player != null) {
                                this.i = i + 1;
                                this.player = player;
                                return true;
                            }
                        }
                        return false;
                    }

                    @Override
                    public Player next() {
                        if (!hasNext()) {
                            throw new java.util.NoSuchElementException();
                        }
                        final EntityPlayer player = this.player;
                        this.player = null;
                        this.ret = this.i - 1;
                        return (Player) player.getBukkitEntity();
                    }

                    @Override
                    public void remove() {
                        final int i = this.ret;
                        if (i < 0 || players[i] == null) {
                            throw new IllegalStateException();
                        }
                        players[i] = null;
                    }
                };
            }
        }

        ServerListPingEvent event = new ServerListPingEvent();
        this.minecraftServer.server.getPluginManager().callEvent(event);

        java.util.List<GameProfile> profiles = new java.util.ArrayList<GameProfile>(players.length);
        for (Object player : players) {
            if (player != null) {
                profiles.add(((EntityPlayer) player).getProfile());
            }
        }

        ServerPing.ServerPingPlayerSample playerSample = new ServerPing.ServerPingPlayerSample(event.getMaxPlayers(), profiles.size());
        playerSample.a(profiles.toArray(new GameProfile[0]));

        ServerPing ping = new ServerPing();
        ping.setFavicon(event.icon.value);
        ping.setMOTD(new ChatComponentText(event.getMotd()));
        ping.setPlayerSample(playerSample);
        ping.setServerInfo(new ServerPing.ServerData(minecraftServer.getServerModName() + " " + minecraftServer.getVersion(), 47)); // TODO: Update when protocol changes

        this.networkManager.handle(new PacketStatusOutServerInfo(ping));
        // CraftBukkit end
    }

}
