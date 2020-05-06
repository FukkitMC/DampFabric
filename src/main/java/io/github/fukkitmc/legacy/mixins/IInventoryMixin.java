package io.github.fukkitmc.legacy.mixins;


import io.github.fukkitmc.legacy.extra.IInventoryExtra;
import net.minecraft.server.BiomeBase;
import net.minecraft.server.IInventory;
import net.minecraft.server.ItemStack;
import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryHolder;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(IInventory.class)
public class IInventoryMixin implements IInventoryExtra {
    @Override
    public ItemStack[] getContents() {
        return new ItemStack[0];
    }

    @Override
    public void onOpen(CraftHumanEntity who) {

    }

    @Override
    public void onClose(CraftHumanEntity who) {

    }

    @Override
    public List<HumanEntity> getViewers() {
        return null;
    }

    @Override
    public InventoryHolder getOwner() {
        return null;
    }

    @Override
    public void setMaxStackSize(int size) {

    }
}
