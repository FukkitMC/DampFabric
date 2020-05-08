package io.github.fukkitmc.legacy.mixins.extra;


import com.mojang.authlib.GameProfile;
import io.github.fukkitmc.legacy.debug.BytecodeAnchor;
import io.github.fukkitmc.legacy.extra.PlayerListExtra;
import io.github.fukkitmc.legacy.misc.PlayerListWorldBorderListener;
import io.netty.buffer.Unpooled;
import net.minecraft.server.*;
import org.apache.logging.log4j.Logger;
import org.bukkit.Location;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.chunkio.ChunkIOExecutor;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Mixin(PlayerList.class)
public abstract class PlayerListMixin implements PlayerListExtra {

    @Shadow public MinecraftServer server;

    @Shadow public abstract void a(EntityPlayer entityPlayer, EntityPlayer entityPlayer2, World world);

    @Shadow public abstract void sendScoreboard(ScoreboardServer scoreboardServer, EntityPlayer entityPlayer);

    @Shadow public abstract int getMaxPlayers();

    @Shadow public static Logger f;

    @Shadow public IPlayerFileData playerFileData;

    @Shadow public List<EntityPlayer> players;

    @Shadow public abstract void a(EntityPlayer entityPlayer, WorldServer worldServer);

    @Shadow public CraftServer cserver;

    @Shadow public Map<UUID, EntityPlayer> j;

    @Override
    public EntityPlayer moveToWorld(EntityPlayer entityplayer, int i, boolean flag, Location location, boolean avoidSuffocation) {
        return null;
    }

    @Override
    public void setPlayerFileData(WorldServer[] aworldserver) {
        if (playerFileData != null) return; // CraftBukkit
        this.playerFileData = aworldserver[0].getDataManager().getPlayerFileData();
        aworldserver[0].getWorldBorder().a(new PlayerListWorldBorderListener(((PlayerList)(Object)this)));
    }

    @Override
    public void onPlayerJoin(EntityPlayer entityplayer, String joinMessage) { // CraftBukkit added param
        this.players.add(entityplayer);
        this.j.put(entityplayer.getUniqueID(), entityplayer);
        // this.sendAll(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, new EntityPlayer[] { entityplayer})); // CraftBukkit - replaced with loop below
        WorldServer worldserver = this.server.getWorldServer(entityplayer.dimension);

        // CraftBukkit start
        PlayerJoinEvent playerJoinEvent = new PlayerJoinEvent(cserver.getPlayer(entityplayer), joinMessage);
        cserver.getPluginManager().callEvent(playerJoinEvent);

        joinMessage = playerJoinEvent.getJoinMessage();

        if (joinMessage != null && joinMessage.length() > 0) {
            for (IChatBaseComponent line : org.bukkit.craftbukkit.util.CraftChatMessage.fromString(joinMessage)) {
                server.getPlayerList().sendAll(new PacketPlayOutChat(line));
            }
        }

        ChunkIOExecutor.adjustPoolSize(this.players.size());
        // CraftBukkit end

        // CraftBukkit start - sendAll above replaced with this loop
        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityplayer);

        for (EntityPlayer player : this.players) {
            EntityPlayer entityplayer1 = (EntityPlayer) player;

            if (entityplayer1.getBukkitEntity().canSee((CraftPlayer) entityplayer.getBukkitEntity())) {
                entityplayer1.playerConnection.sendPacket(packet);
            }

            if (!entityplayer.getBukkitEntity().canSee((CraftPlayer) entityplayer1.getBukkitEntity())) {
                continue;
            }

            entityplayer.playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, entityplayer1));
        }
        // CraftBukkit end

        // CraftBukkit start - Only add if the player wasn't moved in the event
        if (entityplayer.world == worldserver && !worldserver.players.contains(entityplayer)) {
            worldserver.addEntity(entityplayer);
            this.a(entityplayer, null);
        }
        // CraftBukkit end
    }

    /**
     * @author fukkit
     */
    @Overwrite(remap = false)
    public void b(EntityPlayer entityplayer, WorldServer worldserver) {
        WorldBorder worldborder = entityplayer.world.getWorldBorder(); // CraftBukkit

        entityplayer.playerConnection.sendPacket(new PacketPlayOutWorldBorder(worldborder, PacketPlayOutWorldBorder.EnumWorldBorderAction.INITIALIZE));
        entityplayer.playerConnection.sendPacket(new PacketPlayOutUpdateTime(worldserver.getTime(), worldserver.getDayTime(), worldserver.getGameRules().getBoolean("doDaylightCycle")));
        if (worldserver.S()) {
            // CraftBukkit start - handle player weather
            entityplayer.setPlayerWeather(org.bukkit.WeatherType.DOWNFALL, false);
            entityplayer.updateWeather(-worldserver.p, worldserver.p, -worldserver.r, worldserver.r);
            // CraftBukkit end
        }

    }

    /**
     * @author fukkit
     */
    @Overwrite
    public String[] getSeenPlayers() {
        return this.server.worlds.get(0).getDataManager().getPlayerFileData().getSeenPlayers();
    }
}
