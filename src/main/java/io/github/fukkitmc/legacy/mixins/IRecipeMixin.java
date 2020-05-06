package io.github.fukkitmc.legacy.mixins;


import io.github.fukkitmc.legacy.extra.IRecipeExtra;
import net.minecraft.server.BiomeBase;
import net.minecraft.server.IRecipe;
import org.bukkit.inventory.Recipe;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(IRecipe.class)
public class IRecipeMixin implements IRecipeExtra {
    @Override
    public Recipe toBukkitRecipe() {
        return null;
    }
}
