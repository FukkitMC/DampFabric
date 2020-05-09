package io.github.fukkitmc.legacy.mixins.extra;


import io.github.fukkitmc.legacy.extra.IRecipeExtra;
import net.minecraft.server.BiomeBase;
import net.minecraft.server.IRecipe;
import org.bukkit.inventory.Recipe;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(IRecipe.class)
public interface IRecipeMixin extends IRecipeExtra {
    @Override
    default Recipe toBukkitRecipe() {
        return null;
    }
}
