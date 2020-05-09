package io.github.fukkitmc.legacy.mixins.craftbukkit;

import io.github.fukkitmc.legacy.craftbukkit.synthetic.SyntheticClass_1;
import net.minecraft.server.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.net.InetAddress;
import java.util.HashMap;

@Mixin(HandshakeListener.class)
public class HandshakeListenerMixin {

    @Shadow public NetworkManager b;

    @Shadow public MinecraftServer a;

    @Shadow public HashMap<InetAddress, Long> throttleTracker;

    @Shadow public int throttleCounter;

    /**
     * @author Fukkit
     */
    @Overwrite
    public void a(PacketHandshakingInSetProtocol packethandshakinginsetprotocol) {
        switch (SyntheticClass_1.a[packethandshakinginsetprotocol.a().ordinal()]) {
            case 1:
                this.b.a(EnumProtocol.LOGIN);
                ChatComponentText chatcomponenttext;

                // CraftBukkit start - Connection throttle
                try {
                    long currentTime = System.currentTimeMillis();
                    long connectionThrottle = MinecraftServer.getServer().server.getConnectionThrottle();
                    InetAddress address = ((java.net.InetSocketAddress) this.b.getSocketAddress()).getAddress();

                    synchronized (throttleTracker) {
                        if (throttleTracker.containsKey(address) && !"127.0.0.1".equals(address.getHostAddress()) && currentTime - throttleTracker.get(address) < connectionThrottle) {
                            throttleTracker.put(address, currentTime);
                            chatcomponenttext = new ChatComponentText("Connection throttled! Please wait before reconnecting.");
                            this.b.handle(new PacketLoginOutDisconnect(chatcomponenttext));
                            this.b.close(chatcomponenttext);
                            return;
                        }

                        throttleTracker.put(address, currentTime);
                        throttleCounter++;
                        if (throttleCounter > 200) {
                            throttleCounter = 0;

                            // Cleanup stale entries
                            java.util.Iterator iter = throttleTracker.entrySet().iterator();
                            while (iter.hasNext()) {
                                java.util.Map.Entry<InetAddress, Long> entry = (java.util.Map.Entry) iter.next();
                                if (entry.getValue() > connectionThrottle) {
                                    iter.remove();
                                }
                            }
                        }
                    }
                } catch (Throwable t) {
                    org.apache.logging.log4j.LogManager.getLogger().debug("Failed to check connection throttle", t);
                }
                // CraftBukkit end

                if (packethandshakinginsetprotocol.b() > 47) {
                    chatcomponenttext = new ChatComponentText("Outdated server! I\'m still on 1.8.8");
                    this.b.handle(new PacketLoginOutDisconnect(chatcomponenttext));
                    this.b.close(chatcomponenttext);
                } else if (packethandshakinginsetprotocol.b() < 47) {
                    chatcomponenttext = new ChatComponentText("Outdated client! Please use 1.8.8");
                    this.b.handle(new PacketLoginOutDisconnect(chatcomponenttext));
                    this.b.close(chatcomponenttext);
                } else {
                    this.b.a((PacketListener) (new LoginListener(this.a, this.b)));
//                    ((LoginListener) this.b.getPacketListener()).hostname = packethandshakinginsetprotocol.hostname + ":" + packethandshakinginsetprotocol.port; // CraftBukkit - set hostname
                    //TODO: fukkit add login event
                }
                break;

            case 2:
                this.b.a(EnumProtocol.STATUS);
                this.b.a(new PacketStatusListener(this.a, this.b));
                break;

            default:
                throw new UnsupportedOperationException("Invalid intention " + packethandshakinginsetprotocol.a());
        }

    }



}
