package io.github.fukkitmc.legacy.mixins.extra;

import com.mojang.authlib.GameProfile;
import io.github.fukkitmc.legacy.debug.BytecodeAnchor;
import io.github.fukkitmc.legacy.extra.EntityHumanExtra;
import net.minecraft.server.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityHuman.class)
public abstract class EntityHumanMixin extends EntityLiving implements EntityHumanExtra {

    @Shadow public String spawnWorld;

    @Shadow public PlayerInventory inventory;

    @Shadow public boolean sleeping;

    @Shadow public int sleepTicks;

    @Shadow public float exp;

    @Shadow public int expLevel;

    @Shadow public int f;

    @Shadow public int expTotal;

    @Shadow public abstract int getScore();

    @Shadow public boolean d;

    @Shadow public FoodMetaData foodData;

    @Shadow public PlayerAbilities abilities;

    @Shadow public InventoryEnderChest enderChest;

    @Shadow public BlockPosition c;

    public EntityHumanMixin(World world) {
        super(world);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void constructor(World world, GameProfile gameProfile, CallbackInfo ci){
        spawnWorld = "";
    }

    @Override
    public String getSpawnWorld() {
        return this.spawnWorld;
    }

    @Override
    public void setSpawnWorld(String s){
        this.spawnWorld = s;
    }

    /**
     * @author fukkit
     */
    @Overwrite
    public void b(NBTTagCompound nBTTagCompound) {
        super.b(nBTTagCompound);
        nBTTagCompound.set("Inventory", this.inventory.a(new NBTTagList()));
        nBTTagCompound.setInt("SelectedItemSlot", this.inventory.itemInHandIndex);
        nBTTagCompound.setBoolean("Sleeping", this.sleeping);
        nBTTagCompound.setShort("SleepTimer", (short) this.sleepTicks);
        nBTTagCompound.setFloat("XpP", this.exp);
        nBTTagCompound.setInt("XpLevel", this.expLevel);
        nBTTagCompound.setInt("XpTotal", this.expTotal);
        nBTTagCompound.setInt("XpSeed", this.f);
        nBTTagCompound.setInt("Score", this.getScore());
        if (this.c != null) {
            nBTTagCompound.setInt("SpawnX", this.c.getX());
            nBTTagCompound.setInt("SpawnY", this.c.getY());
            nBTTagCompound.setInt("SpawnZ", this.c.getZ());
            nBTTagCompound.setBoolean("SpawnForced", this.d);
        }
        this.foodData.b(nBTTagCompound);
        this.abilities.a(nBTTagCompound);
        nBTTagCompound.set("EnderItems", this.enderChest.h());
        ItemStack itemstack = this.inventory.getItemInHand();
        if (itemstack != null && itemstack.getItem() != null) {
            nBTTagCompound.set("SelectedItem", itemstack.save(new NBTTagCompound()));
        }
        nBTTagCompound.setString("SpawnWorld", getSpawnWorld()); // CraftBukkit - fixes bed spawns for multiworld worlds
    }

}
