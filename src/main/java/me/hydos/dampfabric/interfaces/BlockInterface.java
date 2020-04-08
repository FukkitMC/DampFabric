package me.hydos.dampfabric.interfaces;

import net.minecraft.block.BlockState;
import net.minecraft.world.World;

public interface BlockInterface {

    default int getExpDrop(World world, BlockState blockState, int enchantmentLevel){
        return 0;
    }

}
