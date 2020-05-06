package io.github.fukkitmc.legacy.mixins;


import io.github.fukkitmc.legacy.extra.RecipesFurnaceExtra;
import net.minecraft.server.BiomeBase;
import net.minecraft.server.ItemStack;
import net.minecraft.server.RecipesFurnace;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(RecipesFurnace.class)
public class RecipesFurnaceMixin implements RecipesFurnaceExtra {
    @Override
    public void registerRecipe(ItemStack itemstack, ItemStack itemstack1) {

    }
}
