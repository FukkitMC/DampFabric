package io.github.fukkitmc.legacy.craftbukkit.synthetic;

import net.minecraft.server.EnumProtocol;
import net.minecraft.server.HandshakeListener;

public class SyntheticClass_1 {

    public static final int[] a = new int[EnumProtocol.values().length];

    static {
        try {
            SyntheticClass_1.a[EnumProtocol.LOGIN.ordinal()] = 1;
        } catch (NoSuchFieldError ignored) {
            ;
        }

        try {
            SyntheticClass_1.a[EnumProtocol.STATUS.ordinal()] = 2;
        } catch (NoSuchFieldError ignored) {
            ;
        }

    }
}