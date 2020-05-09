package io.github.fukkitmc.legacy.redirects;

import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.BlockPosition;
import net.minecraft.server.EnumDirection;

public class EntityHangingRedirects {

    public static AxisAlignedBB calculateBoundingBox(BlockPosition blockPosition, EnumDirection direction, int width, int height) {
        double d0 = (double) blockPosition.getX() + 0.5D;
        double d1 = (double) blockPosition.getY() + 0.5D;
        double d2 = (double) blockPosition.getZ() + 0.5D;
        double d4 = width % 32 == 0 ? 0.5D : 0.0D;
        double d5 = height % 32 == 0 ? 0.5D : 0.0D;

        d0 -= (double) direction.getAdjacentX() * 0.46875D;
        d2 -= (double) direction.getAdjacentZ() * 0.46875D;
        d1 += d5;
        EnumDirection enumdirection = direction.f();

        d0 += d4 * (double) enumdirection.getAdjacentX();
        d2 += d4 * (double) enumdirection.getAdjacentZ();
        double d6 = width;
        double d7 = height;
        double d8 = width;

        if (direction.k() == EnumDirection.EnumAxis.Z) {
            d8 = 1.0D;
        } else {
            d6 = 1.0D;
        }

        d6 /= 32.0D;
        d7 /= 32.0D;
        d8 /= 32.0D;
        return new AxisAlignedBB(d0 - d6, d1 - d7, d2 - d8, d0 + d6, d1 + d7, d2 + d8);
    }

}
