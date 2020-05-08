package io.github.fukkitmc.legacy.mixins.craftbukkit;

import net.minecraft.server.*;
import org.bukkit.craftbukkit.inventory.CraftInventoryCrafting;
import org.bukkit.craftbukkit.inventory.CraftInventoryView;
import org.bukkit.entity.HumanEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ContainerPlayer.class)
public abstract class ContainerPlayerMixin extends Container {

    @Shadow public InventoryCrafting craftInventory;

    @Shadow public IInventory resultInventory;

    @Shadow public CraftInventoryView bukkitEntity;

    @Shadow public PlayerInventory player;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void constructor(PlayerInventory playerInventory, boolean bl, EntityHuman entityHuman, CallbackInfo ci){
        this.player = playerInventory;
    }

    @Override
    public CraftInventoryView getBukkitView() {
        if (bukkitEntity != null) {
            return bukkitEntity;
        }

        CraftInventoryCrafting inventory = new CraftInventoryCrafting(this.craftInventory, this.resultInventory);
        bukkitEntity = new CraftInventoryView((HumanEntity) this.player.player.getBukkitEntity(), inventory, this);
        return bukkitEntity;
    }
    // CraftBukkit end

}
