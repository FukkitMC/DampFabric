package io.github.fukkitmc.legacy.mixins.craftbukkit;

import net.minecraft.server.*;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PlayerInteractManager.class)
public abstract class PlayerInteractManagerMixin {

    @Shadow public World world;

    @Shadow public EntityPlayer player;

    @Shadow public WorldSettings.EnumGamemode gamemode;

    @Shadow public abstract boolean c(BlockPosition blockPosition);

    @Shadow public abstract boolean isCreative();

    /**
     * @author Fukkit
     */
    @Overwrite
    public boolean breakBlock(BlockPosition blockposition) {
        // CraftBukkit start - fire BlockBreakEvent
        BlockBreakEvent event = null;

        if (this.player != null) {
            org.bukkit.block.Block block = this.world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());

            // Sword + Creative mode pre-cancel
            boolean isSwordNoBreak = this.gamemode.d() && this.player.bA() != null && this.player.bA().getItem() instanceof ItemSword;

            // Tell client the block is gone immediately then process events
            // Don't tell the client if its a creative sword break because its not broken!
            if (world.getTileEntity(blockposition) == null && !isSwordNoBreak) {
                PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange(this.world, blockposition);
                packet.block = Blocks.AIR.getBlockData();
                ((EntityPlayer) this.player).playerConnection.sendPacket(packet);
            }

            event = new BlockBreakEvent(block, (Player) this.player.getBukkitEntity());

            // Sword + Creative mode pre-cancel
            event.setCancelled(isSwordNoBreak);

            // Calculate default block experience
            IBlockData nmsData = this.world.getType(blockposition);
            Block nmsBlock = nmsData.getBlock();

            if (nmsBlock != null && !event.isCancelled() && !this.isCreative() && this.player.b(nmsBlock)) {
                // Copied from block.a(World world, EntityHuman entityhuman, BlockPosition blockposition, IBlockData iblockdata, TileEntity tileentity)
                if (!(nmsBlock.I() && EnchantmentManager.hasSilkTouchEnchantment(this.player))) {
                    int data = block.getData();
                    int bonusLevel = EnchantmentManager.getBonusBlockLootEnchantmentLevel(this.player);
                    event.setExpToDrop(0);//TODO: implement
//                    event.setExpToDrop(nmsBlock.getExpDrop(this.world, nmsData, bonusLevel));
                }
            }

            this.world.getServer().getPluginManager().callEvent(event);

            if (event.isCancelled()) {
                if (isSwordNoBreak) {
                    return false;
                }
                // Let the client know the block still exists
                ((EntityPlayer) this.player).playerConnection.sendPacket(new PacketPlayOutBlockChange(this.world, blockposition));
                // Update any tile entity data for this block
                TileEntity tileentity = this.world.getTileEntity(blockposition);
                if (tileentity != null) {
                    this.player.playerConnection.sendPacket(tileentity.getUpdatePacket());
                }
                return false;
            }
        }
        IBlockData iblockdata = this.world.getType(blockposition);
        if (iblockdata.getBlock() == Blocks.AIR) return false; // CraftBukkit - A plugin set block to air without cancelling
        TileEntity tileentity = this.world.getTileEntity(blockposition);

        // CraftBukkit start - Special case skulls, their item data comes from a tile entity
        if (iblockdata.getBlock() == Blocks.SKULL && !this.isCreative()) {
            iblockdata.getBlock().dropNaturally(world, blockposition, iblockdata, 1.0F, 0);
            return this.c(blockposition);
        }
        // CraftBukkit end

        if (this.gamemode.c()) {
            if (this.gamemode == WorldSettings.EnumGamemode.SPECTATOR) {
                return false;
            }

            if (!this.player.cn()) {
                ItemStack itemstack = this.player.bZ();

                if (itemstack == null) {
                    return false;
                }

                if (!itemstack.c(iblockdata.getBlock())) {
                    return false;
                }
            }
        }

        this.world.a(this.player, 2001, blockposition, Block.getCombinedId(iblockdata));
        boolean flag = this.c(blockposition);

        if (this.isCreative()) {
            this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(this.world, blockposition));
        } else {
            ItemStack itemstack1 = this.player.bZ();
            boolean flag1 = this.player.b(iblockdata.getBlock());

            if (itemstack1 != null) {
                itemstack1.a(this.world, iblockdata.getBlock(), blockposition, this.player);
                if (itemstack1.count == 0) {
                    this.player.ca();
                }
            }

            if (flag && flag1) {
                iblockdata.getBlock().a(this.world, this.player, blockposition, iblockdata, tileentity);
            }
        }

        // CraftBukkit start - Drop event experience
        if (flag && event != null) {
            iblockdata.getBlock().dropExperience(this.world, blockposition, event.getExpToDrop());
        }
        // CraftBukkit end

        return flag;
    }

}
