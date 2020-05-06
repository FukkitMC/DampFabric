package io.github.fukkitmc.legacy.mixins;


import io.github.fukkitmc.legacy.extra.CraftingManagerExtra;
import net.minecraft.server.BiomeBase;
import net.minecraft.server.CraftingManager;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CraftingManager.class)
public class CraftingManagerMixin implements CraftingManagerExtra {
    @Override
    public void sort() {

    }
}
