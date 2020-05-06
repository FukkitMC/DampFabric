package io.github.fukkitmc.legacy.mixins;


import io.github.fukkitmc.legacy.extra.PlayerInventoryExtra;
import net.minecraft.server.BiomeBase;
import net.minecraft.server.ItemStack;
import net.minecraft.server.PlayerInventory;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin implements PlayerInventoryExtra {
    @Override
    public ItemStack[] getArmorContents() {
        return new ItemStack[0];
    }
}
