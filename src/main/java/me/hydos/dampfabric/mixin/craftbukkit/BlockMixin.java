package me.hydos.dampfabric.mixin.craftbukkit;

import me.hydos.dampfabric.interfaces.BlockInterface;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(Block.class)
public class BlockMixin implements BlockInterface {

    @Redirect(method = "method_646", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextFloat()F")) //think this is the amount of items dropped from an explosion
    private static float nextFloat(Random r){
        float f = r.nextFloat();
        return Float.intBitsToFloat(Float.floatToIntBits(f)+1);
    }

}
