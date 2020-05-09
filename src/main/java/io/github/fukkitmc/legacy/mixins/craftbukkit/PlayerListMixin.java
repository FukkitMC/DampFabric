package io.github.fukkitmc.legacy.mixins.craftbukkit;

import com.mojang.authlib.GameProfile;
import io.github.fukkitmc.legacy.extra.EntityExtra;
import io.github.fukkitmc.legacy.extra.MinecraftServerExtra;
import io.github.fukkitmc.legacy.extra.PlayerListExtra;
import io.netty.buffer.Unpooled;
import net.minecraft.server.*;
import org.apache.logging.log4j.Logger;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.chunkio.ChunkIOExecutor;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
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
public abstract class PlayerListMixin {

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

    @Shadow public Map<UUID, ServerStatisticManager> o;

    @Shadow public abstract int getPlayerCount();

    @Shadow public abstract void savePlayerFile(EntityPlayer entityPlayer);

    @Inject(method = "<init>", at = @At("TAIL"))
    public void constructor(MinecraftServer minecraftServer, CallbackInfo ci){
        this.players = new java.util.concurrent.CopyOnWriteArrayList(); // CraftBukkit - ArrayList -> CopyOnWriteArrayList: Iterator safety
        ((PlayerList)(Object)this).cserver = minecraftServer.server = new CraftServer(minecraftServer, ((PlayerList)(Object)this));
        minecraftServer.console = org.bukkit.craftbukkit.command.ColouredConsoleSender.getInstance();
        minecraftServer.reader.addCompleter(new org.bukkit.craftbukkit.command.ConsoleCommandCompleter(minecraftServer.server));
    }


    /**
     * @author
     */
    @Overwrite
    public NBTTagCompound a(EntityPlayer entityplayer) {
        NBTTagCompound nbttagcompound = this.server.worlds.get(0).getWorldData().i(); // CraftBukkit
        NBTTagCompound nbttagcompound1;
        if (entityplayer.getName().equals(this.server.S()) && nbttagcompound != null) {
            entityplayer.f(nbttagcompound);
            nbttagcompound1 = nbttagcompound;
            PlayerList.f.debug("loading single player");
        } else {
            nbttagcompound1 = this.playerFileData.load(entityplayer);
        }
        return nbttagcompound1;
    }

    /**
     * @author fukkit
     */
    @Overwrite
    public void a(NetworkManager networkmanager, EntityPlayer entityplayer) {
        GameProfile gameprofile = entityplayer.getProfile();
        UserCache usercache = ((MinecraftServerExtra)this.server).getUserCache();
        GameProfile gameprofile1 = usercache.a(gameprofile.getId());
        String s = gameprofile1 == null ? gameprofile.getName() : gameprofile1.getName();

        usercache.a(gameprofile);
        NBTTagCompound nbttagcompound = this.a(entityplayer);
        // CraftBukkit start - Better rename detection
        if (nbttagcompound != null && nbttagcompound.hasKey("bukkit")) {
            NBTTagCompound bukkit = nbttagcompound.getCompound("bukkit");
            s = bukkit.hasKeyOfType("lastKnownName", 8) ? bukkit.getString("lastKnownName") : s;
        }
        // CraftBukkit end

        entityplayer.spawnIn(((MinecraftServerExtra)this.server).getWorldServer(entityplayer.dimension));
        entityplayer.playerInteractManager.a((WorldServer) entityplayer.world);
        String s1 = "local";

        if (networkmanager.getSocketAddress() != null) {
            s1 = networkmanager.getSocketAddress().toString();
        }

        // CraftBukkit - Moved message to after join
        // PlayerList.f.info(entityplayer.getName() + "[" + s1 + "] logged in with entity id " + entityplayer.getId() + " at (" + entityplayer.locX + ", " + entityplayer.locY + ", " + entityplayer.locZ + ")");
        WorldServer worldserver = ((MinecraftServerExtra)this.server).getWorldServer(entityplayer.dimension);
        WorldData worlddata = worldserver.getWorldData();
        BlockPosition blockposition = worldserver.getSpawn();

        this.a(entityplayer, (EntityPlayer) null, worldserver);
        PlayerConnection playerconnection = new PlayerConnection(this.server, networkmanager, entityplayer);

        playerconnection.sendPacket(new PacketPlayOutLogin(entityplayer.getId(), entityplayer.playerInteractManager.getGameMode(), worlddata.isHardcore(), worldserver.worldProvider.getDimension(), worldserver.getDifficulty(), Math.min(this.getMaxPlayers(), 60), worlddata.getType(), worldserver.getGameRules().getBoolean("reducedDebugInfo"))); // CraftBukkit - cap player list to 60
        ((CraftPlayer)((EntityExtra)entityplayer).getBukkitEntity()).sendSupportedChannels(); // CraftBukkit
        playerconnection.sendPacket(new PacketPlayOutCustomPayload("MC|Brand", (new PacketDataSerializer(Unpooled.buffer())).a(this.server.getServerModName())));
        playerconnection.sendPacket(new PacketPlayOutServerDifficulty(worlddata.getDifficulty(), worlddata.isDifficultyLocked()));
        playerconnection.sendPacket(new PacketPlayOutSpawnPosition(blockposition));
        playerconnection.sendPacket(new PacketPlayOutAbilities(entityplayer.abilities));
        playerconnection.sendPacket(new PacketPlayOutHeldItemSlot(entityplayer.inventory.itemInHandIndex));
        entityplayer.getStatisticManager().d();
        entityplayer.getStatisticManager().updateStatistics(entityplayer);
        this.sendScoreboard((ScoreboardServer) worldserver.getScoreboard(), entityplayer);
        this.server.aH();
        // CraftBukkit start - login message is handled in the event
        // ChatMessage chatmessage;

        String joinMessage;
        if (!entityplayer.getName().equalsIgnoreCase(s)) {
            // chatmessage = new ChatMessage("multiplayer.player.joined.renamed", new Object[] { entityplayer.getScoreboardDisplayName(), s});
            joinMessage = "\u00A7e" + LocaleI18n.a("multiplayer.player.joined.renamed", entityplayer.getName(), s);
        } else {
            // chatmessage = new ChatMessage("multiplayer.player.joined", new Object[] { entityplayer.getScoreboardDisplayName()});
            joinMessage = "\u00A7e" + LocaleI18n.a("multiplayer.player.joined", entityplayer.getName());
        }

        ((PlayerListExtra)this).onPlayerJoin(entityplayer, joinMessage);
        // CraftBukkit end
        worldserver = ((MinecraftServerExtra)this.server).getWorldServer(entityplayer.dimension);  // CraftBukkit - Update in case join event changed it
        playerconnection.a(entityplayer.locX, entityplayer.locY, entityplayer.locZ, entityplayer.yaw, entityplayer.pitch);
        ((PlayerList)(Object)this).b(entityplayer, worldserver);
        if (((MinecraftServerExtra)this.server).getResourcePack().length() > 0) {
            entityplayer.setResourcePack(((MinecraftServerExtra)this.server).getResourcePack(), ((MinecraftServerExtra)this.server).getResourcePackHash());
        }

        Iterator iterator = entityplayer.getEffects().iterator();

        while (iterator.hasNext()) {
            MobEffect mobeffect = (MobEffect) iterator.next();

            playerconnection.sendPacket(new PacketPlayOutEntityEffect(entityplayer.getId(), mobeffect));
        }

        entityplayer.syncInventory();
        if (nbttagcompound != null && nbttagcompound.hasKeyOfType("Riding", 10)) {
            Entity entity = EntityTypes.a(nbttagcompound.getCompound("Riding"), (World) worldserver);

            if (entity != null) {
                entity.attachedToPlayer = true;
                worldserver.addEntity(entity);
                entityplayer.mount(entity);
                entity.attachedToPlayer = false;
            }
        }

        // CraftBukkit - Moved from above, added world
        PlayerList.f.info(entityplayer.getName() + "[" + s1 + "] logged in with entity id " + entityplayer.getId() + " at ([" + entityplayer.world.worldData.getName() + "]" + entityplayer.locX + ", " + entityplayer.locY + ", " + entityplayer.locZ + ")");
    }

