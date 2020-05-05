package io.github.fukkitmc.legacy.mixins;

import io.github.fukkitmc.legacy.extra.EntityRabbitExtra;
import net.minecraft.server.EntityRabbit;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityRabbit.class)
public class EntityRabbitMixin implements EntityRabbitExtra {

    @Override
    public void initializePathFinderGoals() {

    }
}
