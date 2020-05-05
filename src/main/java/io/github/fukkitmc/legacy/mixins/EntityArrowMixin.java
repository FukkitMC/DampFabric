package io.github.fukkitmc.legacy.mixins;

import io.github.fukkitmc.legacy.extra.EntityArrowExtra;
import net.minecraft.server.EntityArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityArrow.class)
public class EntityArrowMixin implements EntityArrowExtra {


    @Shadow public boolean inGround;

    @Override
    public boolean isInGround() {
        return inGround;
    }
}