    /**
     * @author fukkit
     */
    @Overwrite
    public void disconnect(EntityPlayer entityplayer) { // CraftBukkit - return string
        entityplayer.b(StatisticList.f);

        // CraftBukkit start - Quitting must be before we do final save of data, in case plugins need to modify it
        org.bukkit.craftbukkit.event.CraftEventFactory.handleInventoryCloseEvent(entityplayer);

        PlayerQuitEvent playerQuitEvent = new PlayerQuitEvent(cserver.getPlayer(entityplayer), "\u00A7e" + entityplayer.getName() + " left the game.");
        cserver.getPluginManager().callEvent(playerQuitEvent);
        ((CraftPlayer)((EntityExtra)entityplayer).getBukkitEntity()).disconnect(playerQuitEvent.getQuitMessage());
        // CraftBukkit end

        this.savePlayerFile(entityplayer);
        WorldServer worldserver = entityplayer.u();

        if (entityplayer.vehicle != null && !(entityplayer.vehicle instanceof EntityPlayer)) { // CraftBukkit - Don't remove players
            worldserver.removeEntity(entityplayer.vehicle);
            PlayerList.f.debug("removing player mount");
        }

        worldserver.kill(entityplayer);
        worldserver.getPlayerChunkMap().removePlayer(entityplayer);
        this.players.remove(entityplayer);
        UUID uuid = entityplayer.getUniqueID();
        EntityPlayer entityplayer1 = this.j.get(uuid);

        if (entityplayer1 == entityplayer) {
            this.j.remove(uuid);
            this.o.remove(uuid);
        }

        // CraftBukkit start
        //  this.sendAll(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER, new EntityPlayer[] { entityplayer}));
        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, entityplayer);
        for (EntityPlayer player : players) {
            EntityPlayer entityplayer2 = (EntityPlayer) player;

            if (((EntityExtra)entityplayer2).getBukkitEntity().canSee((CraftPlayer) ((EntityExtra)entityplayer).getBukkitEntity())) {
                entityplayer2.playerConnection.sendPacket(packet);
            } else {
                ((CraftPlayer) ((EntityExtra)entityplayer2).getBukkitEntity()).removeDisconnectingPlayer((Player) ((EntityExtra)entityplayer).getBukkitEntity());
            }
        }
        // This removes the scoreboard (and player reference) for the specific player in the manager
        cserver.getScoreboardManager().removePlayer((Player) ((EntityExtra)entityplayer).getBukkitEntity());
        // CraftBukkit end

        ChunkIOExecutor.adjustPoolSize(this.players.size()); // CraftBukkit

    }

}
