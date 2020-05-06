package io.github.fukkitmc.legacy.mixins;

import io.github.fukkitmc.legacy.extra.PlayerConnectionExtra;
import net.minecraft.server.*;
import org.bukkit.Location;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Collections;
import java.util.Set;

@Mixin(PlayerConnection.class)
public class PlayerConnectionMixin implements PlayerConnectionExtra {


    @Shadow public MinecraftServer minecraftServer;

    @Shadow public int chatThrottle;

    @Shadow public EntityPlayer player;

    @Shadow @Final public NetworkManager networkManager;

    @Override
    public boolean isDisconnected() {
        return !this.player.joining && !this.networkManager.channel.config().isAutoRead();
    }

    @Override
    public void teleport(Location dest) {
        internalTeleport(dest.getX(), dest.getY(), dest.getZ(), dest.getYaw(), dest.getPitch(), Collections.emptySet());
    }

    private void internalTeleport(double d0, double d1, double d2, float f, float f1, Set set) {
//        if (Float.isNaN(f)) {
//            f = 0;
//        }
//
//        if (Float.isNaN(f1)) {
//            f1 = 0;
//        }
//        this.justTeleported = true;
//        // CraftBukkit end
//        this.checkMovement = false;
//        this.o = d0;
//        this.p = d1;
//        this.q = d2;
//        if (set.contains(PacketPlayOutPosition.EnumPlayerTeleportFlags.X)) {
//            this.o += this.player.locX;
//        }
//
//        if (set.contains(PacketPlayOutPosition.EnumPlayerTeleportFlags.Y)) {
//            this.p += this.player.locY;
//        }
//
//        if (set.contains(PacketPlayOutPosition.EnumPlayerTeleportFlags.Z)) {
//            this.q += this.player.locZ;
//        }
//
//        float f2 = f;
//        float f3 = f1;
//
//        if (set.contains(PacketPlayOutPosition.EnumPlayerTeleportFlags.Y_ROT)) {
//            f2 = f + this.player.yaw;
//        }
//
//        if (set.contains(PacketPlayOutPosition.EnumPlayerTeleportFlags.X_ROT)) {
//            f3 = f1 + this.player.pitch;
//        }
//
//        // CraftBukkit start - update last location
//        this.lastPosX = this.o;
//        this.lastPosY = this.p;
//        this.lastPosZ = this.q;
//        this.lastYaw = f2;
//        this.lastPitch = f3;
//        // CraftBukkit end
//
//        this.player.setLocation(this.o, this.p, this.q, f2, f3);
//        this.player.playerConnection.sendPacket(new PacketPlayOutPosition(d0, d1, d2, f, f1, set));
    }

    @Override
    public void chat(String s, boolean async) {
//        if (s.isEmpty() || this.player.getChatFlags() == EntityHuman.EnumChatVisibility.HIDDEN) {
//            return;
//        }
//
//        if (!async && s.startsWith("/")) {
//            this.handleCommand(s);
//        } else if (this.player.getChatFlags() == EntityHuman.EnumChatVisibility.SYSTEM) {
//            // Do nothing, this is coming from a plugin
//        } else {
//            Player player = this.getPlayer();
//            AsyncPlayerChatEvent event = new AsyncPlayerChatEvent(async, player, s, new LazyPlayerSet());
//            this.server.getPluginManager().callEvent(event);
//
//            if (PlayerChatEvent.getHandlerList().getRegisteredListeners().length != 0) {
//                // Evil plugins still listening to deprecated event
//                final PlayerChatEvent queueEvent = new PlayerChatEvent(player, event.getMessage(), event.getFormat(), event.getRecipients());
//                queueEvent.setCancelled(event.isCancelled());
//                Waitable waitable = () -> {
//                        org.bukkit.Bukkit.getPluginManager().callEvent(queueEvent);
//
//                        if (queueEvent.isCancelled()) {
//                            return null;
//                        }
//
//                        String message = String.format(queueEvent.getFormat(), queueEvent.getPlayer().getDisplayName(), queueEvent.getMessage());
//                        minecraftServer.console.sendMessage(message);
//                        if (((LazyPlayerSet) queueEvent.getRecipients()).isLazy()) {
//                            for (Object player : minecraftServer.getPlayerList().players) {
//                                ((EntityPlayer) player).sendMessage(CraftChatMessage.fromString(message));
//                            }
//                        } else {
//                            for (Player player : queueEvent.getRecipients()) {
//                                player.sendMessage(message);
//                            }
//                        }
//                        return null;
//                    };
//                if (async) {
//                    minecraftServer.processQueue.add(waitable);
//                } else {
//                    waitable.run();
//                }
//                try {
//                    waitable.get();
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt(); // This is proper habit for java. If we aren't handling it, pass it on!
//                } catch (ExecutionException e) {
//                    throw new RuntimeException("Exception processing chat event", e.getCause());
//                }
//            } else {
//                if (event.isCancelled()) {
//                    return;
//                }
//
//                s = String.format(event.getFormat(), event.getPlayer().getDisplayName(), event.getMessage());
//                minecraftServer.console.sendMessage(s);
//                if (((LazyPlayerSet) event.getRecipients()).isLazy()) {
//                    for (Object recipient : minecraftServer.getPlayerList().players) {
//                        ((EntityPlayer) recipient).sendMessage(CraftChatMessage.fromString(s));
//                    }
//                } else {
//                    for (Player recipient : event.getRecipients()) {
//                        recipient.sendMessage(s);
//                    }
//                }
//            }
//        }
    }
}
